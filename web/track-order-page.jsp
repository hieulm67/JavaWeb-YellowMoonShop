<%-- 
    Document   : track-order-page
    Created on : Oct 17, 2020, 12:49:41 PM
    Author     : MinHiu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Track Order Page</title>
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
	    <form class="form-inline justify-content-center align-items-center" action="trackOrder">
		<input class="form-control" type="text" name="txtOrderId" placeholder="Enter order ID" value="${param.txtOrderId}" />
		<input class="btn btn-outline-success form-control ml-1" type="submit" value="Search Order" />
	    </form>

	    <c:if test="${not empty param.txtOrderId}">
		<c:set var="orderFounded" value="${requestScope.ORDER_FOUNDED}"/>
		<c:if test="${not empty orderFounded}">
		    <div class="card">
			<div class="card-header font-weight-bold">
			    Your Cart ID: ${orderFounded.orderId} 
			</div>
			<div class="card-body">
			    <div>
				<label class="font-weight-bold">Name:</label>
				<input type="text" class="form-control m-1" name="txtNameCustomer"
				       value="${orderFounded.userName}" disabled/>
			    </div>
			    <div>
				<label class="font-weight-bold">Email:</label>
				<input type="text" class="form-control m-1" name="txtEmailCustomer"
				       value="${orderFounded.userEmail}" disabled/>
			    </div>
			    <div>
				<label class="font-weight-bold">Phone:</label>
				<input type="text" class="form-control m-1" name="txtPhoneCustomer"
				       value="${orderFounded.userPhone}" disabled/>
			    </div>
			    <div>
				<label class="font-weight-bold">Address:</label>
				<input type="text" class="form-control m-1" name="txtAddressCustomer"
				       value="${orderFounded.userAddress}" disabled/>
			    </div>
			    <div>
				<label class="font-weight-bold">Order Day:</label>
				<input type="text" class="form-control m-1" name="txtAddressCustomer"
				       value="${orderFounded.orderDate}" disabled/>
			    </div>
			</div>
		    </div>
		</c:if>

		<c:set var="listProduct" value="${requestScope.ORDER_ITEMS}"/>
		<c:if test="${not empty listProduct}">
		    <table class="table table-hover mt-2 justify-content-center align-items-center">
			<thead class="thead-light">
			    <tr>
				<th>#</th>
				<th>Cake Name</th>
				<th>Price</th>
				<th>Quantity</th>
				<th>Total</th>
			    </tr>
			</thead>
			<tbody>
			    <c:forEach var="item" items="${listProduct}" varStatus="counter">
				<tr>
				    <td>
					${counter.count}
				    </td>
				    <td>
					${item.productName}
				    </td>
				    <td>
					${item.productPrice}$
				    </td>
				    <td>
					${item.productQuantity}
				    </td>
				    <td>
					${item.productPrice * item.productQuantity}$
				    </td>
				    </form>
				</tr>
			    </c:forEach>
			    <tr class="table-success">
				<td colspan="4">Total Price:</td>
				<td>${orderFounded.orderTotalPrice}$</td>
			    </tr>
			</tbody>
		    </table>
		</c:if>

		<c:if test="${empty listProduct}">
		    Your order is missing.
		</c:if>
	    </c:if>
	</div>
    </body>
</html>
