/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieulm.servlet;

import hieulm.cart.CartObject;
import hieulm.tblproduct.TblProductDAO;
import hieulm.tblproduct.TblProductDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.naming.NamingException;
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
@WebServlet(name = "AddCakeToCartServlet", urlPatterns = {"/AddCakeToCartServlet"})
public class AddCakeToCartServlet extends HttpServlet {

    static Logger log = Logger.getLogger(AddCakeToCartServlet.class.getName());

    final String SEARCH_CAKE_CONTROLLER = "searchCake";

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

	String productId = request.getParameter("productId");
	String txtSearchName = request.getParameter("txtCakeName");
	String txtSearchRange = request.getParameter("txtRange");
	String txtSearchCategory = request.getParameter("txtCategory");
	String txtPage = request.getParameter("txtPage");

	String url = SEARCH_CAKE_CONTROLLER + "?txtCakeName=" + txtSearchName
		+ "&txtRange=" + txtSearchRange + "&txtCategory=" + txtSearchCategory + "&txtPage=" + txtPage;

	try {
	    HttpSession session = request.getSession();
	    CartObject cart = (CartObject) session.getAttribute("CART");
	    if (cart == null) {
		cart = new CartObject();
	    }

	    TblProductDAO dao = new TblProductDAO();
	    TblProductDTO dto = dao.searchProductBaseId(Integer.parseInt(productId));

	    if (dto != null) {
		cart.addItemToCart(dto);

		session.setAttribute("CART", cart);
		session.setAttribute("NUMBER_ITEMS", cart.totalItemsInCart());
	    }
	} catch (NamingException ex) {
	    log.error("NamingException " + ex.getMessage());
	} catch (SQLException ex) {
	    log.error("SQLException " + ex.getMessage());
	} catch (NumberFormatException ex) {
	    log.error("NumberFormatException " + ex.getMessage());
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
