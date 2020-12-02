/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieulm.servlet;

import hieulm.tblcategory.TblCategoryDAO;
import hieulm.tblproduct.TblProductDAO;
import hieulm.tblproduct.TblProductDTO;
import hieulm.tbluser.TblUserDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
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
@WebServlet(name = "SearchCakeServlet", urlPatterns = {"/SearchCakeServlet"})
public class SearchCakeServlet extends HttpServlet {

    final String HOME_PAGE = "homePage";

    static Logger log = Logger.getLogger(SearchCakeServlet.class.getName());

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

	String txtSearchName = request.getParameter("txtCakeName");
	String txtSearchRange = request.getParameter("txtRange");
	String txtSearchCategory = request.getParameter("txtCategory");
	String txtPage = request.getParameter("txtPage");

	ServletContext context = request.getServletContext();
	Map<String, String> siteMap = (Map<String, String>) context.getAttribute("SITE_MAP");
	String url = siteMap.get(HOME_PAGE);

	boolean isAdmin = false;

	try {
	    int page = 1;

	    if (txtPage != null) {
		page = Integer.parseInt(txtPage);
	    }

	    if (page > 0) {
		String categoryId = "All";

		if (!txtSearchCategory.equals("All")) {
		    TblCategoryDAO categoryDAO = new TblCategoryDAO();
		    categoryId = categoryDAO.getCategoryId(txtSearchCategory);
		}

		if (txtSearchCategory.isEmpty()) {
		    categoryId = "All";
		}

		if (categoryId != null) {
		    HttpSession session = request.getSession(false);
		    if (session != null) {
			TblUserDTO currentUser = (TblUserDTO) session.getAttribute("CURRENT_USER");
			if (currentUser != null) {
			    if (currentUser.getUserRole().equals("A")) {
				TblProductDAO dao = new TblProductDAO();
				dao.searchProductBaseRequireAdmin(txtSearchName, txtSearchRange, categoryId, page);
				int numberOfPage = dao.calculatePageBaseRequireAdmin(txtSearchName, txtSearchRange, categoryId);
				List<TblProductDTO> listProduct = dao.getListProductActive();

				if (listProduct != null) {
				    request.setAttribute("LIST_PRODUCT", listProduct);
				    request.setAttribute("CURRENT_PAGE", page);
				    request.setAttribute("NUMBER_OF_PAGE", numberOfPage);

				    isAdmin = true;
				}
			    }
			}
		    }

		    if (!isAdmin) {
			TblProductDAO dao = new TblProductDAO();
			dao.searchProductBaseRequire(txtSearchName, txtSearchRange, categoryId, page);
			int numberOfPage = dao.calculatePageBaseRequire(txtSearchName, txtSearchRange, categoryId);
			List<TblProductDTO> listProduct = dao.getListProductActive();

			if (listProduct != null) {
			    request.setAttribute("LIST_PRODUCT", listProduct);
			    request.setAttribute("CURRENT_PAGE", page);
			    request.setAttribute("NUMBER_OF_PAGE", numberOfPage);
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
