/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieulm.tblorder;

import hieulm.util.DBHelpers;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import javax.naming.NamingException;

/**
 *
 * @author MinHiu
 */
public class TblOrderDAO implements Serializable {

    public boolean createNewOrder(float totalPrice, String userEmail, String userName, String userPhone, String userAddress) throws NamingException, SQLException {
	Connection con = null;
	PreparedStatement pst = null;

	try {
	    con = DBHelpers.makeConnection();
	    if (con != null) {
		pst = con.prepareStatement("INSERT INTO "
			+ "tblOrder(orderId, totalPrice, userEmail, userName, userPhone, userAddress) "
			+ "VALUES(default, ?, ?, ?, ?, ?)");
		pst.setFloat(1, totalPrice);
		pst.setString(2, userEmail);
		pst.setString(3, userName);
		pst.setString(4, userPhone);
		pst.setString(5, userAddress);
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

    public String getOrderId(String userEmail, String userName, String userPhone, String userAddress) throws NamingException, SQLException {
	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	try {
	    con = DBHelpers.makeConnection();
	    if (con != null) {
		pst = con.prepareStatement("SELECT TOP 1 orderId "
			+ "FROM tblOrder "
			+ "WHERE userEmail = ? AND userName = ? AND userPhone = ? AND userAddress = ? "
			+ "ORDER BY orderDate DESC");
		pst.setString(1, userEmail);
		pst.setString(2, userName);
		pst.setString(3, userPhone);
		pst.setString(4, userAddress);
		rs = pst.executeQuery();
		if (rs.next()) {
		    String orderId = rs.getString("orderId");
		    return orderId;
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

    public TblOrderDTO searchOrder(String orderId, String userEmail) throws NamingException, SQLException {
	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	try {
	    con = DBHelpers.makeConnection();
	    if (con != null) {
		pst = con.prepareStatement("SELECT totalPrice, orderDate, userName, userPhone, userAddress "
			+ "FROM tblOrder "
			+ "WHERE orderId= ? AND userEmail = ?");
		pst.setString(1, orderId);
		pst.setString(2, userEmail);
		rs = pst.executeQuery();
		if (rs.next()) {
		    float totalPrice = rs.getFloat("totalPrice");
		    Timestamp orderDate = rs.getTimestamp("orderDate");
		    String userName = rs.getString("userName");
		    String userPhone = rs.getString("userPhone");
		    String userAddress = rs.getString("userAddress");

		    TblOrderDTO dto = new TblOrderDTO(orderId, totalPrice, formatDate(orderDate), userEmail, userName, userPhone, userAddress);
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

    private String formatDate(Timestamp orderDate) {
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	return formatter.format(orderDate);
    }
}
