/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieulm.servlet;

import com.paypal.http.HttpResponse;
import com.paypal.orders.LinkDescription;
import com.paypal.orders.Order;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import hieulm.cart.CartObject;
import hieulm.tblproduct.TblProductDTO;
import hieulm.tbluser.TblUserDTO;
import hieulm.util.PayPalUtils;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 *
 * @author MinHiu
 */
@WebServlet(name = "SendPayPalRequestServlet", urlPatterns = {"/SendPayPalRequestServlet"})
public class SendPayPalRequestServlet extends HttpServlet {

    final String ERROR_PAGE = "loadCart";

    static Logger log = Logger.getLogger(SendPayPalRequestServlet.class.getName());

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

	String url = ERROR_PAGE;

	try {
	    String orderId = (String) request.getAttribute("ORDER_ID");
	    if (orderId != null) {

		HttpSession session = request.getSession(false);
		if (session != null) {
		    CartObject cart = (CartObject) session.getAttribute("CART");
		    if (cart != null) {
			cart.calculateTotalPrice();
			float totalPrice = cart.getTotalPrice();
			if (totalPrice > -1) {
			    Map<TblProductDTO, Integer> items = cart.getItems();

			    TblUserDTO currentUser = (TblUserDTO) session.getAttribute("CURRENT_USER");
			    if (currentUser != null) {
				HttpResponse<Order> orderResponse = PayPalUtils.createOrder(totalPrice, items, currentUser);
				if (orderResponse.statusCode() == 201) {
				    for (LinkDescription link : orderResponse.result().links()) {
					if (link.rel().equals("approve")) {
					    url = link.href();
					}
				    }
				}
			    }
			}
		    }
		}
	    }
	    
	    if (url.equals(ERROR_PAGE)) {
		url += "?sendingError=We can't send your request to Paypal. Please try again.";
	    }
	} catch (IOException ex) {
	    log.error(ex.getMessage());
	    url += "?sendingError=We can't send your request to Paypal. Please try again.";
	} finally {
	    response.sendRedirect(url);
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
