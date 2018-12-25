<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@  taglib  prefix="c"   uri="http://java.sun.com/jsp/jstl/core"  %>
	
<!-- @author Karanveer -->
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Sollers Trading System - Account Home</title>
</head>
<style>
	ul.navList {
		list-style-type: none;
		margin: 0;
		padding: 0;
		width: 200px;
		background-color: #f1f1f1;
		border: 1px solid #555;
	}
	
	li.navItem a {
		display: block;
		color: #000;
		padding: 8px 16px;
		text-decoration: none;
	}
	
	li.navItem {
		text-align: center;
		border-bottom: 1px solid #555;
	}
	
	li.navItem:last-child {
		border-bottom: none;
	}
	
	li.navItem a.active {
		background-color: #4CAF50;
		color: white;
	}
	
	li.navItem a:hover:not(.active) {
	    background-color: #555;
	    color: white;
	}
	
	li.navItem a.logout {
		float:right;
	}
</style>
<body>
	<h2>Account Home</h2>

	
	<c:if test="${successMessage != null}">
		<p style="color:green;"><c:out value="${successMessage}"/></p>
	</c:if>
	<c:if test="${errorMessage != null}">
		<p style="color:red;"><c:out value="${errorMessage}"/></p>
	</c:if>
	
	<c:if test="${userId != null}">
		<p>Current session user id: <c:out value="${userId}"/></p>
	</c:if>

	<ul class="navList">
		<li class="navItem"><a class="active" href="account_home.jsp">Home</a></li>
		<li class="navItem"><a href="ViewStocks">View Stocks</a></li>
		<li class="navItem"><a href="add_funds.jsp">Add Funds</a></li>
		<li class="navItem"><a href="CreateOrder">Create Order</a></li>
		<li class="navItem"><a href="EvaluatePortfolio">Evaluate Portfolio</a></li>
		<li class="navItem"><a href="#viewHistory">History</a>
	</ul>


	<ul class="navList">
		<li class="navItem"><a class="logout" href="login.jsp">Logout</a>
	</ul>

</body>
</html>