/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieulm.servlet;

import hieulm.cart.CartObject;
import hieulm.tblorder.TblOrderCreateError;
import hieulm.tblorder.TblOrderDAO;
import hieulm.tblorderdetail.TblOrderDetailDAO;
import hieulm.tblproduct.TblProductDAO;
import hieulm.tblproduct.TblProductDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 *
 * @author MinHiu
 */
@WebServlet(name = "CheckOutServlet", urlPatterns = {"/CheckOutServlet"})
public class CheckOutServlet extends HttpServlet {

    static Logger log = Logger.getLogger(CheckOutServlet.class.getName());

    final String ORDER_SUCCESS_PAGE = "orderSuccessPage";
    final String VIEW_CART_PAGE_ERROR = "viewCartPage";
    final String ACTIVE_PRODUCT_STATUS_NAME = "Active";
    final String SEND_MOMO_REQUEST = "sendMoMoRequest";
    final String SEND_PAYPAL_REQUEST = "sendPayPalRequest";
    

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	response.setContentType("text/html;charset=UTF-8");

	PrintWriter out = response.getWriter();

	ServletContext context = request.getServletContext();
	Map<String, String> siteMap = (Map<String, String>) context.getAttribute("SITE_MAP");

	String url = siteMap.get(VIEW_CART_PAGE_ERROR);

	String customerName = request.getParameter("txtNameCustomer");
	String customerEmail = request.getParameter("txtEmailCustomer");
	String customerPhone = request.getParameter("txtPhoneCustomer");
	String customerAddress = request.getParameter("txtAddressCustomer");
	String paymentMethod = request.getParameter("txtPaymentMethod");

	TblOrderCreateError error = new TblOrderCreateError();

	boolean foundError = false;

	try {
	    if (customerName.trim().length() < 3 || customerName.trim().length() > 50) {
		error.setCustomerNameLengthError("Name must contain 3 - 50 characters.");
		foundError = true;
	    }
	    if (!customerEmail.trim().matches("((\\w*)(\\d*))+@((\\w+)\\.(\\w+)){1}(\\.(\\w+))*") || customerEmail.length() > 100 || customerEmail.length() < 10) {
		error.setCustomerEmailFormatError("Email entered is invalid. Email must contain 10-100 characters and match pattern.");
		foundError = true;
	    }
	    if (!customerPhone.matches("[0-9]{5,20}")) {
		error.setCustomerPhoneFormatError("Phone entered is invalid. Phone must contain 5-20 numbers.");
		foundError = true;
	    }
	    if (customerAddress.trim().length() < 5 || customerAddress.trim().length() > 200) {
		error.setCustomerAddressLengthError("Address must contain 5-200 characters.");
		foundError = true;
	    }

	    HttpSession session = request.getSession(false);
	    if (session != null) {
		CartObject cart = (CartObject) session.getAttribute("CART");

		String quantityError = checkCartQuantity(cart);
		if (!quantityError.isEmpty()) {
		    error.setQuantityOverRangeError(quantityError);
		    foundError = true;
		}
	    } else {
		foundError = true;
	    }

	    if (session != null) {
		CartObject cart = (CartObject) session.getAttribute("CART");

		String statusError = checkCartItemStatus(cart);
		if (!statusError.isEmpty()) {
		    error.setProductStatusError(statusError);
		    foundError = true;
		}
	    } else {
		foundError = true;
	    }

	    if (!foundError) {
		CartObject cart = (CartObject) session.getAttribute("CART");

		if (cart != null) {
		    cart.calculateTotalPrice();
		    float totalPrice = cart.getTotalPrice();

		    if (totalPrice != -1) {
			Map<TblProductDTO, Integer> items = cart.getItems();

			if (items != null) {
			    TblOrderDAO orderDAO = new TblOrderDAO();
			    boolean result = orderDAO.createNewOrder(totalPrice, customerEmail, customerName, customerPhone, customerAddress);

			    if (result) {
				String orderId = orderDAO.getOrderId(customerEmail, customerName, customerPhone, customerAddress);

				if (paymentMethod.equals("COD")) {
				    for (Map.Entry<TblProductDTO, Integer> entry : items.entrySet()) {
					TblProductDTO key = entry.getKey();
					Integer value = entry.getValue();

					TblOrderDetailDAO orderDetailDAO = new TblOrderDetailDAO();
					result = orderDetailDAO.insertOrderDetail(orderId, key.getProductId(), key.getProductPrice(), value);

					if (result) {
					    TblProductDAO productDAO = new TblProductDAO();
					    productDAO.updateProductQuantity(key.getProductId(), key.getProductQuantity() - value);

					    request.setAttribute("CART_ITEMS_COMPLETED", items);
					    request.setAttribute("ORDER_ID", orderId);
					    request.setAttribute("TOTAL_PRICE_COMPLETED", totalPrice);
					    
					    request.setAttribute("CUSTOMER_NAME", customerName);
					    request.setAttribute("CUSTOMER_EMAIL", customerEmail);
					    request.setAttribute("CUSTOMER_PHONE", customerPhone);
					    request.setAttribute("CUSTOMER_ADDRESS", customerAddress);
					    
					    session.removeAttribute("CART");
					    session.removeAttribute("NUMBER_ITEMS");

					    url = siteMap.get(ORDER_SUCCESS_PAGE);
					}
				    }
				}
				else if(paymentMethod.equals("Momo")) {
				    url = siteMap.get(SEND_MOMO_REQUEST);
				    request.setAttribute("ORDER_ID", orderId);
				    request.setAttribute("TOTAL_PAY", totalPrice);
				}
				else if(paymentMethod.equals("Paypal")){
				    url = siteMap.get(SEND_PAYPAL_REQUEST);
				    request.setAttribute("ORDER_ID", orderId);
				    request.setAttribute("TOTAL_PAY", totalPrice);
				}
			    }
			}
		    }
		}
	    } else {
		CartObject cart = (CartObject) session.getAttribute("CART");

		if (cart != null) {
		    Map<TblProductDTO, Integer> items = cart.getItems();

		    cart.calculateTotalPrice();
		    float totalPrice = cart.getTotalPrice();

		    request.setAttribute("TOTAL_PRICE", totalPrice);
		    request.setAttribute("CART_ITEMS", items);
		}
		request.setAttribute("CHECK_OUT_ERROR", error);
	    }

	} catch (NamingException ex) {
	    log.error("NamingException " + ex.getMessage());
	} catch (SQLException ex) {
	    log.error("SQLException " + ex.getMessage());
	} finally {
	    RequestDispatcher rd = request.getRequestDispatcher(url);
	    rd.forward(request, response);
	    out.close();
	}
    }

    private String checkCartQuantity(CartObject cart) {
	String quantityError = "";

	if (cart != null) {
	    Map<TblProductDTO, Integer> items = cart.getItems();

	    if (items != null) {
		for (Map.Entry<TblProductDTO, Integer> entry : items.entrySet()) {
		    TblProductDTO key = entry.getKey();
		    int quantity = entry.getValue();

		    if (key.getProductQuantity() < quantity) {
			quantityError += key.getProductName() + " only have " + key.getProductQuantity() + " left. Your order need " + quantity + ". Please update the quantity.\n";
		    }
		}
	    }
	}
	return quantityError;
    }

    private String checkCartItemStatus(CartObject cart) {
	String statusError = "";

	if (cart != null) {
	    Map<TblProductDTO, Integer> items = cart.getItems();

	    if (items != null) {
		for (Map.Entry<TblProductDTO, Integer> entry : items.entrySet()) {
		    TblProductDTO key = entry.getKey();

		    if (!key.getProductStatus().equals(ACTIVE_PRODUCT_STATUS_NAME)) {
			statusError += key.getProductName() + " is not available now. Please remove it to continue.\n";
		    }
		}
	    }
	}
	return statusError;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
	return "Short description";
    }// </editor-fold>

}
