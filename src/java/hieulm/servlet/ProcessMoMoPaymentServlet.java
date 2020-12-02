/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieulm.servlet;

import com.mservice.shared.sharedmodels.Environment;
import com.mservice.shared.utils.Encoder;
import hieulm.cart.CartObject;
import hieulm.tblorderdetail.TblOrderDetailDAO;
import hieulm.tblproduct.TblProductDAO;
import hieulm.tblproduct.TblProductDTO;
import hieulm.tbluser.TblUserDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
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
@WebServlet(name = "ProcessMoMoPaymentServlet", urlPatterns = {"/ProcessMoMoPaymentServlet"})
public class ProcessMoMoPaymentServlet extends HttpServlet {

    static Logger log = Logger.getLogger(ProcessMoMoPaymentServlet.class.getName());

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

	String partnerCode = request.getParameter("partnerCode");
	String accessKey = request.getParameter("accessKey");
	String requestId = request.getParameter("requestId");
	String amount = request.getParameter("amount");
	String orderId = request.getParameter("orderId");
	String orderInfo = request.getParameter("orderInfo");
	String orderType = request.getParameter("orderType");
	String transId = request.getParameter("transId");
	String message = request.getParameter("message");
	String localMessage = request.getParameter("localMessage");
	String responseTime = request.getParameter("responseTime");
	String errorCode = request.getParameter("errorCode");
	String payType = request.getParameter("payType");
	String extraData = request.getParameter("extraData");
	String signature = request.getParameter("signature");
	String data = "partnerCode=" + partnerCode
		+ "&accessKey=" + accessKey
		+ "&requestId=" + requestId
		+ "&amount=" + amount
		+ "&orderId=" + orderId
		+ "&orderInfo=" + orderInfo
		+ "&orderType=" + orderType
		+ "&transId=" + transId
		+ "&message=" + message
		+ "&localMessage=" + localMessage
		+ "&responseTime=" + responseTime
		+ "&errorCode=" + errorCode
		+ "&payType=" + payType
		+ "&extraData=" + extraData;

	System.out.println(errorCode);

	ServletContext context = request.getServletContext();
	Map<String, String> siteMap = (Map<String, String>) context.getAttribute("SITE_MAP");

	String url = siteMap.get(VIEW_CART_PAGE_ERROR);

	try {
	    HttpSession session = request.getSession(false);
	    if (session != null) {
		Environment env = Environment.selectEnv("dev", Environment.ProcessType.PAY_GATE);
		String secrectKey = env.getPartnerInfo().getSecretKey();
		String generateSignature = Encoder.signHmacSHA256(data, secrectKey);

		if (generateSignature.equals(signature)) {
		    if (errorCode.equals("0")) {
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
					    TblUserDTO currentUser = (TblUserDTO) session.getAttribute("CURRENT_USER");
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
	    }

	    if (!url.equals(siteMap.get(ORDER_SUCCESS_PAGE))) {
		CartObject cart = (CartObject) session.getAttribute("CART");

		if (cart != null) {
		    Map<TblProductDTO, Integer> items = cart.getItems();

		    cart.calculateTotalPrice();
		    float totalPrice = cart.getTotalPrice();

		    request.setAttribute("TOTAL_PRICE", totalPrice);
		    request.setAttribute("CART_ITEMS", items);
		    request.setAttribute("SEND_REQUEST_ERROR_MOMO", "Something went wrong, we can't recieve your payment. Please try again.");
		}
	    }
	} catch (IllegalArgumentException ex) {
	    log.error(ex.getMessage());
	} catch (InvalidKeyException ex) {
	    log.error(ex.getMessage());
	} catch (NoSuchAlgorithmException ex) {
	    log.error(ex.getMessage());
	} catch (UnsupportedEncodingException ex) {
	    log.error(ex.getMessage());
	} catch (NamingException ex) {
	    log.error(ex.getMessage());
	} catch (SQLException ex) {
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
