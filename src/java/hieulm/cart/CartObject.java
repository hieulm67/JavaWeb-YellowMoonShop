/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieulm.cart;

import hieulm.tblproduct.TblProductDTO;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author MinHiu
 */
public class CartObject implements Serializable {

    final String PRODUCT_STATUS_ID = "A";

    private Map<TblProductDTO, Integer> items;

    public Map<TblProductDTO, Integer> getItems() {
	return items;
    }

    private float totalPrice;

    public float getTotalPrice() {
	return totalPrice;
    }

    public void addItemToCart(TblProductDTO product) {
	if (this.items == null) {
	    this.items = new HashMap<>();
	}
	int quantity = 1;

	boolean isExisted = false;

	for (Map.Entry<TblProductDTO, Integer> entry : items.entrySet()) {
	    TblProductDTO dto = entry.getKey();
	    Integer value = entry.getValue();

	    if (dto.getProductId() == product.getProductId()) {
		this.items.put(dto, value + 1);

		isExisted = true;
	    }
	}

	if (!isExisted) {
	    this.items.put(product, quantity);
	}
    }

    public void deleteItemFromCart(TblProductDTO product) {
	if (this.items != null) {
	    for (Map.Entry<TblProductDTO, Integer> entry : items.entrySet()) {
		TblProductDTO dto = entry.getKey();

		if (dto.getProductId() == product.getProductId()) {
		    this.items.remove(dto);
		}
	    }
	}
    }

    public void updateQuantityOfItem(TblProductDTO product, int quantity) {
	for (Map.Entry<TblProductDTO, Integer> entry : items.entrySet()) {
	    TblProductDTO dto = entry.getKey();

	    if (dto.getProductId() == product.getProductId()) {
		this.items.put(dto, quantity);
	    }
	}
    }

    public void updateInformation(TblProductDTO product) {
	for (Map.Entry<TblProductDTO, Integer> entry : items.entrySet()) {
	    TblProductDTO dto = entry.getKey();

	    if (dto.getProductId() == product.getProductId()) {
		dto.setProductName(product.getProductName());
		dto.setProductCatagory(product.getProductCatagory());
		dto.setProductCreateDate(product.getProductCreateDate());
		dto.setProductExpirationDate(product.getProductExpirationDate());
		dto.setProductPrice(product.getProductPrice());
		dto.setProductQuantity(product.getProductQuantity());
		dto.setProductStatus(product.getProductStatus());
	    }
	}
    }

    public void updateProductStatus() {
	for (Map.Entry<TblProductDTO, Integer> entry : items.entrySet()) {
	    TblProductDTO dto = entry.getKey();

	    if (!dto.getProductStatus().equals(PRODUCT_STATUS_ID)) {
		this.items.remove(dto);
	    }
	}
    }

    public void calculateTotalPrice() {
	totalPrice = 0;

	if (this.items != null) {
	    for (Map.Entry<TblProductDTO, Integer> entry : items.entrySet()) {
		TblProductDTO dto = entry.getKey();
		Integer value = entry.getValue();
		totalPrice += value * dto.getProductPrice();
	    }
	}
    }

    public int totalItemsInCart() {
	int total = 0;
	for (Map.Entry<TblProductDTO, Integer> entry : items.entrySet()) {
	    Integer value = entry.getValue();

	    total += value;
	}
	return total;
    }
}
