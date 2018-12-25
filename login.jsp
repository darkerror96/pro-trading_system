<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!-- @author Karanveer -->
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Trading System</title>
</head>
<body>
	<h1>Login</h1>

	<%
	    // if there is a message attribute with this page's request, print message
	    if (request.getAttribute("errorMessage") != null) {
			out.println("<p style=\"color:red;\">" + request.getAttribute("errorMessage") + "</p><br>");
	    }
	%>
	<form action="LoginController" method="post">
		Enter username :<input type="text" name="username"><br>
		Enter password :<input type="password" name="password"><br>
		<br> <input type="submit" value="Login">
	</form>
	<h2>
		<a href="./add_user.jsp">Create New Account</a>
	</h2>

</body>
</html>