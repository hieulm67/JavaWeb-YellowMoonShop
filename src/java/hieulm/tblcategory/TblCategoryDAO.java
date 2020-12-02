/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieulm.tblcategory;

import hieulm.util.DBHelpers;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;

/**
 *
 * @author MinHiu
 */
public class TblCategoryDAO implements Serializable {

    public String getCategoryId(String categoryName) throws NamingException, SQLException {
	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	try {
	    con = DBHelpers.makeConnection();
	    if (con != null) {
		pst = con.prepareStatement("SELECT categoryId "
			+ "FROM tblCategory "
			+ "WHERE categoryName = ?");
		pst.setString(1, categoryName);
		rs = pst.executeQuery();
		if (rs.next()) {
		    String categoryId = rs.getString("categoryId");
		    return categoryId;
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
}
