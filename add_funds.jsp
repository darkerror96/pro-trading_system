<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Sollers Trading System - Add Funds</title>
		<script language="JavaScript">
		function checker() {
			if (document.adduserform.amount.value > 10000000) {
				alert("Cannot add more than $10,000,000");
				document.adduserform.amount.value = "";
				return false;
			} 
		};
	</script>
</head>
<body>
<h1>Add Funds</h1>
<form name="adduserform" method="post" action="AddFunds"
	onsubmit="return checker();"> 
	<!--  User name: <input type="text" name="uname"/ required><br/>-->
	<br/>
	Funding Source: <input type="radio" name="fundSource" value="Bank Account"/ required>Bank Account</input>
	<input type="radio" name="fundSource" value="Credit Card"/ required>Credit Card<br/><br/>
	Amount: <input type="number" name="amount"/ required><br/><br/>
	<button type="submit" name="submit">Add Funds</button>
	<br />
</form>
</body>
</html>
