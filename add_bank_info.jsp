<!DOCTYPE html>
<html>
	<head>
	<style>
		.button {
		    border: none;
		    background-color: black;
    		color: white;
		    padding: 8px 14px;
		    text-align: center;
		    font-size: 16px;
		    margin: 5px 120px;
		    cursor: pointer;
		}
		.legend {
    		background-color: #000;
    		color: #fff;
		}
	</style>
	<meta charset="UTF-8">
		<title>Bank Info</title>
		<script>
			function validateForm() {
			    var accno = document.forms["bankForm"]["b_accno"].value;
			    var rno = document.forms["bankForm"]["b_rno"].value;
			    
			    if (!(accno.length > 7 && accno.length < 16)) {
			    	document.forms["bankForm"]["b_accno"].value = "";
			        alert("Account Number length should be in between 8 to 15 digits. Try again...");
			        return false;
			    }
			    
			    if (!(rno.length == 9)) {
			    	document.forms["bankForm"]["b_rno"].value = "";
			        alert("Routing Number length should be 9 digits. Try again...");
			        return false;
			    }
			}
		</script>
	</head>
	<body>
		<form name="bankForm" action="BankController" method="post" onsubmit="return validateForm()">
			<fieldset>
			<legend class="legend">Bank Information</legend>
			<br>
			<table>
				<tr>
					<th>Account Holder Name :</th>
					<th><input type="text" name="b_owner" required autofocus/><br></th>
				</tr>
				<tr>
					<th>Name of Bank :</th>
					<th><input type="text" name="b_name" required/><br></th>
				</tr>
				<tr>
					<th>Account Number :</th>
					<th><input type="number" name="b_accno" required/><br></th>
				</tr>
				<tr>
					<th>Routing Number :</th>
					<th><input type="number" name="b_rno" required/><br></th>
				</tr>
			</table>
			<br><br><input type="submit" name="Submit" class="button"/>
			</fieldset>
		</form>
	</body>
</html>
