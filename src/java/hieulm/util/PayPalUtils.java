/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieulm.util;

import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.AddressPortable;
import com.paypal.orders.AmountBreakdown;
import com.paypal.orders.AmountWithBreakdown;
import com.paypal.orders.ApplicationContext;
import com.paypal.orders.Item;
import com.paypal.orders.Money;
import com.paypal.orders.Name;
import com.paypal.orders.Order;
import com.paypal.orders.OrderRequest;
import com.paypal.orders.OrdersCaptureRequest;
import com.paypal.orders.OrdersCreateRequest;
import com.paypal.orders.PurchaseUnitRequest;
import com.paypal.orders.ShippingDetail;
import hieulm.tblproduct.TblProductDTO;
import hieulm.tbluser.TblUserDTO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author MinHiu
 */
public class PayPalUtils {

    static String clientId = "AWwKhYsLNM2lnUmTYAkvajfKvFmO62__1HO1aoNcqfZkBsINn1igj7XpTaMtMr6Xsr48Q8PYmyNPHsKm";
    static String secret = "ELNkqTptOeLGsQutgC36ux3UiPehF4BI_G5ng8JTo_8kG--3x8IZW_ixeBTl6ysx2vmpWAPvP9f4oAAT";
    static String url = "http://localhost:8084/YellowMoonShop/processPayPalPayment";

    // Creating a sandbox environment
    private static PayPalEnvironment environment = new PayPalEnvironment.Sandbox(clientId, secret);

    // Creating a client for the environment
    public static PayPalHttpClient client = new PayPalHttpClient(environment);

    private static OrderRequest buildRequestBody(float totalPrice, Map<TblProductDTO, Integer> items, TblUserDTO currentUser) {
	OrderRequest orderRequest = new OrderRequest();
	orderRequest.checkoutPaymentIntent("CAPTURE");

	ApplicationContext applicationContext = new ApplicationContext().brandName("YELLOW MOON SHOP").landingPage("BILLING")
		.cancelUrl(url).returnUrl(url).userAction("CONTINUE")
		.shippingPreference("SET_PROVIDED_ADDRESS");
	orderRequest.applicationContext(applicationContext);

	List<PurchaseUnitRequest> purchaseUnitRequests = new ArrayList<PurchaseUnitRequest>();
	PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest()
		.amountWithBreakdown(new AmountWithBreakdown().currencyCode("USD").value(Float.toString(totalPrice))
			.amountBreakdown(new AmountBreakdown().itemTotal(new Money().currencyCode("USD").value(Float.toString(totalPrice)))
				.shipping(new Money().currencyCode("USD").value("0"))
				.handling(new Money().currencyCode("USD").value("0"))
				.taxTotal(new Money().currencyCode("USD").value("0"))))
		.items(new ArrayList<Item>() {
		    {
			for (Map.Entry<TblProductDTO, Integer> entry : items.entrySet()) {
			    TblProductDTO key = entry.getKey();
			    Integer value = entry.getValue();

			    add(new Item().name(key.getProductName())
				    .unitAmount(new Money().currencyCode("USD").value(Float.toString(key.getProductPrice())))
				    .tax(new Money().currencyCode("USD").value("0")).quantity(Integer.toString(value)));

			}
		    }
		})
		.shippingDetail(new ShippingDetail().name(new Name().fullName(currentUser.getUserName()))
			.addressPortable(new AddressPortable().addressLine1(currentUser.getUserAddress())
				.adminArea2("VN").adminArea1("TPHCM").postalCode("700000").countryCode("VN")));
	purchaseUnitRequests.add(purchaseUnitRequest);
	orderRequest.purchaseUnits(purchaseUnitRequests);

	return orderRequest;
    }

    public static HttpResponse<Order> createOrder(float totalPrice, Map<TblProductDTO, Integer> items, TblUserDTO currentUser) throws IOException {
	OrdersCreateRequest request = new OrdersCreateRequest();
	request.header("prefer", "return=representation");
	request.requestBody(buildRequestBody(totalPrice, items, currentUser));
	HttpResponse<Order> response = client.execute(request);
	return response;
    }

    public static HttpResponse<Order> captureOrder(String orderId) throws IOException {
	OrdersCaptureRequest request = new OrdersCaptureRequest(orderId);
	request.requestBody(new OrderRequest());
	HttpResponse<Order> response = client.execute(request);
	return response;
    }
}
