/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieulm.tblstatus;

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
public class TblStatusDAO implements Serializable {

    public String getStatusId(String statusName) throws NamingException, SQLException {
	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	try {
	    con = DBHelpers.makeConnection();
	    if (con != null) {
		pst = con.prepareStatement("SELECT statusId "
			+ "FROM tblStatus "
			+ "WHERE statusName = ?");
		pst.setString(1, statusName);
		rs = pst.executeQuery();
		if (rs.next()) {
		    String statusId = rs.getString("statusId");
		    return statusId;
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
