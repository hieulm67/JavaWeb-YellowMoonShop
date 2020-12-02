/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieulm.servlet;

import com.paypal.http.HttpResponse;
import com.paypal.orders.Order;
import hieulm.cart.CartObject;
import hieulm.tblorder.TblOrderDAO;
import hieulm.tblorderdetail.TblOrderDetailDAO;
import hieulm.tblproduct.TblProductDAO;
import hieulm.tblproduct.TblProductDTO;
import hieulm.tbluser.TblUserDTO;
import hieulm.util.PayPalUtils;
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
@WebServlet(name = "ProcessPayPalPaymentServlet", urlPatterns = {"/ProcessPayPalPaymentServlet"})
public class ProcessPayPalPaymentServlet extends HttpServlet {

    static Logger log = Logger.getLogger(ProcessPayPalPaymentServlet.class.getName());

    final String ORDER_SUCCESS_PAGE = "orderSuccessPage";
    final String VIEW_CART_PAGE_ERROR = "viewCartPage";

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

	String token = request.getParameter("token");

	ServletContext context = request.getServletContext();
	Map<String, String> siteMap = (Map<String, String>) context.getAttribute("SITE_MAP");

	String url = siteMap.get(VIEW_CART_PAGE_ERROR);

	try {
	    HttpSession session = request.getSession(false);
	    if (session != null) {
		HttpResponse<Order> orderResponse = PayPalUtils.captureOrder(token);
		if (orderResponse.statusCode() == 201) {
		    TblUserDTO currentUser = (TblUserDTO) session.getAttribute("CURRENT_USER");
		    if (currentUser != null) {
			TblOrderDAO orderDAO = new TblOrderDAO();
			String orderId = orderDAO.getOrderId(currentUser.getUserEmail(), currentUser.getUserName(), currentUser.getUserPhone(), currentUser.getUserAddress());
			
			CartObject cart = (CartObject) session.getAttribute("CART");
			if (cart != null) {
			    cart.calculateTotalPrice();
			    float totalPrice = cart.getTotalPrice();

			    if (totalPrice != -1) {
				Map<TblProductDTO, Integer> items = cart.getItems();

				if (items != null) {
				    for (Map.Entry<TblProductDTO, Integer> entry : items.entrySet()) {
					TblProductDTO key = entry.getKey();
					Integer value = entry.getValue();

					TblOrderDetailDAO orderDetailDAO = new TblOrderDetailDAO();
					boolean result = orderDetailDAO.insertOrderDetail(orderId, key.getProductId(), key.getProductPrice(), value);

					if (result) {
					    if (currentUser != null) {
						TblProductDAO productDAO = new TblProductDAO();
						productDAO.updateProductQuantity(key.getProductId(), key.getProductQuantity() - value);

						request.setAttribute("CART_ITEMS_COMPLETED", items);
						request.setAttribute("ORDER_ID", orderId);
						request.setAttribute("TOTAL_PRICE_COMPLETED", totalPrice);

						request.setAttribute("CUSTOMER_NAME", currentUser.getUserName());
						request.setAttribute("CUSTOMER_EMAIL", currentUser.getUserEmail());
						request.setAttribute("CUSTOMER_PHONE", currentUser.getUserPhone());
						request.setAttribute("CUSTOMER_ADDRESS", currentUser.getUserAddress());

						session.removeAttribute("CART");
						session.removeAttribute("NUMBER_ITEMS");

						url = siteMap.get(ORDER_SUCCESS_PAGE);
					    }
					}
				    }
				}
			    }
			}
		    }
		}

		if (!url.equals(siteMap.get(ORDER_SUCCESS_PAGE))) {
		    CartObject cart = (CartObject) session.getAttribute("CART");

		    if (cart != null) {
			Map<TblProductDTO, Integer> items = cart.getItems();

			cart.calculateTotalPrice();
			float totalPrice = cart.getTotalPrice();

			request.setAttribute("TOTAL_PRICE", totalPrice);
			request.setAttribute("CART_ITEMS", items);
			request.setAttribute("SEND_REQUEST_ERROR_MOMO", "Something went wrong. Please try again.");
		    }
		}
	    }
	} catch (NamingException ex) {
	    log.error("NamingException " + ex.getMessage());
	} catch (SQLException ex) {
	    log.error("SQLException " + ex.getMessage());
	} catch (IOException ex) {
	    log.error(ex.getMessage());
	} finally {
	    RequestDispatcher rd = request.getRequestDispatcher(url);
	    rd.forward(request, response);
	    out.close();
	}
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
