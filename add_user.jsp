<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Sollers Trading System - Add User</title>
		<script language="JavaScript">
		
		function checker()
		{
			var regExp="[A-Za-z][A-Za-z0-9]+";
			var result1 = document.adduserform.password.value.match(regExp);
			var result2 = document.adduserform.password.value.match(document.adduserform.vpassword.value);
			if (document.adduserform.password.value.length < 8){
				alert("Password not long enough");
				document.adduserform.password.value="";
				document.adduserform.vpassword.value="";
				return false;
			} else	if(result1 == null){
					alert("Password not valid");
					document.adduserform.password.value="";
					document.adduserform.vpassword.value="";
					return false;
				} else if (result2 == null){
					alert ("Passwords don't match");
					document.adduserform.password.value="";
					document.adduserform.vpassword.value="";
					return false;
				} else {
					document.adduserform.submit();
				}		
		};
		
		</script>
	</head>
	<body>
		<h1>Add User</h1>
		<form name="adduserform" method="post" action="AddUser" onsubmit="return checker();">
		Username: <input type="text" name="uname" required><br/>
		Password: <input type="password" name="password" required/><br/>
		Verify Password: <input type="password" name="vpassword" required/><br/>
		<button type="submit" name="submit">Create Account</button><br/>
		</form>
	</body>
</html>