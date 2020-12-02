/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieulm.tblproduct;

import java.io.Serializable;

/**
 *
 * @author MinHiu
 */
public class TblProductDTO implements Serializable {

    private int productId;
    private String productName;
    private String productImage;
    private String productCreateDate;
    private String productExpirationDate;
    private float productPrice;
    private int productQuantity;
    private String productCatagory;
    private String productStatus;

    public TblProductDTO() {
    }

    public TblProductDTO(int productId, String productName, String productImage, String productCreateDate, String productExpirationDate, float productPrice, int productQuantity, String productCatagory, String productStatus) {
	this.productId = productId;
	this.productName = productName;
	this.productImage = productImage;
	this.productCreateDate = productCreateDate;
	this.productExpirationDate = productExpirationDate;
	this.productPrice = productPrice;
	this.productQuantity = productQuantity;
	this.productCatagory = productCatagory;
	this.productStatus = productStatus;
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
     * @return the productCatagory
     */
    public String getProductCatagory() {
	return productCatagory;
    }

    /**
     * @param productCatagory the productCatagory to set
     */
    public void setProductCatagory(String productCatagory) {
	this.productCatagory = productCatagory;
    }

    /**
     * @return the productStatus
     */
    public String getProductStatus() {
	return productStatus;
    }

    /**
     * @param productStatus the productStatus to set
     */
    public void setProductStatus(String productStatus) {
	this.productStatus = productStatus;
    }

    /**
     * @return the productImage
     */
    public String getProductImage() {
	return productImage;
    }

    /**
     * @param productImage the productImage to set
     */
    public void setProductImage(String productImage) {
	this.productImage = productImage;
    }

    /**
     * @return the productCreateDate
     */
    public String getProductCreateDate() {
	return productCreateDate;
    }

    /**
     * @param productCreateDate the productCreateDate to set
     */
    public void setProductCreateDate(String productCreateDate) {
	this.productCreateDate = productCreateDate;
    }

    /**
     * @return the productExpirationDate
     */
    public String getProductExpirationDate() {
	return productExpirationDate;
    }

    /**
     * @param productExpirationDate the productExpirationDate to set
     */
    public void setProductExpirationDate(String productExpirationDate) {
	this.productExpirationDate = productExpirationDate;
    }
}
