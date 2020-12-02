<%-- 
    Document   : view-cart-page
    Created on : Oct 16, 2020, 12:27:42 AM
    Author     : MinHiu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cart Page</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
	<link rel="stylesheet" href="./assets/css/custom.css">
    </head>
    <body>
	<c:set var="currentUser" value="${sessionScope.CURRENT_USER}"/>
	<c:set var="userRole" value="${currentUser.userRole}"/>
	<nav class="navbar navbar-expand-lg bg-success">
	    <p class="navbar-brand text-white h1 ml-1">
		Yellow Moon Cake Store
	    </p>
	    <div class="collapse navbar-collapse">
		<ul class="navbar-nav mr-auto">
		    <li class="nav-item">
			<a class="nav-link text-white" href="startUp">Home Page</a>
		    </li>

		    <c:if test="${userRole eq 'M'}">
			<li class="nav-item">
			    <a class="nav-link text-white" href="trackOrderPage">View Order History</a>
			</li>
		    </c:if>
		</ul>

		<div class="navbar-right m-2">
		    <c:if test="${not empty currentUser}">
			<p class="text-white text-center h4">
			    Welcome ${currentUser.userName} <br>
			</p>
			<form class="form-inline float-right" action="logout">
			    <input class="btn btn-danger" type="submit" value="Logout" />
			</form>
		    </c:if>
		    <c:if test="${empty currentUser}">
			<p class="text-white text-center h4">
			    Welcome to our store <br>
			</p>
			<form class="form-inline float-right" action="loginPage">
			    <input class="btn btn-info" type="submit" value="Login" />
			</form>
		    </c:if>
		</div>
	    </div>
	</nav>

	<div class="container h-100mh w-150 mt-3 justify-content-center align-items-center">
	    <c:set var="listProduct" value="${requestScope.CART_ITEMS}"/>
	    <c:if test="${not empty listProduct}">
		<table class="table table-hover">
		    <thead class="thead-light">
			<tr>
			    <th>#</th>
			    <th>Cake Name</th>
			    <th>Price</th>
			    <th>Quantity</th>
			    <th>Total</th>
			    <th>Update Quantity</th>
			    <th>Delete From Cart</th>
			</tr>
		    </thead>
		    <tbody>
			<c:forEach var="item" items="${listProduct}" varStatus="counter">
			    <c:set var="updateError" value="${requestScope.UPDATE_QUANTITY_ERROR}"/>
			    <tr>
			<form action="updateCartQuantity">
			    <td>
				${counter.count}
			    </td>
			    <td>
				${item.key.productName}
			    </td>
			    <td>
				${item.key.productPrice}$
			    </td>
			    <td>
				<input type="text" name="txtQuantity" value="${item.value}"/>
				<c:if test="${not empty updateError}">
				    <c:if test="${requestScope.PRODUCT_ID_ERROR eq item.key.productId}">
					<br><font color="red">${updateError.quantityFormatError}</font>
					<br><font color="red">${updateError.quantityOverRangeError}</font>
				    </c:if>
				</c:if>
			    </td>
			    <td>
				${item.key.productPrice * item.value}$
			    </td>
			    <td>
				<input class="btn btn-outline-warning" type="submit" value="Update" name="btAction" />
				<input type="hidden" name="productId" value="${item.key.productId}" />
			    </td>
			    <td>
				<c:url var="deleteFromCartLink" value="removeCakeFromCart">
				    <c:param name="productId" value="${item.key.productId}"/>
				</c:url>
				<a class="btn btn-outline-danger" href="${deleteFromCartLink}" onclick="return confirm('Are you really want to remove this item?')">Delete</a>
			    </td>
			</form>
			</tr>
		    </c:forEach>
		    <tr class="table-success">
			<td colspan="6">Total Price:</td>
			<td>${requestScope.TOTAL_PRICE}$</td>
		    </tr>
		    </tbody>
		</table>

	    </div>
	    <div class="container h-100mh w-150 mt-2 justify-content-center align-items-center">
		<c:set var="error" value="${requestScope.CHECK_OUT_ERROR}"/>
		<p class="text-danger">${error.productStatusError}</p>
		<p class="text-danger">${error.quantityOverRangeError}</p>
		<div class="card">
		    <div class="card-header">
			Your Information
		    </div>
		    <form action="checkOut" method="POST">
			<div class="card-body">
			    <c:set var="currentUser" value="${sessionScope.CURRENT_USER}"/>

			    <div class="text-danger">
				Please check your cart again before check out!
			    </div>
			    <div class="form-group">
				<label>Name</label>
				<c:if test="${not empty currentUser}">
				    <input type="text" class="form-control m-1" name="txtNameCustomer"
					   value="${currentUser.userName}" required/>
				</c:if>
				<c:if test="${empty currentUser}">
				    <input type="text" class="form-control m-1" name="txtNameCustomer"
					   value="${param.txtNameCustomer}" required/>
				</c:if>
				<small class="text-muted">
				    Must be 5-100 characters.
				</small>
				<p class="text-danger">${error.customerNameLengthError}</p>
			    </div>
			    <div class="form-group">
				<label>Email</label>
				<c:if test="${not empty currentUser}">
				    <input type="text" class="form-control m-1" name="txtEmailCustomer"
					   value="${currentUser.userEmail}" required/>
				</c:if>
				<c:if test="${empty currentUser}">
				    <input type="text" class="form-control m-1" name="txtEmailCustomer"
					   value="${param.txtEmailCustomer}" required/>
				</c:if>
				<small class="text-muted">
				    Example: abc@gmail.com
				</small>
				<p class="text-danger">${error.customerEmailFormatError}</p>
			    </div>
			    <div class="form-group">
				<label>Phone</label>
				<c:if test="${not empty currentUser}">
				    <input type="text" class="form-control m-1" name="txtPhoneCustomer"
					   value="${currentUser.userPhone}" required/>
				</c:if>
				<c:if test="${empty currentUser}">
				    <input type="text" class="form-control m-1" name="txtPhoneCustomer"
					   value="${param.txtPhoneCustomer}" required/>
				</c:if>
				<small class="text-muted">
				    Must be 5 - 20 numbers.
				</small>
				<p class="text-danger">${error.customerPhoneFormatError}</p>
			    </div>
			    <div class="form-group">
				<label>Address</label>
				<c:if test="${not empty currentUser}">
				    <input type="text" class="form-control m-1" name="txtAddressCustomer"
					   value="${currentUser.userAddress}" required/>
				</c:if>
				<c:if test="${empty currentUser}">
				    <input type="text" class="form-control m-1" name="txtAddressCustomer"
					   value="${param.txtAddressCustomer}" required/>
				</c:if>
				<small class="text-muted">
				    Must be 5 - 200 characters.
				</small>
				<p class="text-danger">${error.customerAddressLengthError}</p>
			    </div>

			    <div class="form-group">
				<p class="text-warning">Please choose your payment method:
				    <select name="txtPaymentMethod">
					<option selected="">COD</option>
					<c:if test="${not empty currentUser}">
					    <option>Momo</option>
					    <option>Paypal</option>
					</c:if>
				    </select>
				</p>
				<c:set var="momoError" value="${requestScope.SEND_REQUEST_ERROR_MOMO}"/>
				<p class="text-danger">${momoError}</p>
				<c:set var="sendingError" value="${requestScope.SENDING_ERROR}"/>
				<p class="text-danger">${sendingError}</p>
			    </div>
			</div>
			<div class="card-footer">
			    <input class="btn btn-outline-primary" type="submit" value="Check Out" name="btAction"/>
			    <input class="btn btn-outline-warning" type="reset" value="Reset" />
			</div>
		    </form>
		</div>
	    </c:if>

	    <c:if test="${empty listProduct}">
		Your cart is empty.
	    </c:if>
	</div>
    </body>
</html>
