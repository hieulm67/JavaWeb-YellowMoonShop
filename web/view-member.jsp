<%-- 
    Document   : view-member
    Created on : Nov 1, 2020, 11:44:07 PM
    Author     : MinHiu
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Member Page</title>
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

		<c:if test="${userRole eq 'A'}">
		    <li class="nav-item">
			<a class="nav-link text-white" href="createCakePage">Create New Cake</a>
		    </li>
		</c:if>
		<c:if test="${userRole eq 'A'}">
		    <li class="nav-item">
			<a class="nav-link disabled text-white font-weight-bold" href="viewMembersPage">View Members</a>
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
	<c:set var="listMembers" value="${requestScope.LIST_MEMBERS}"/>
	<table class="table table-hover">
	    <thead class="thead-light">
		<tr>
		    <th>#</th>
		    <th>Email</th>
		    <th>Name</th>
		    <th>Phone</th>
		    <th>Address</th>
		    <th>Role</th>
		</tr>
	    </thead>
	    <tbody>
	    <c:forEach var="item" items="${listMembers}" varStatus="counter">
		<tr>
		    <td>
			${counter.count}
		    </td>
		    <td>
			${item.userEmail}
		    </td>
		    <td>
			${item.userName}
		    </td>
		    <td>
			${item.userPhone}
		    </td>
		    <td>
			${item.userAddress}
		    </td>
		    <td>
			${item.userRole}
		    </td>
		    </form>
		</tr>
	    </c:forEach>
	    </tbody>
	</table>
    </div>
</body>
</html>
