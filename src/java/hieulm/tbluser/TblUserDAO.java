/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieulm.tbluser;

import hieulm.util.DBHelpers;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author MinHiu
 */
public class TblUserDAO implements Serializable {

    final String USER_ACTIVE_STATUS_NAME = "Active";

    public TblUserDTO checkLogin(String email, String password) throws SQLException, NamingException {
	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	try {
	    con = DBHelpers.makeConnection();
	    if (con != null) {
		pst = con.prepareStatement("SELECT userEmail, userPassword, userName, userPhone, userAddress, userRole "
			+ "FROM tblUser JOIN tblStatus ON tblUser.userStatus = tblStatus.statusId "
			+ "WHERE userEmail = ? AND userPassword = ? AND tblStatus.statusName = ?");
		pst.setString(1, email);
		pst.setString(2, password);
		pst.setString(3, USER_ACTIVE_STATUS_NAME);
		rs = pst.executeQuery();

		if (rs.next()) {
		    String name = rs.getString("userName");
		    String phone = rs.getString("userPhone");
		    String address = rs.getString("userAddress");
		    String role = rs.getString("userRole");

		    TblUserDTO dto = new TblUserDTO(email, name, phone, address, role, password);
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

    public TblUserDTO checkExsited(String email) throws SQLException, NamingException {
	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	try {
	    con = DBHelpers.makeConnection();
	    if (con != null) {
		pst = con.prepareStatement("SELECT userEmail, userPassword, userName, userPhone, userAddress, userRole "
			+ "FROM tblUser JOIN tblStatus ON tblUser.userStatus = tblStatus.statusId "
			+ "WHERE userEmail = ? AND tblStatus.statusName = ?");
		pst.setString(1, email);
		pst.setString(2, USER_ACTIVE_STATUS_NAME);
		rs = pst.executeQuery();

		if (rs.next()) {
		    String name = rs.getString("userName");
		    String password = rs.getString("userPassword");
		    String phone = rs.getString("userPhone");
		    String address = rs.getString("userAddress");
		    String role = rs.getString("userRole");

		    TblUserDTO dto = new TblUserDTO(email, name, phone, address, role, password);
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
    
    private List<TblUserDTO> listMembers;

    public List<TblUserDTO> getListMembers() {
	return listMembers;
    }
    
    public void loadMembers() throws SQLException, NamingException {
	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	try {
	    con = DBHelpers.makeConnection();
	    if (con != null) {
		pst = con.prepareStatement("SELECT userEmail, userPassword, userName, userPhone, userAddress, roleName "
			+ "FROM tblUser, tblStatus, tblRole "
			+ "WHERE tblStatus.statusName = ? AND tblUser.userStatus = tblStatus.statusId AND tblUser.userRole = tblRole.roleId");
		pst.setString(1, USER_ACTIVE_STATUS_NAME);
		rs = pst.executeQuery();

		while (rs.next()) {
		    
		    if(listMembers == null){
			listMembers = new ArrayList<>();
		    }
		    
		    String email = rs.getString(("userEmail"));
		    String name = rs.getString("userName");
		    String phone = rs.getString("userPhone");
		    String address = rs.getString("userAddress");
		    String role = rs.getString("roleName");

		    TblUserDTO dto = new TblUserDTO(email, name, phone, address, role, "");
		    
		    listMembers.add(dto);
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
