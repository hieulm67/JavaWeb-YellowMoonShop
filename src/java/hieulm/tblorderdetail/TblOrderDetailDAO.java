package hieulm.tblorderdetail;

import hieulm.util.DBHelpers;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author MinHiu
 */
public class TblOrderDetailDAO implements Serializable {

    private List<TblOrderDetailDTO> listItems;

    public List<TblOrderDetailDTO> getListItems() {
	return listItems;
    }

    public boolean insertOrderDetail(String orderId, int productId, float productPrice, int productQuantity) throws NamingException, SQLException {
	Connection con = null;
	PreparedStatement pst = null;

	try {
	    con = DBHelpers.makeConnection();
	    if (con != null) {
		pst = con.prepareStatement("INSERT INTO "
			+ "tblOrderDetail(orderId, productId, productPrice, productQuantity) "
			+ "VALUES(?, ?, ?, ?)");
		pst.setString(1, orderId);
		pst.setInt(2, productId);
		pst.setFloat(3, productPrice);
		pst.setInt(4, productQuantity);
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

    public void searchByOderId(String orderId) throws NamingException, SQLException {
	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	try {
	    con = DBHelpers.makeConnection();
	    if (con != null) {
		pst = con.prepareStatement("SELECT tblOrderDetail.detailId, tblOrderDetail.productId, tblProduct.productName, tblOrderDetail.productPrice, tblOrderDetail.productQuantity "
			+ "FROM tblOrderDetail, tblProduct "
			+ "WHERE orderId = ? AND tblOrderDetail.productId = tblProduct.productId");
		pst.setString(1, orderId);
		rs = pst.executeQuery();
		while (rs.next()) {
		    if (listItems == null) {
			listItems = new ArrayList<>();
		    }

		    int detailId = rs.getInt("detailId");
		    int productId = rs.getInt("productId");
		    String productName = rs.getString("productName");
		    float productPrice = rs.getFloat("productPrice");
		    int productQuantity = rs.getInt("productQuantity");

		    TblOrderDetailDTO dto = new TblOrderDetailDTO(detailId, orderId, productId, productName, productPrice, productQuantity);
		    listItems.add(dto);
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
}
