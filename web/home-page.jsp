<%-- 
    Document   : homepage
    Created on : Oct 6, 2020, 9:30:56 PM
    Author     : MinHiu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
	<link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css" integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous"/>
	<link rel="stylesheet" href="./assets/css/custom.css">
	<title>Home Page</title>
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
		    <li class="nav-item active">
			<a class="nav-link disabled text-white font-weight-bold" href="startUp">Home Page</a>
		    </li>

		    <c:if test="${userRole eq 'M'}">
			<li class="nav-item">
			    <a class="nav-link text-white" href="trackOrderPage">View Order History</a>
			</li>
		    </c:if>

		    <c:if test="${userRole eq 'A'}">
			<li class="nav-item">
			    <a class="nav-link text-white" href="createCakePage">Create New Cake</a>
			</li>
		    </c:if>
		    <c:if test="${userRole eq 'A'}">
			<li class="nav-item">
			    <a class="nav-link text-white" href="viewMember">View Member</a>
			</li>
		    </c:if>

		</ul>

		<div class="navbar-right m-2">
		    <c:if test="${not empty currentUser}">
			<p class="text-white text-center h4">
			    Welcome ${currentUser.userName} <br>
			</p>
			<c:if test="${userRole ne 'A'}">
			    <div class="d-inline-block">
				<i class="fa fa-shopping-cart" style="height: 20px; width: 20px"></i><span class="badge badge-warning" id="lblCartCount">${sessionScope.NUMBER_ITEMS}</span>
			    </div>
			    <div class="d-inline-block">
				<a href="loadCart" class="icon-shopping-cart text-white btn btn-primary ml-2" style="font-size: 16px">Your cart</a>
			    </div>
			</c:if>
			<form class="form-inline float-right ml-1" action="logout">
			    <input class="btn btn-danger" type="submit" value="Logout" />
			</form>
		    </c:if>
		    <c:if test="${empty currentUser}">
			<p class="text-white text-center h4">
			    Welcome to our store <br>
			</p>
			<div class="d-inline-block">
			    <i class="fa fa-shopping-cart" style="height: 20px; width: 20px"></i><span class="badge badge-warning" id="lblCartCount">${sessionScope.NUMBER_ITEMS}</span>
			</div>
			<div class="d-inline-block">
			    <a href="loadCart" class="icon-shopping-cart text-white btn btn-primary ml-2" style="font-size: 16px">Your cart</a>
			</div>
			<form class="form-inline float-right" action="loginPage">
			    <input class="btn btn-info" type="submit" value="Login" />
			</form>
		    </c:if>
		</div>
	    </div>
	</nav>

	<div class="container h-100 w-150 mt-2 justify-content-center align-items-center">
	    <form class="form-inline justify-content-center align-items-center mb-2" action="searchCake">
		<label class="m-1">Search name:</label>
		<input class="form-control m-1" type="text" name="txtCakeName" value="${param.txtCakeName}" />

		<label class="m-1">Search price:</label>

		<select id="cboRange" class="form-control m-1" name="txtRange" value="${param.txtRange}">
		    <option value="All">All</option>
		    <option value="0$ - 5$">0$ - 5$</option>
		    <option value="5$ - 10$">5$ - 10$</option>
		    <option value="10$ - 20$">10$ - 20$</option>
		    <option value="20$ - 25$">20$ - 25$</option>
		    <option value="25$ - 50$">25$ - 50$</option>
		</select>

		<label class="m-1">Search category</label>
		<select id="cboCategory" class="form-control m-1" name="txtCategory" value="${param.txtCategory}">
		    <option value="All">All</option>
		    <option value="Cake">Cake</option>
		    <option value="Combo">Combo</option>
		</select>

		<input class="form-control btn btn-outline-primary" type="submit" value="Search" name="txtSearch" />
	    </form>

	    <c:set var="listProduct" value="${requestScope.LIST_PRODUCT}"/>
	    <c:set var="currentPage" value="${requestScope.CURRENT_PAGE}" />
	    <c:if test="${not empty listProduct}">
		<div class="row justify-content-start">
		    <c:forEach var="product" items="${listProduct}">

			<div class="d-flex col-sm-3 p-1 mb-1">
			    <div class="card">
				<div class="card-header">
				    ${product.productName}
				</div>
				<div class="card-body">
				    <img class="img-responsive card-img-top m-1" style="height: auto; width: 100%" src="loadImage?filePath=${product.productImage}"/><br>
				    <div style="margin-top: auto">
					<p>
					    <font style="font-weight: bold">Price:</font> ${product.productPrice}$
					</p>

					<p>
					    <font style="font-weight: bold">Quantity:</font> ${product.productQuantity}
					</p>

					<p>
					    <font style="font-weight: bold">Category:</font> ${product.productCatagory}
					</p>

					<p>
					    <font style="font-weight: bold">Expiration Date:</font> ${product.productExpirationDate}
					</p>

					<c:if test="${userRole eq 'A'}">
					    <p>
						<font style="font-weight: bold">Created Date:</font> ${product.productCreateDate}
					    </p>

					    <p>
						<font style="font-weight: bold">Status:</font> ${product.productStatus}
					    </p>
					</c:if>
				    </div>
				</div>
				<div class="card-footer">
				    <c:if test="${userRole  ne 'A'}">
					<c:set var="addToCartLink" value="addToCart?productId=${product.productId}&txtPage=${currentPage}&txtCakeName=${param.txtCakeName}&txtRange=${param.txtRange}&txtCategory=${param.txtCategory}"/>
					<a class="btn btn-outline-primary float-right" href="${addToCartLink}">Add to cart</a>
				    </c:if>
				    <c:if test="${userRole eq 'A'}">
					<c:url var="updateCakeLink" value="loadUpdatePage">
					    <c:param name="productId" value="${product.productId}"/>
					</c:url>
					<a class="btn btn-outline-info float-right" href="${updateCakeLink}">Update Cake</a>
				    </c:if>
				</div>
			    </div>
			</div>
		    </c:forEach>
		</div>


		<c:set var="numberOfPage" value="${requestScope.NUMBER_OF_PAGE}" />
		<ul class="pagination m-3 justify-content-center align-items-center">
		    <c:forEach begin="1" end="${numberOfPage}" varStatus="counter">

			<c:if test="${counter.count ne currentPage}">

			    <c:url var="pageLink" value="searchCake">
				<c:param name="txtCakeName" value="${param.txtCakeName}" />
				<c:param name="txtRange" value="${param.txtRange}" />
				<c:param name="txtCategory" value="${param.txtCategory}" />
				<c:param name="txtPage" value="${counter.count}" />
			    </c:url>

			    <li class="page-item"><a class="page-link" href="${pageLink}">${counter.count}</a>
			    </li>
			</c:if>

			<c:if test="${counter.count eq currentPage}">
			    <li class="page-item disabled"><a class="page-link"
							      href="${pageLink}">${currentPage}</a></li>
			    </c:if>
			</c:forEach>
		</ul>
	    </c:if>
	    <c:if test="${empty listProduct}">
		No cake is matched.
	    </c:if>
	</div>
    </body>
</html>
