<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!-- @author Karanveer -->
<!DOCTYPE html>
	<html>
		<head>
			<meta charset="UTF-8">
			<title>Sollers Trading System - Personal Information</title>
		</head>
	<body>
		<h1>Add Personal Information</h1>
		<% 
			if (request.getSession().getAttribute("userId") != null) {
				out.println("User id in session: " + request.getSession().getAttribute("userId"));
			}
		%>
			
		<form name="infoform" method="post" action="AddAccountInfo">
			First Name:       <input type="text"     name="fName" pattern="^[A-Z][a-z]+$" title="Starts with capital letter, no middle name"   required/><br>
			Last Name:        <input type="text"     name="lName" pattern="^[A-Z][a-z]+" title="Starts with capital letter"   required/><br>
			Email: <input type="email" name="email" required><br>
			DOB: <input type="date" name="dob" required/><br>
			SS#: <input type="text" name="ssn" pattern="\d{3}-\d{2}-\d{4}" title="***-**-****" required/><br>
			<button type="submit" name="submit">Next</button><br>
		</form>
	</body>
</html>