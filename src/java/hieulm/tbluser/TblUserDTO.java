/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieulm.tbluser;

import java.io.Serializable;

/**
 *
 * @author MinHiu
 */
public class TblUserDTO implements Serializable {

    private String userEmail;
    private String userName;
    private String userPhone;
    private String userAddress;
    private String userRole;
    private String userPassword;

    public TblUserDTO() {
    }

    public TblUserDTO(String userEmail, String userName, String userPhone, String userAddress, String userRole, String userPassword) {
	this.userEmail = userEmail;
	this.userName = userName;
	this.userPhone = userPhone;
	this.userAddress = userAddress;
	this.userRole = userRole;
	this.userPassword = userPassword;
    }

    /**
     * @return the userEmail
     */
    public String getUserEmail() {
	return userEmail;
    }

    /**
     * @param userEmail the userEmail to set
     */
    public void setUserEmail(String userEmail) {
	this.userEmail = userEmail;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
	return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
	this.userName = userName;
    }

    /**
     * @return the userPhone
     */
    public String getUserPhone() {
	return userPhone;
    }

    /**
     * @param userPhone the userPhone to set
     */
    public void setUserPhone(String userPhone) {
	this.userPhone = userPhone;
    }

    /**
     * @return the userAddress
     */
    public String getUserAddress() {
	return userAddress;
    }

    /**
     * @param userAddress the userAddress to set
     */
    public void setUserAddress(String userAddress) {
	this.userAddress = userAddress;
    }

    /**
     * @return the userRole
     */
    public String getUserRole() {
	return userRole;
    }

    /**
     * @param userRole the userRole to set
     */
    public void setUserRole(String userRole) {
	this.userRole = userRole;
    }

    /**
     * @return the userPassword
     */
    public String getUserPassword() {
	return userPassword;
    }

    /**
     * @param userPassword the userPassword to set
     */
    public void setUserPassword(String userPassword) {
	this.userPassword = userPassword;
    }

}
