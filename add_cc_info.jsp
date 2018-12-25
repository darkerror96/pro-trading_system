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
		    margin: 5px 150px;
		    cursor: pointer;
		}
		.legend {
    		background-color: #000;
    		color: #fff;
		}
	</style>
	<meta charset="UTF-8">
		<title>Credit Card Info</title>
		<script>
			function getDigit(num)
			{
				return (num % 10) + Math.round((num / 10) - ((num % 10) / 10));   
			}
			function sumofDep(num,len)
			{
				var sum = 0;
				for (var i = len - 2; i >= 0; i -= 2)  
			    {
					sum += getDigit(num.charAt(i) * 2); 
				}	    
				return sum;
			}
			function sumofOp(num,len)
			{
				var sum = 0;  
				for (var i = len - 1; i >= 0; i -= 2)  
			    {
					sum += Number(num.charAt(i)); 
			   	}       	
			    return sum;
			}
			function checkCCNo(num,len)
			{
				if( ((sumofDep(num,len) + sumofOp(num,len)) % 10) == 0)
					return true;
				else
					return false;
			}
			
			function validateForm() 
			{
				var c_type = document.forms["creditCardForm"]["c_type"].value;
			    var ccno = document.forms["creditCardForm"]["c_ccno"].value;
			    var fccno = String(ccno).charAt(0);
			    var sccno = String(ccno).charAt(1);
			    if(c_type == "Visa")
			    {
			    	if (ccno.length == 16) 
			    	{
			    		if(fccno == 4)
			    		{
			    			if(!(checkCCNo(ccno.toString(),ccno.toString().length)))
			    			{
			    				document.forms["creditCardForm"]["c_ccno"].value = "";
						        alert("Invalid Credit Card Number entered. Try again...");
						        return false;
			    			}
			    		}
			    		else{
			    			document.forms["creditCardForm"]["c_ccno"].value = "";
					        alert("Visa Credit Card Number begins with '4'. Try again...");
					        return false;
			    		}
			    	}
			    	else{
			    		document.forms["creditCardForm"]["c_ccno"].value = "";
				        alert("Visa Credit Card Number length should be 16 digits. Try again...");
				        return false;
			    	}
			    }
			    else if(c_type == "Mastercard")
			    {
			    	if (ccno.length == 16) 
			    	{
			    		if(fccno == 5)
			    		{
			    			if(!(checkCCNo(ccno.toString(),ccno.toString().length)))
			    			{
			    				document.forms["creditCardForm"]["c_ccno"].value = "";
						        alert("Invalid Credit Card Number entered. Try again...");
						        return false;
			    			}
			    		}
			    		else{
			    			document.forms["creditCardForm"]["c_ccno"].value = "";
					        alert("Mastercard Credit Card Number begins with '5'. Try again...");
					        return false;
			    		}
			    	}	
			    	else{
			    		document.forms["creditCardForm"]["c_ccno"].value = "";
				        alert("Mastercard Credit Card Number length should be 16 digits. Try again...");
				        return false;	
			    	}
			   	}
			    else if(c_type == "AMEX")
			    {
			    	if (ccno.length == 15) 
			    	{
			    		if(fccno == 3 && (sccno == 7 || sccno == 4))
			    		{
			    			if(!(checkCCNo(ccno.toString(),ccno.toString().length)))
			    			{
			    				document.forms["creditCardForm"]["c_ccno"].value = "";
						        alert("Invalid Credit Card Number entered. Try again...");
						        return false;
			    			}
			    		}
			    		else{
			    			document.forms["creditCardForm"]["c_ccno"].value = "";
					        alert("American Express Credit Card Number begins with '37/34'. Try again...");
					        return false;
			    		}
			    	}	
			    	else{
			    		document.forms["creditCardForm"]["c_ccno"].value = "";
				        alert("American Express Credit Card Number length should be 15 digits. Try again...");
				        return false;
			    	}
			   	}
			    else if(c_type == "Discover")
			    {
			    	if (ccno.length == 16) 
			    	{
			    		if(fccno == 6)
			    		{
			    			if(!(checkCCNo(ccno.toString(),ccno.toString().length)))
			    			{
			    				document.forms["creditCardForm"]["c_ccno"].value = "";
						        alert("Invalid Credit Card Number entered. Try again...");
						        return false;
			    			}
			    		}
			    		else{
			    			document.forms["creditCardForm"]["c_ccno"].value = "";
					        alert("Discover Credit Card Number begins with '6'. Try again...");
					        return false;
			    		}
			    	}
			    	else{
			    		document.forms["creditCardForm"]["c_ccno"].value = "";
				        alert("Discover Credit Card Number length should be 16 digits. Try again...");
				        return false;
			    	}
			   	}
			    
			    var expm = document.forms["creditCardForm"]["c_expm"].value;
			    var expy = document.forms["creditCardForm"]["c_expy"].value;
			    var eD = new Date(expy,expm,0);
			    var tD = new Date(new Date().getFullYear(), new Date().getMonth()+1,0);
			    if(eD < tD)
			    {
			    	document.forms["creditCardForm"]["c_expm"].value = "";
			        alert("Credit Card is already Expired. Try again...");
			        return false;
			   	}
			    
			    var cvvno = document.forms["creditCardForm"]["c_cvvno"].value;
			    if(c_type == "AMEX"){
			    	if (!(cvvno.length == 4)) {
				    	document.forms["creditCardForm"]["c_cvvno"].value = "";
				        alert("CVV Number length should be 4 digits. Try again...");
				        return false;
			    	}
			    }
			    else{
			    	if (!(cvvno.length == 3)) {
				    	document.forms["creditCardForm"]["c_cvvno"].value = "";
				        alert("CVV Number length should be 3 digits. Try again...");
				        return false;
			    	}	
			   	}
			}
		</script>
	</head>
	<body>
		<form name="creditCardForm" action="CCController" method="post" onsubmit="return validateForm()">
			<fieldset>
			<legend class="legend">Credit Card Information</legend>
			<br>
				<table>
					<tr>
						<th>Credit Card Holder Name :</th>
						<th><input type="text" name="c_owner" required autofocus/><br></th>
					</tr>
					<tr>
						<th>Type of Credit Card :</th>
						<th>
							<select id="c_type" name="c_type">
		    					<option value="Visa">Visa</option>
		   	 					<option value="Mastercard">Mastercard</option>
		    					<option value="AMEX">American Express</option>
		    					<option value="Discover">Discover</option>
	  						</select>
	  					</th>
					</tr>
					<tr>
						<th>Credit Card Number :</th>
						<th><input type="number" name="c_ccno" required/><br></th>
					</tr>
					<tr>
						<th>Expiration Month/Year :</th>
						<th>
                        	<input id="c_expm" name="c_expm" type="number" min="1" max="12" placeholder="mm" required/>
                        	<input id="c_expy" name="c_expy" type="number" placeholder="yyyy" required/>
                        	<script>
	                        	var td = new Date();
	                            
	                            document.getElementById("c_expy").min = td.getFullYear();
	                            document.getElementById("c_expy").max = (td.getFullYear()+10);
                        	</script>
                      	</th>
					</tr>
					<tr>
						<th>CVV Number :</th>
						<th>
							<input id="c_cvvno" type="number" min="0" max="9999" name="c_cvvno" required/>
						</th>
					</tr>
				</table>
			<br><input type="submit" name="Submit" class="button"/>
			</fieldset>
		</form>
	</body>
</html>