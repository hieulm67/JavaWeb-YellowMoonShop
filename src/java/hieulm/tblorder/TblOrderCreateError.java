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
public class TblOrderCreateError implements Serializable {

    private String customerNameLengthError;
    private String customerEmailFormatError;
    private String customerPhoneFormatError;
    private String customerAddressLengthError;
    private String quantityOverRangeError;
    private String productStatusError;

    /**
     * @return the customerNameLengthError
     */
    public String getCustomerNameLengthError() {
	return customerNameLengthError;
    }

    /**
     * @param customerNameLengthError the customerNameLengthError to set
     */
    public void setCustomerNameLengthError(String customerNameLengthError) {
	this.customerNameLengthError = customerNameLengthError;
    }

    /**
     * @return the customerEmailFormatError
     */
    public String getCustomerEmailFormatError() {
	return customerEmailFormatError;
    }

    /**
     * @param customerEmailFormatError the customerEmailFormatError to set
     */
    public void setCustomerEmailFormatError(String customerEmailFormatError) {
	this.customerEmailFormatError = customerEmailFormatError;
    }

    /**
     * @return the customerPhoneFormatError
     */
    public String getCustomerPhoneFormatError() {
	return customerPhoneFormatError;
    }

    /**
     * @param customerPhoneFormatError the customerPhoneFormatError to set
     */
    public void setCustomerPhoneFormatError(String customerPhoneFormatError) {
	this.customerPhoneFormatError = customerPhoneFormatError;
    }

    /**
     * @return the customerAddressLengthError
     */
    public String getCustomerAddressLengthError() {
	return customerAddressLengthError;
    }

    /**
     * @param customerAddressLengthError the customerAddressLengthError to set
     */
    public void setCustomerAddressLengthError(String customerAddressLengthError) {
	this.customerAddressLengthError = customerAddressLengthError;
    }

    /**
     * @return the quantityOverRangeError
     */
    public String getQuantityOverRangeError() {
	return quantityOverRangeError;
    }

    /**
     * @param quantityOverRangeError the quantityOverRangeError to set
     */
    public void setQuantityOverRangeError(String quantityOverRangeError) {
	this.quantityOverRangeError = quantityOverRangeError;
    }

    /**
     * @return the productStatusError
     */
    public String getProductStatusError() {
	return productStatusError;
    }

    /**
     * @param productStatusError the productStatusError to set
     */
    public void setProductStatusError(String productStatusError) {
	this.productStatusError = productStatusError;
    }
}
