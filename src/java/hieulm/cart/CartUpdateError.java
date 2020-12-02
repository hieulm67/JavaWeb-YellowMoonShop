/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieulm.cart;

import java.io.Serializable;

/**
 *
 * @author MinHiu
 */
public class CartUpdateError implements Serializable {

    private String quantityFormatError;
    private String quantityOverRangeError;

    /**
     * @return the quantityFormatError
     */
    public String getQuantityFormatError() {
	return quantityFormatError;
    }

    /**
     * @param quantityFormatError the quantityFormatError to set
     */
    public void setQuantityFormatError(String quantityFormatError) {
	this.quantityFormatError = quantityFormatError;
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

}
