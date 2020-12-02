/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieulm.tbllog;

import hieulm.util.DBHelpers;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import javax.naming.NamingException;

/**
 *
 * @author MinHiu
 */
public class TblLogDAO implements Serializable {

    public boolean createNewLog(int productId, String userEmail) throws NamingException, SQLException {
	Connection con = null;
	PreparedStatement pst = null;

	try {
	    con = DBHelpers.makeConnection();
	    if (con != null) {
		pst = con.prepareStatement("INSERT INTO "
			+ "tblLog(productId, userEmail) "
			+ "VALUES(?, ?)");
		pst.setInt(1, productId);
		pst.setString(2, userEmail);
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

    public boolean checkExistLog(int productId) throws NamingException, SQLException {
	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	try {
	    con = DBHelpers.makeConnection();
	    if (con != null) {
		pst = con.prepareStatement("SELECT logId "
			+ "FROM tblLog "
			+ "WHERE productId = ?");
		pst.setInt(1, productId);

		rs = pst.executeQuery();

		if (rs.next()) {
		    return true;
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
	return false;
    }

    public boolean updateLog(int productId, String userEmail) throws NamingException, SQLException {
	Connection con = null;
	PreparedStatement pst = null;

	try {
	    con = DBHelpers.makeConnection();
	    if (con != null) {
		pst = con.prepareStatement("UPDATE tblLog "
			+ "SET userEmail = ?, logDate = ? "
			+ "WHERE productId = ?");
		pst.setString(1, userEmail);
		Calendar calendar = Calendar.getInstance();
		Timestamp updateDate = new Timestamp(calendar.getTimeInMillis());
		pst.setTimestamp(2, updateDate);
		pst.setInt(3, productId);
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
}
