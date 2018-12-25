<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Sollers Trading System - user verification</title>
<script type="text/javascript">
	function getUrlVars() {
		var vars = {};
		var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi,
				function(m, key, value) {
					vars[key] = value;
				});
		return vars;
	}
function onLoadSubmit() {
	var code = getUrlVars()["code"];
	document.getElementById('var1').value = code;
	document.myform.submit();
}
</script>
</head>
<body onload="onLoadSubmit()">
 
<form name="myform" id="myform" action="VerifyUser" method="post">
    <input type="hidden" id="var1" name="var1" value="" />
</form>
</body>
</html>
