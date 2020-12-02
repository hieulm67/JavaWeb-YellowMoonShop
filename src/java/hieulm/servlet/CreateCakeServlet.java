/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieulm.servlet;

import hieulm.tblcategory.TblCategoryDAO;
import hieulm.tbllog.TblLogDAO;
import hieulm.tblproduct.TblProductCreateNewError;
import hieulm.tblproduct.TblProductDAO;
import hieulm.tbluser.TblUserDTO;
import hieulm.util.FileUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.apache.log4j.Logger;

/**
 *
 * @author MinHiu
 */
@MultipartConfig
@WebServlet(name = "CreateCakeServlet", urlPatterns = {"/CreateCakeServlet"})
public class CreateCakeServlet extends HttpServlet {

    final String START_UP_CONTROLLER = "startUp";
    final String CREATE_NEW_CAKE_PAGE = "createCakePage";
    final String SERVER_PATH = "D:/Study/CN5/LabWeb/Project/YellowMoonShop/img/";

    static Logger log = Logger.getLogger(CreateCakeServlet.class.getName());

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

	String url = siteMap.get(CREATE_NEW_CAKE_PAGE);

	Part imageSelect = request.getPart("imageSelect");
	String txtName = request.getParameter("txtName");
	String txtPrice = request.getParameter("txtPrice");
	String txtQuantity = request.getParameter("txtQuantity");
	String txtCategory = request.getParameter("cboCategory");

	TblProductCreateNewError error = new TblProductCreateNewError();
	boolean foundError = false;

	try {
	    if (imageSelect.getSize() == 0) {
		error.setProductImageSelectedError("Please choose image for new product.");
		foundError = true;
	    }

	    if (txtName.trim().length() < 5 || txtName.trim().length() > 100) {
		error.setProductNameLengthError("Product name must have 5-100 characters.");
		foundError = true;
	    }

	    float price = 0;
	    if (!txtPrice.isEmpty()) {
		try {
		    price = Float.parseFloat(txtPrice);
		    if (price < 1 || price > 50) {
			error.setProductPriceLengthError("Price must be in 1-50$.");
			foundError = true;
		    }
		} catch (NumberFormatException ex) {
		    error.setProductPriceFormatError("Invalid price. Please try again.");
		    foundError = true;
		}
	    }

	    int quantity = 0;
	    if (!txtQuantity.isEmpty()) {
		try {
		    quantity = Integer.parseInt(txtQuantity);
		    if (quantity < 10 || quantity > 10000) {
			error.setProductQuantityLengthError("Quantity must be 10-10000 quantity.");
			foundError = true;
		    }
		} catch (NumberFormatException ex) {
		    error.setProductQuantityFormatError("Invalid quantity. Please try again.");
		    foundError = true;
		}
	    }

	    String categoryId = null;
	    if (!foundError) {
		TblCategoryDAO dao = new TblCategoryDAO();
		categoryId = dao.getCategoryId(txtCategory);

		if (categoryId == null) {
		    error.setProductCategoryError("Please choose category in the list.");
		    foundError = true;
		}
	    }

	    if (!foundError) {
		String[] type = imageSelect.getContentType().split("/");

		String filePath = SERVER_PATH + txtName + "." + type[1];

		FileUtils.copyFileToServer(imageSelect, filePath);

		TblProductDAO dao = new TblProductDAO();
		boolean result = dao.createNewCake(txtName, filePath, price, quantity, categoryId);

		int productId = dao.getProductId(txtName, filePath, price, quantity, categoryId);

		if (result) {
		    url = siteMap.get(START_UP_CONTROLLER);

		    if (productId != -1) {
			HttpSession session = request.getSession(false);
			if (session != null) {
			    TblUserDTO currentUser = (TblUserDTO) session.getAttribute("CURRENT_USER");
			    if (currentUser != null) {
				TblLogDAO logDAO = new TblLogDAO();
				logDAO.createNewLog(productId, currentUser.getUserEmail());
			    }
			}
		    }
		}
	    } else {
		request.setAttribute("CREATE_ERROR", error);
	    }
	} catch (NamingException ex) {
	    log.error("NamingException " + ex.getMessage());
	} catch (SQLException ex) {
	    log.error("SQLException " + ex.getMessage());
	} catch (IOException ex) {
	    log.error("IOException " + ex.getMessage());
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
