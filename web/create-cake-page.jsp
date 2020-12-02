<%-- 
    Document   : create-cake-page
    Created on : Oct 11, 2020, 5:34:09 PM
    Author     : MinHiu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
	<link rel="stylesheet" href="./assets/css/custom.css">
        <title>Create Cake Page</title>
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

		    <c:if test="${userRole eq 'A'}">
			<li class="nav-item active">
			    <a class="nav-link disabled text-white font-weight-bold" href="createCakePage">Create New Cake</a>
			</li>
		    </c:if>
		</ul>

		<div class="navbar-right m-2">
		    <c:if test="${not empty currentUser}">
			<p class="text-white text-center h4">
			    Welcome ${currentUser.userName} <br>
			</p>
			<form class="form-inline" action="logout">
			    <input class="btn btn-danger" type="submit" value="Logout" />
			</form>
		    </c:if>
		    <c:if test="${empty currentUser}">
			<p class="text-white text-center h4">
			    Welcome to our store <br>
			</p>
			<form class="form-inline" action="loginPage">
			    <input class="btn btn-info" type="submit" value="Login" />
			</form>
		    </c:if>
		</div>
	    </div>
	</nav>

	<div class="container h-100 w-150 mt-2 justify-content-center align-items-center">
	    <c:set var="error" value="${requestScope.CREATE_ERROR}"/>
	    <form action="createCake" method="POST" enctype="multipart/form-data">
		<div class="form-group">
		    <label>Cake Name</label>
		    <input type="text" class="form-control m-1" name="txtName"
			   value="${param.txtName}" required/>
		    <small class="text-muted">
			Must be 5-100 characters.
		    </small>
		    <p class="text-danger">${error.productNameLengthError}</p>
		</div>
		<div class="form-group">
		    <div class="custom-file">
			<input type="file" accept=".jpg,.png" class="custom-file-input m-1" name="imageSelect" required/>
			<label class="custom-file-label" for="inputGroupFile04">Choose file</label>
			<p class="text-danger">${error.productImageSelectedError}</p>
		    </div>
		</div>
		<div class="form-group">
		    <label>Cake Price</label>
		    <input type="text" class="form-control m-1" name="txtPrice"
			   value="${param.txtPrice}" required/>
		    <small class="text-muted">
			Must be 1-50$.
		    </small>
		    <p class="text-danger">${error.productPriceLengthError}</p>
		    <p class="text-danger">${error.productPriceFormatError}</p>
		</div>
		<div class="form-group">
		    <label>Cake Quantity</label>
		    <input type="text" class="form-control m-1" name="txtQuantity"
			   value="${param.txtQuantity}" required/>
		    <small class="text-muted">
			Must be 10-10000 quantity.
		    </small>
		    <p class="text-danger">${error.productQuantityLengthError}</p>
		    <p class="text-danger">${error.productQuantityFormatError}</p>
		</div>
		<div class="form-group">
		    <label>Cake Category</label>
		    <select class="form-control m-1" name="cboCategory">
			<option>Cake</option>
			<option>Combo</option>
		    </select>
		    <p class="text-danger">${error.productCategoryError}</p>
		</div>
		<div class="form-group justify-content-center align-items-center">
		    <input class="btn btn-outline-success" type="submit" value="Upload New Cake" name="btAction"/>
		</div>
	    </form>
	</div>

	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" crossorigin="anonymous"></script>
	<script>
	    $('.custom-file-input').on('change', function () {
		let fileName = $(this).val().split("\\")[2];
		$('.custom-file-label').html(fileName);
	    });
	</script>
    </body>
</html>
