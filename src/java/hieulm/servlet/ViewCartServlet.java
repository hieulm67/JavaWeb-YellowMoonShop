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
@WebServlet(name = "ViewCartServlet", urlPatterns = {"/ViewCartServlet"})
public class ViewCartServlet extends HttpServlet {

    static Logger log = Logger.getLogger(ViewCartServlet.class.getName());

    final String VIEW_CART_PAGE = "viewCartPage";

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
	String url = siteMap.get(VIEW_CART_PAGE);

	try {

	    HttpSession session = request.getSession(false);
	    if (session != null) {
		CartObject cart = (CartObject) session.getAttribute("CART");

		if (cart != null) {

		    Map<TblProductDTO, Integer> items = cart.getItems();

		    if (items != null) {
			for (Map.Entry<TblProductDTO, Integer> entry : items.entrySet()) {
			    TblProductDTO key = entry.getKey();

			    TblProductDAO dao = new TblProductDAO();
			    TblProductDTO dto = dao.searchProductBaseId(key.getProductId());

			    cart.updateInformation(dto);
			}

			cart.calculateTotalPrice();
			float totalPrice = cart.getTotalPrice();

			session.setAttribute("CART", cart);
			request.setAttribute("TOTAL_PRICE", totalPrice);
			request.setAttribute("CART_ITEMS", items);
			
			String error = request.getParameter("sendingError");
			if(error != null){
			    request.setAttribute("SENDING_ERROR", error);
			}
		    }
		}
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
