/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieulm.servlet;

import hieulm.tblcategory.TblCategoryDAO;
import hieulm.tbllog.TblLogDAO;
import hieulm.tblproduct.TblProductDAO;
import hieulm.tblproduct.TblProductDTO;
import hieulm.tblproduct.TblProductUpdateError;
import hieulm.tblstatus.TblStatusDAO;
import hieulm.tbluser.TblUserDTO;
import hieulm.util.FileUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
@WebServlet(name = "UpdateCakeServlet", urlPatterns = {"/UpdateCakeServlet"})
public class UpdateCakeServlet extends HttpServlet {

    static Logger log = Logger.getLogger(UpdateCakeServlet.class.getName());

    final String SERVER_PATH = "D:/Study/CN5/LabWeb/Project/YellowMoonShop/img/";
    final String START_UP_CONTROLLER = "startUp";
    final String UPDATE_ERROR_PAGE = "updateCakePage";

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

	String url = siteMap.get(UPDATE_ERROR_PAGE);

	String txtId = request.getParameter("productId");
	Part imageSelect = request.getPart("imageSelectUpdate");
	String txtName = request.getParameter("txtNameUpdate");
	String txtPrice = request.getParameter("txtPriceUpdate");
	String txtQuantity = request.getParameter("txtQuantityUpdate");
	String txtCategory = request.getParameter("cboCategoryUpdate");
	String txtCreatedDate = request.getParameter("txtCreatedDateUpdate");
	String txtExpireDate = request.getParameter("txtExpireDateUpdate");
	String txtStatus = request.getParameter("cboStatusUpdate");

	TblProductUpdateError error = new TblProductUpdateError();

	boolean foundError = false;

	try {

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

	    Date createdDate = null;
	    try {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		createdDate = formatter.parse(txtCreatedDate);
	    } catch (ParseException ex) {
		error.setProductCreatedDateFormatError("Invalid create date. Please try again.");
		foundError = true;
	    }

	    Date expireDate = null;
	    try {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		expireDate = formatter.parse(txtExpireDate);
	    } catch (ParseException ex) {
		error.setProductExpireDateFormatError("Invalid expire date. Please try again.");
		foundError = true;
	    }

	    if (!foundError) {
		if (createdDate.after(expireDate)) {
		    error.setProductExpireDateInputError("Expire day must be after created day.");
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

	    String statusId = null;
	    if (!foundError) {
		TblStatusDAO dao = new TblStatusDAO();
		statusId = dao.getStatusId(txtStatus);

		if (statusId == null) {
		    error.setProductStatusError("Please choose staus in the list.");
		    foundError = true;
		}
	    }

	    if (!foundError) {
		String filePath = getImageFilePath(imageSelect, txtName, txtId);

		if (filePath != null) {
		    TblProductDAO dao = new TblProductDAO();
		    boolean result = dao.updateCakeBaseId(Integer.parseInt(txtId), txtName, filePath, price, quantity, categoryId, txtCreatedDate, txtExpireDate, statusId);

		    if (result) {
			url = siteMap.get(START_UP_CONTROLLER);

			HttpSession session = request.getSession(false);
			if (session != null) {
			    TblUserDTO currentUser = (TblUserDTO) session.getAttribute("CURRENT_USER");
			    if (currentUser != null) {
				TblLogDAO logDAO = new TblLogDAO();
				boolean foundLog = logDAO.checkExistLog(Integer.parseInt(txtId));
				if (foundLog) {
				    logDAO.updateLog(Integer.parseInt(txtId), currentUser.getUserEmail());
				} else {
				    logDAO.createNewLog(Integer.parseInt(txtId), currentUser.getUserEmail());
				}
			    }
			}
		    }
		}
	    } else {
		TblProductDAO dao = new TblProductDAO();
		TblProductDTO dto = dao.searchProductBaseId(Integer.parseInt(txtId));
		if (dto != null) {
		    request.setAttribute("PRODUCT_DETAIL", dto);
		    request.setAttribute("UPDATE_ERROR", error);
		}
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

    private String getImageFilePath(Part imageSelect, String txtName, String txtId) throws NamingException, IOException, SQLException {
	String filePath = null;
	if (imageSelect.getSize() > 0) {

	    String[] type = imageSelect.getContentType().split("/");

	    filePath = SERVER_PATH + txtName + "." + type[1];

	    FileUtils.copyFileToServer(imageSelect, filePath);

	} else {
	    TblProductDAO dao = new TblProductDAO();
	    TblProductDTO dto = dao.searchProductBaseId(Integer.parseInt(txtId));
	    if (dto != null) {
		filePath = dto.getProductImage();
	    }
	}
	return filePath;
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
