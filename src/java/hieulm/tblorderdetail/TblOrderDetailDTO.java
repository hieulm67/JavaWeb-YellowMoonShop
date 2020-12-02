/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieulm.tblorderdetail;

import java.io.Serializable;

/**
 *
 * @author MinHiu
 */
public class TblOrderDetailDTO implements Serializable {

    private int orderDetailId;
    private String orderId;
    private int productId;
    private String productName;
    private float productPrice;
    private int productQuantity;

    public TblOrderDetailDTO() {
    }

    public TblOrderDetailDTO(int orderDetailId, String orderId, int productId, String productName, float productPrice, int productQuantity) {
	this.orderDetailId = orderDetailId;
	this.orderId = orderId;
	this.productId = productId;
	this.productName = productName;
	this.productPrice = productPrice;
	this.productQuantity = productQuantity;
    }

    /**
     * @return the orderDetailId
     */
    public int getOrderDetailId() {
	return orderDetailId;
    }

    /**
     * @param orderDetailId the orderDetailId to set
     */
    public void setOrderDetailId(int orderDetailId) {
	this.orderDetailId = orderDetailId;
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
     * @return the productId
     */
    public int getProductId() {
	return productId;
    }

    /**
     * @param productId the productId to set
     */
    public void setProductId(int productId) {
	this.productId = productId;
    }

    /**
     * @return the productPrice
     */
    public float getProductPrice() {
	return productPrice;
    }

    /**
     * @param productPrice the productPrice to set
     */
    public void setProductPrice(float productPrice) {
	this.productPrice = productPrice;
    }

    /**
     * @return the productQuantity
     */
    public int getProductQuantity() {
	return productQuantity;
    }

    /**
     * @param productQuantity the productQuantity to set
     */
    public void setProductQuantity(int productQuantity) {
	this.productQuantity = productQuantity;
    }

    /**
     * @return the productName
     */
    public String getProductName() {
	return productName;
    }

    /**
     * @param productName the productName to set
     */
    public void setProductName(String productName) {
	this.productName = productName;
    }

}
