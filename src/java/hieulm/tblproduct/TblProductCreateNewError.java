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
public class TblProductCreateNewError implements Serializable {

    private String productNameLengthError;
    private String productImageSelectedError;
    private String productPriceLengthError;
    private String productPriceFormatError;
    private String productQuantityLengthError;
    private String productQuantityFormatError;
    private String productCategoryError;

    /**
     * @return the productNameLengthError
     */
    public String getProductNameLengthError() {
	return productNameLengthError;
    }

    /**
     * @param productNameLengthError the productNameLengthError to set
     */
    public void setProductNameLengthError(String productNameLengthError) {
	this.productNameLengthError = productNameLengthError;
    }

    /**
     * @return the productImageSelectedError
     */
    public String getProductImageSelectedError() {
	return productImageSelectedError;
    }

    /**
     * @param productImageSelectedError the productImageSelectedError to set
     */
    public void setProductImageSelectedError(String productImageSelectedError) {
	this.productImageSelectedError = productImageSelectedError;
    }

    /**
     * @return the productPriceLengthError
     */
    public String getProductPriceLengthError() {
	return productPriceLengthError;
    }

    /**
     * @param productPriceLengthError the productPriceLengthError to set
     */
    public void setProductPriceLengthError(String productPriceLengthError) {
	this.productPriceLengthError = productPriceLengthError;
    }

    /**
     * @return the productPriceFormatError
     */
    public String getProductPriceFormatError() {
	return productPriceFormatError;
    }

    /**
     * @param productPriceFormatError the productPriceFormatError to set
     */
    public void setProductPriceFormatError(String productPriceFormatError) {
	this.productPriceFormatError = productPriceFormatError;
    }

    /**
     * @return the productQuantityLengthError
     */
    public String getProductQuantityLengthError() {
	return productQuantityLengthError;
    }

    /**
     * @param productQuantityLengthError the productQuantityLengthError to set
     */
    public void setProductQuantityLengthError(String productQuantityLengthError) {
	this.productQuantityLengthError = productQuantityLengthError;
    }

    /**
     * @return the productQuantityFormatError
     */
    public String getProductQuantityFormatError() {
	return productQuantityFormatError;
    }

    /**
     * @param productQuantityFormatError the productQuantityFormatError to set
     */
    public void setProductQuantityFormatError(String productQuantityFormatError) {
	this.productQuantityFormatError = productQuantityFormatError;
    }

    /**
     * @return the productCategoryError
     */
    public String getProductCategoryError() {
	return productCategoryError;
    }

    /**
     * @param productCategoryError the productCategoryError to set
     */
    public void setProductCategoryError(String productCategoryError) {
	this.productCategoryError = productCategoryError;
    }
}
