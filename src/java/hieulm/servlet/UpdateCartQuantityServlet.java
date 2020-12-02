/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieulm.servlet;

import hieulm.cart.CartObject;
import hieulm.cart.CartUpdateError;
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
@WebServlet(name = "UpdateCartQuantityServlet", urlPatterns = {"/UpdateCartQuantityServlet"})
public class UpdateCartQuantityServlet extends HttpServlet {

    static Logger log = Logger.getLogger(UpdateCartQuantityServlet.class.getName());

    final String LOAD_CART_CONTROLLER = "loadCart";

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

	String url = siteMap.get(LOAD_CART_CONTROLLER);

	String productId = request.getParameter("productId");
	String txtQuantity = request.getParameter("txtQuantity");

	CartUpdateError error = new CartUpdateError();

	boolean foundError = false;

	try {
	    int quantity = 0;
	    if (!txtQuantity.isEmpty()) {
		try {
		    quantity = Integer.parseInt(txtQuantity);
		    if (quantity > 10000) {
			error.setQuantityOverRangeError("Our store can't provide enough quantity.");
			foundError = true;
		    }
		} catch (NumberFormatException ex) {
		    error.setQuantityFormatError("Invalid quantity.");
		    foundError = true;
		}
	    }

	    if (!foundError) {
		HttpSession session = request.getSession(false);
		if (session != null) {
		    CartObject cart = (CartObject) session.getAttribute("CART");
		    if (cart != null) {
			TblProductDAO dao = new TblProductDAO();
			TblProductDTO dto = dao.searchProductBaseId(Integer.parseInt(productId));

			if (quantity > 0) {
			    cart.updateQuantityOfItem(dto, quantity);
			} else {
			    cart.deleteItemFromCart(dto);
			}
			session.setAttribute("CART", cart);
			session.setAttribute("NUMBER_ITEMS", cart.totalItemsInCart());
		    }
		}
	    } else {
		request.setAttribute("UPDATE_QUANTITY_ERROR", error);
		request.setAttribute("PRODUCT_ID_ERROR", productId);
	    }
	} catch (NamingException ex) {
	    log.error("NamingException " + ex.getMessage());
	} catch (SQLException ex) {
	    log.error("SQLException " + ex.getMessage());
	} catch (NumberFormatException ex) {
	    log.error("NumberFormatException " + ex.getMessage());
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
