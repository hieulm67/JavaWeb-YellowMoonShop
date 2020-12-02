/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieulm.tblproduct;

import hieulm.util.DBHelpers;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author MinHiu
 */
public class TblProductDAO implements Serializable {

    final int PRODUCT_PER_PAGE = 20;

    final String EXPRIE_PRODUCT_STATUS_ID = "E";

    final String ACTIVE_PRODUCT_STATUS_NAME = "Active";

    private List<TblProductDTO> listProduct;

    public List<TblProductDTO> getListProductActive() {
	return listProduct;
    }

    public void searchProductForAdmin(int pageNumber) throws NamingException, SQLException {
	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	try {
	    con = DBHelpers.makeConnection();
	    if (con != null) {
		pst = con.prepareStatement("SELECT tblProduct.productId, tblProduct.productName, tblProduct.productImage, tblProduct.productCreateDate, tblProduct.productExpirationDate, tblProduct.productPrice, tblProduct.productQuantity, tblCategory.categoryName, tblStatus.statusName "
			+ "FROM tblProduct, tblCategory, tblStatus "
			+ "WHERE tblProduct.productCategory = tblCategory.categoryId AND tblProduct.productStatus = tblStatus.statusId "
			+ "ORDER BY productCreateDate DESC "
			+ "OFFSET ? ROWS "
			+ "FETCH NEXT ? ROWS ONLY");
		pst.setInt(1, (pageNumber - 1) * PRODUCT_PER_PAGE);
		pst.setInt(2, PRODUCT_PER_PAGE);
		rs = pst.executeQuery();
		while (rs.next()) {
		    if (listProduct == null) {
			listProduct = new ArrayList<>();
		    }

		    int productId = rs.getInt("productId");
		    String productName = rs.getString("productName");
		    String productImage = rs.getString("productImage");
		    float productPrice = rs.getFloat("productPrice");
		    int productQuantity = rs.getInt("productQuantity");
		    String productCategory = rs.getString("categoryName");
		    String productStatus = rs.getString("statusName");
		    Timestamp productCreateDate = rs.getTimestamp("productCreateDate");
		    Timestamp productExpirationDate = rs.getTimestamp("productExpirationDate");

		    TblProductDTO dto = new TblProductDTO(productId, productName, productImage, formatDate(productCreateDate), formatDate(productExpirationDate), productPrice, productQuantity, productCategory, productStatus);

		    listProduct.add(dto);
		}
	    }
	} finally {
	    if (rs != null) {
		rs.close();
	    }
	    if (pst != null) {
		pst.close();
	    }
	    if (con != null) {
		con.close();
	    }
	}
    }

    public void searchProductActive(int pageNumber) throws NamingException, SQLException {
	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	try {
	    con = DBHelpers.makeConnection();
	    if (con != null) {
		pst = con.prepareStatement("SELECT tblProduct.productId, tblProduct.productName, tblProduct.productImage, tblProduct.productCreateDate, tblProduct.productExpirationDate, tblProduct.productPrice, tblProduct.productQuantity, tblCategory.categoryName, tblStatus.statusName "
			+ "FROM tblProduct, tblStatus, tblCategory "
			+ "WHERE statusName = ? AND tblProduct.productStatus = tblStatus.statusId AND tblProduct.productCategory = tblCategory.categoryId "
			+ "ORDER BY productCreateDate DESC "
			+ "OFFSET ? ROWS "
			+ "FETCH NEXT ? ROWS ONLY");
		pst.setString(1, ACTIVE_PRODUCT_STATUS_NAME);
		pst.setInt(2, (pageNumber - 1) * PRODUCT_PER_PAGE);
		pst.setInt(3, PRODUCT_PER_PAGE);
		rs = pst.executeQuery();
		while (rs.next()) {
		    if (listProduct == null) {
			listProduct = new ArrayList<>();
		    }

		    int productId = rs.getInt("productId");
		    String productName = rs.getString("productName");
		    String productImage = rs.getString("productImage");
		    float productPrice = rs.getFloat("productPrice");
		    int productQuantity = rs.getInt("productQuantity");
		    String productCategory = rs.getString("categoryName");
		    String productStatus = rs.getString("statusName");
		    Timestamp productCreateDate = rs.getTimestamp("productCreateDate");
		    Timestamp productExpirationDate = rs.getTimestamp("productExpirationDate");

		    boolean isExpiration = checkExpiration(productExpirationDate);

		    if (!isExpiration) {
			TblProductDTO dto = new TblProductDTO(productId, productName, productImage, formatDate(productCreateDate), formatDate(productExpirationDate), productPrice, productQuantity, productCategory, productStatus);

			listProduct.add(dto);
		    } else {
			updateExpiredCake(productId);
		    }
		}
	    }
	} finally {
	    if (rs != null) {
		rs.close();
	    }
	    if (pst != null) {
		pst.close();
	    }
	    if (con != null) {
		con.close();
	    }
	}
    }

    public void searchProductBaseRequire(String searchName, String searchRange, String searchCategory, int pageNumber) throws NamingException, SQLException {
	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	try {
	    con = DBHelpers.makeConnection();
	    if (con != null) {
		String sql = "SELECT tblProduct.productId, tblProduct.productName, tblProduct.productImage, tblProduct.productCreateDate, tblProduct.productExpirationDate, tblProduct.productPrice, tblProduct.productQuantity, tblCategory.categoryName, tblStatus.statusName "
			+ "FROM tblProduct, tblStatus, tblCategory "
			+ "WHERE statusName = ? AND tblProduct.productStatus = tblStatus.statusId AND tblProduct.productCategory = tblCategory.categoryId ";
		sql = checkSearchName(searchName, sql);
		sql = checkSearchRange(searchRange, sql);
		sql = checkSearchCategory(searchCategory, sql);
		sql += "ORDER BY productCreateDate DESC ";
		sql += "OFFSET " + (pageNumber - 1) * PRODUCT_PER_PAGE + " ROWS ";
		sql += "FETCH NEXT " + PRODUCT_PER_PAGE + " ROWS ONLY";

		pst = con.prepareStatement(sql);
		pst.setString(1, ACTIVE_PRODUCT_STATUS_NAME);
		rs = pst.executeQuery();
		while (rs.next()) {
		    if (listProduct == null) {
			listProduct = new ArrayList<>();
		    }

		    int productId = rs.getInt("productId");
		    String productName = rs.getString("productName");
		    String productImage = rs.getString("productImage");
		    float productPrice = rs.getFloat("productPrice");
		    int productQuantity = rs.getInt("productQuantity");
		    String productCategory = rs.getString("categoryName");
		    String productStatus = rs.getString("statusName");
		    Timestamp productCreateDate = rs.getTimestamp("productCreateDate");
		    Timestamp productExpirationDate = rs.getTimestamp("productExpirationDate");

		    boolean isExpiration = checkExpiration(productExpirationDate);

		    if (!isExpiration) {
			TblProductDTO dto = new TblProductDTO(productId, productName, productImage, formatDate(productCreateDate), formatDate(productExpirationDate), productPrice, productQuantity, productCategory, productStatus);

			listProduct.add(dto);
		    } else {
			updateExpiredCake(productId);
		    }
		}
	    }
	} finally {
	    if (rs != null) {
		rs.close();
	    }
	    if (pst != null) {
		pst.close();
	    }
	    if (con != null) {
		con.close();
	    }
	}
    }

    public void searchProductBaseRequireAdmin(String searchName, String searchRange, String searchCategory, int pageNumber) throws NamingException, SQLException {
	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	try {
	    con = DBHelpers.makeConnection();
	    if (con != null) {
		String sql = "SELECT tblProduct.productId, tblProduct.productName, tblProduct.productImage, tblProduct.productCreateDate, tblProduct.productExpirationDate, tblProduct.productPrice, tblProduct.productQuantity, tblCategory.categoryName, tblStatus.statusName "
			+ "FROM tblProduct, tblCategory, tblStatus "
			+ "WHERE tblProduct.productCategory = tblCategory.categoryId AND tblProduct.productStatus = tblStatus.statusId ";
		sql = checkSearchName(searchName, sql);
		sql = checkSearchRange(searchRange, sql);
		sql = checkSearchCategory(searchCategory, sql);
		sql += "ORDER BY productCreateDate DESC ";
		sql += "OFFSET " + (pageNumber - 1) * PRODUCT_PER_PAGE + " ROWS ";
		sql += "FETCH NEXT " + PRODUCT_PER_PAGE + " ROWS ONLY";

		pst = con.prepareStatement(sql);
		rs = pst.executeQuery();
		while (rs.next()) {
		    if (listProduct == null) {
			listProduct = new ArrayList<>();
		    }

		    int productId = rs.getInt("productId");
		    String productName = rs.getString("productName");
		    String productImage = rs.getString("productImage");
		    float productPrice = rs.getFloat("productPrice");
		    int productQuantity = rs.getInt("productQuantity");
		    String productCategory = rs.getString("categoryName");
		    String productStatus = rs.getString("statusName");
		    Timestamp productCreateDate = rs.getTimestamp("productCreateDate");
		    Timestamp productExpirationDate = rs.getTimestamp("productExpirationDate");

		    TblProductDTO dto = new TblProductDTO(productId, productName, productImage, formatDate(productCreateDate), formatDate(productExpirationDate), productPrice, productQuantity, productCategory, productStatus);

		    listProduct.add(dto);
		}
	    }
	} finally {
	    if (rs != null) {
		rs.close();
	    }
	    if (pst != null) {
		pst.close();
	    }
	    if (con != null) {
		con.close();
	    }
	}
    }

    public int calculatePageActive() throws NamingException, SQLException {
	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	int numberOfPage = 0;

	try {
	    con = DBHelpers.makeConnection();
	    if (con != null) {
		pst = con.prepareStatement("SELECT COUNT(productId) AS numberOfCake "
			+ "FROM tblProduct JOIN tblStatus ON tblProduct.productStatus = tblStatus.statusId "
			+ "WHERE statusName = ?");
		pst.setString(1, ACTIVE_PRODUCT_STATUS_NAME);
		rs = pst.executeQuery();
		if (rs.next()) {
		    int numberOfCake = rs.getInt("numberOfCake");

		    if ((numberOfCake % PRODUCT_PER_PAGE) != 0) {
			numberOfPage = (numberOfCake / PRODUCT_PER_PAGE) + 1;
		    } else {
			numberOfPage = numberOfCake / PRODUCT_PER_PAGE;
		    }
		}
	    }
	} finally {
	    if (rs != null) {
		rs.close();
	    }
	    if (pst != null) {
		pst.close();
	    }
	    if (con != null) {
		con.close();
	    }
	}
	return numberOfPage;
    }

    public int calculatePageAdmin() throws NamingException, SQLException {
	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	int numberOfPage = 0;

	try {
	    con = DBHelpers.makeConnection();
	    if (con != null) {
		pst = con.prepareStatement("SELECT COUNT(productId) AS numberOfCake "
			+ "FROM tblProduct");
		rs = pst.executeQuery();
		if (rs.next()) {
		    int numberOfCake = rs.getInt("numberOfCake");

		    if ((numberOfCake % PRODUCT_PER_PAGE) != 0) {
			numberOfPage = (numberOfCake / PRODUCT_PER_PAGE) + 1;
		    } else {
			numberOfPage = numberOfCake / PRODUCT_PER_PAGE;
		    }
		}
	    }
	} finally {
	    if (rs != null) {
		rs.close();
	    }
	    if (pst != null) {
		pst.close();
	    }
	    if (con != null) {
		con.close();
	    }
	}
	return numberOfPage;
    }

    public int calculatePageBaseRequireAdmin(String searchName, String searchRange, String searchCategory) throws NamingException, SQLException {
	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	int numberOfPage = 0;

	try {
	    con = DBHelpers.makeConnection();
	    if (con != null) {
		String sql = "SELECT COUNT(productId) AS numberOfCake "
			+ "FROM tblProduct, tblCategory "
			+ "WHERE tblProduct.productCategory = tblCategory.categoryId ";
		sql = checkSearchName(searchName, sql);
		sql = checkSearchRange(searchRange, sql);
		sql = checkSearchCategory(searchCategory, sql);

		pst = con.prepareStatement(sql);
		rs = pst.executeQuery();
		if (rs.next()) {
		    int numberOfCake = rs.getInt("numberOfCake");

		    if ((numberOfCake % PRODUCT_PER_PAGE) != 0) {
			numberOfPage = (numberOfCake / PRODUCT_PER_PAGE) + 1;
		    } else {
			numberOfPage = numberOfCake / PRODUCT_PER_PAGE;
		    }
		}
	    }
	} finally {
	    if (rs != null) {
		rs.close();
	    }
	    if (pst != null) {
		pst.close();
	    }
	    if (con != null) {
		con.close();
	    }
	}
	return numberOfPage;
    }

    public int calculatePageBaseRequire(String searchName, String searchRange, String searchCategory) throws NamingException, SQLException {
	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	int numberOfPage = 0;

	try {
	    con = DBHelpers.makeConnection();
	    if (con != null) {
		String sql = "SELECT COUNT(productId) AS numberOfCake "
			+ "FROM tblProduct JOIN tblStatus ON tblProduct.productStatus = tblStatus.statusId "
			+ "WHERE statusName = ? ";
		sql = checkSearchName(searchName, sql);
		sql = checkSearchRange(searchRange, sql);
		sql = checkSearchCategory(searchCategory, sql);

		pst = con.prepareStatement(sql);
		pst.setString(1, ACTIVE_PRODUCT_STATUS_NAME);
		rs = pst.executeQuery();
		if (rs.next()) {
		    int numberOfCake = rs.getInt("numberOfCake");

		    if ((numberOfCake % PRODUCT_PER_PAGE) != 0) {
			numberOfPage = (numberOfCake / PRODUCT_PER_PAGE) + 1;
		    } else {
			numberOfPage = numberOfCake / PRODUCT_PER_PAGE;
		    }
		}
	    }
	} finally {
	    if (rs != null) {
		rs.close();
	    }
	    if (pst != null) {
		pst.close();
	    }
	    if (con != null) {
		con.close();
	    }
	}
	return numberOfPage;
    }

    public boolean createNewCake(String productName, String productImage, float productPrice, int productQuantity, String productCategory) throws NamingException, SQLException {
	Connection con = null;
	PreparedStatement pst = null;

	try {
	    con = DBHelpers.makeConnection();
	    if (con != null) {
		pst = con.prepareStatement("INSERT INTO "
			+ "tblProduct(productName, productImage, productPrice, productQuantity, productCategory) "
			+ "VALUES(?, ?, ?, ?, ?)");
		pst.setString(1, productName);
		pst.setString(2, productImage);
		pst.setFloat(3, productPrice);
		pst.setInt(4, productQuantity);
		pst.setString(5, productCategory);
		int row = pst.executeUpdate();

		if (row > 0) {
		    return true;
		}
	    }
	} finally {
	    if (pst != null) {
		pst.close();
	    }
	    if (con != null) {
		con.close();
	    }
	}
	return false;
    }

    public boolean updateCakeBaseId(int productId, String productName, String productImage, float productPrice, int productQuantity, String productCategory, String productCreatedDate, String productExpireDate, String productStatus) throws SQLException, NamingException {
	Connection con = null;
	PreparedStatement pst = null;

	try {
	    con = DBHelpers.makeConnection();
	    if (con != null) {
		pst = con.prepareStatement("UPDATE tblProduct "
			+ "SET productName = ?, productImage = ?, productPrice = ?, productQuantity = ?, productCategory = ?, productCreateDate = ?, productExpirationDate = ?, productStatus = ? "
			+ "WHERE productId = ?");
		pst.setString(1, productName);
		pst.setString(2, productImage);
		pst.setFloat(3, productPrice);
		pst.setInt(4, productQuantity);
		pst.setString(5, productCategory);
		pst.setTimestamp(6, castDateStringToTimestamp(productCreatedDate));
		pst.setTimestamp(7, castDateStringToTimestamp(productExpireDate));
		pst.setString(8, productStatus);
		pst.setInt(9, productId);
		int row = pst.executeUpdate();

		if (row > 0) {
		    return true;
		}
	    }
	} finally {
	    if (pst != null) {
		pst.close();
	    }
	    if (con != null) {
		con.close();
	    }
	}
	return false;
    }

    private void updateExpiredCake(int productId) throws NamingException, SQLException {
	Connection con = null;
	PreparedStatement pst = null;

	try {
	    con = DBHelpers.makeConnection();
	    if (con != null) {
		pst = con.prepareStatement("UPDATE tblProduct "
			+ "SET productStatus = ? "
			+ "WHERE productId = ?");
		pst.setString(1, EXPRIE_PRODUCT_STATUS_ID);
		pst.setInt(2, productId);

		pst.executeUpdate();

	    }
	} finally {
	    if (pst != null) {
		pst.close();
	    }
	    if (con != null) {
		con.close();
	    }
	}
    }

    public void updateProductQuantity(int productId, int productQuantity) throws NamingException, SQLException {
	Connection con = null;
	PreparedStatement pst = null;

	try {
	    con = DBHelpers.makeConnection();
	    if (con != null) {
		pst = con.prepareStatement("UPDATE tblProduct "
			+ "SET productQuantity = ? "
			+ "WHERE productId = ?");
		pst.setInt(1, productQuantity);
		pst.setInt(2, productId);

		pst.executeUpdate();

	    }
	} finally {
	    if (pst != null) {
		pst.close();
	    }
	    if (con != null) {
		con.close();
	    }
	}
    }

    public TblProductDTO searchProductBaseId(int productId) throws NamingException, SQLException {
	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	try {
	    con = DBHelpers.makeConnection();
	    if (con != null) {
		pst = con.prepareStatement("SELECT tblProduct.productId, tblProduct.productName, tblProduct.productImage, tblProduct.productCreateDate, tblProduct.productExpirationDate, tblProduct.productPrice, tblProduct.productQuantity, tblCategory.categoryName, tblStatus.statusName "
			+ "FROM tblProduct, tblStatus, tblCategory "
			+ "WHERE productId = ? AND statusName = ? AND tblProduct.productStatus = tblStatus.statusId AND tblProduct.productCategory = tblCategory.categoryId");

		pst.setInt(1, productId);
		pst.setString(2, ACTIVE_PRODUCT_STATUS_NAME);
		rs = pst.executeQuery();
		if (rs.next()) {
		    String productName = rs.getString("productName");
		    String productImage = rs.getString("productImage");
		    float productPrice = rs.getFloat("productPrice");
		    int productQuantity = rs.getInt("productQuantity");
		    String productCategory = rs.getString("categoryName");
		    String productStatus = rs.getString("statusName");
		    Timestamp productCreateDate = rs.getTimestamp("productCreateDate");
		    Timestamp productExpirationDate = rs.getTimestamp("productExpirationDate");

		    TblProductDTO dto = new TblProductDTO(productId, productName, productImage, formatDate(productCreateDate), formatDate(productExpirationDate), productPrice, productQuantity, productCategory, productStatus);

		    return dto;
		}

	    }
	} finally {
	    if (rs != null) {
		rs.close();
	    }
	    if (pst != null) {
		pst.close();
	    }
	    if (con != null) {
		con.close();
	    }
	}
	return null;
    }

    public int getProductId(String productName, String productImage, float productPrice, int productQuantity, String productCategory) throws NamingException, SQLException {
	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	try {
	    con = DBHelpers.makeConnection();
	    if (con != null) {
		pst = con.prepareStatement("SELECT productId "
			+ "FROM tblProduct "
			+ "WHERE productName = ? AND productImage = ? AND productPrice = ? AND productQuantity = ? AND productCategory = ?");

		pst.setString(1, productName);
		pst.setString(2, productImage);
		pst.setFloat(3, productPrice);
		pst.setInt(4, productQuantity);
		pst.setString(5, productCategory);
		rs = pst.executeQuery();
		if (rs.next()) {
		    int productId = rs.getInt("productId");
		    return productId;
		}

	    }
	} finally {
	    if (rs != null) {
		rs.close();
	    }
	    if (pst != null) {
		pst.close();
	    }
	    if (con != null) {
		con.close();
	    }
	}
	return -1;
    }

    private String checkSearchName(String searchName, String sql) {
	if (searchName != null) {
	    if (!searchName.isEmpty()) {
		sql += "AND productName LIKE '%" + searchName + "%' ";
	    }
	}
	return sql;
    }

    private String checkSearchRange(String searchRange, String sql) {
	if (searchRange != null) {
	    if (!searchRange.equals("All") && !searchRange.isEmpty()) {
		String[] range = searchRange.split("[\\D]");
		String beginRange = range[0];
		String endRange = range[range.length - 1];

		sql += "AND productPrice >= " + beginRange + " AND productPrice <= " + endRange + " ";
	    }
	}
	return sql;
    }

    private String checkSearchCategory(String searchCategory, String sql) {
	if (searchCategory != null) {
	    if (!searchCategory.equals("All") && !searchCategory.isEmpty()) {
		sql += "AND productCategory = '" + searchCategory + "' ";
	    }
	}
	return sql;
    }

    private String formatDate(Timestamp productDate) {
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	return formatter.format(productDate);
    }

    private boolean checkExpiration(Timestamp productExpirationDate) {
	Calendar calendar = Calendar.getInstance();
	Timestamp now = new Timestamp(calendar.getTimeInMillis());

	return productExpirationDate.before(now);
    }

    private Timestamp castDateStringToTimestamp(String date) {
	String[] dateElement = date.split("/");
	Timestamp castedDate = new Timestamp(Integer.parseInt(dateElement[2]) - 1900, Integer.parseInt(dateElement[1]) - 1, Integer.parseInt(dateElement[0]), 0, 0, 0, 0);
	return castedDate;
    }
}
