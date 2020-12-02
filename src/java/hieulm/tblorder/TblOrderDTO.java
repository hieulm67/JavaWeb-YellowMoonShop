/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieulm.tblorder;

import java.io.Serializable;

/**
 *
 * @author MinHiu
 */
public class TblOrderDTO implements Serializable {

    private String orderId;
    private float orderTotalPrice;
    private String orderDate;
    private String userEmail;
    private String userName;
    private String userPhone;
    private String userAddress;

    public TblOrderDTO() {
    }

    public TblOrderDTO(String orderId, float orderTotalPrice, String orderDate, String userEmail, String userName, String userPhone, String userAddress) {
	this.orderId = orderId;
	this.orderTotalPrice = orderTotalPrice;
	this.orderDate = orderDate;
	this.userEmail = userEmail;
	this.userName = userName;
	this.userPhone = userPhone;
	this.userAddress = userAddress;
    }

    /**
     * @return the orderId
     */
    public String getOrderId() {
	return orderId;
    }

    /**
     * @param orderId the orderId to set
     */
    public void setOrderId(String orderId) {
	this.orderId = orderId;
    }

    /**
     * @return the orderTotalPrice
     */
    public float getOrderTotalPrice() {
	return orderTotalPrice;
    }

    /**
     * @param orderTotalPrice the orderTotalPrice to set
     */
    public void setOrderTotalPrice(float orderTotalPrice) {
	this.orderTotalPrice = orderTotalPrice;
    }

    /**
     * @return the orderDate
     */
    public String getOrderDate() {
	return orderDate;
    }

    /**
     * @param orderDate the orderDate to set
     */
    public void setOrderDate(String orderDate) {
	this.orderDate = orderDate;
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

}
