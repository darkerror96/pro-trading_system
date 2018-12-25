<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@  taglib  prefix="c"   uri="http://java.sun.com/jsp/jstl/core"  %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Sollers Trading System - Create Order</title>
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
	</style>

<body>

	<h1>Create Order</h1>
	
	<c:set var="errorMessage" value='${errorMessage}'/>
    <c:set var="position" value='${position}'/>
    <c:set var="symbol" value='${symbol}'/>
    <c:set var="side" value='${side}'/>
    <c:set var="size" value='${size}'/>
    
    <c:if test="${userId != null}">
		<p>Current session user id: <c:out value="${userId}"/></p>
	</c:if>

	<c:if test="${errorMessage != null}">
		<c:forEach items="${errorMessage}" var="line">
			<p style="color: red;">
				<c:out value="${line}"/><br>
			</p>
		</c:forEach>
	</c:if>

	<c:if test="${position != null }">
		<p style="color:blue;">For selected position of: <c:out value="${symbol}"/></p><br>
	</c:if>

	<form name="orderform" method="post" action="CreateOrder">
		<div style="float: left; width: 20%;">
			<c:choose>
				<c:when test="${position == null}">
					<label for="openPosition">Enter a position</label>
					<br>
					<input type="radio" name="orderType" id="openPosition" value="BUY" required> 	  Buy<br>
					<input type="radio" name="orderType" id="openPosition" value="SELL_SHORT">        Sell Short<br>
					<br>
				</c:when>
				<c:when test="${position != null }">
					<c:if test="${side == '1'}">
						<label for="closePosition">Close Position</label><br>
						<input type="radio" name="orderType" id="closePosition" value="SELL" required>        Sell<br>
					</c:if>
					<c:if test="${side == '-1' }">
						<label for="closePosition">Close Position</label><br>
						<input type="radio" name="orderType" id="closePosition" value="BUY_TO_COVER" required>       Buy to Cover<br>
					</c:if>
				</c:when>
			</c:choose>
		</div>
		<div style="float: left; width: 20%;">
			<label for="qtty">Quantity</label> 
			<c:choose>
				<c:when test="${size != null}">
					<input type="text" name="quantity" id="qtty" value="<c:out value='${size}' />" pattern="\d{1,6}" title="Number with 1-6 digits" readonly>
				</c:when>
				<c:otherwise>
					<input type="text" name="quantity" id="qtty" pattern="\d{1,6}" title="Number with 1-6 digits" required>
				</c:otherwise>
			</c:choose>
		</div>
		<div style="float: left; width: 20%;">
			<label for="smbl">Symbol</label>

			<c:choose>
				<c:when test="${symbol != null}">
					<input type="text" name="symbol" id="smbl" value="<c:out value='${symbol}'/>" pattern="^[a-zA-Z]+$" title="Letters only" readonly>
				</c:when>
				<c:otherwise>
					<input type="text" name="symbol" id="smbl" pattern="^[a-zA-Z]+$" title="Letters only" required>
				</c:otherwise>
			</c:choose>
						
		</div>
		<div style="float: left; width: 20%;">
			<input type="radio" name="priceType" value="MARKET"    id="mkt" required> Market<br>
			<input type="radio" name="priceType" value="LIMIT"     id="lmt"> 		  Limit<br>
			<input type="radio" name="priceType" value="STOP"      id="stp"> 		  Stop<br>
			<br> 
			
			<label for="stopPr">Stop/Limit Price</label> 
			<input type="text" name="stopPrice" id="stopPr" pattern="\d{1,6}" title="Number with 1-6 digits" disabled required>
		</div>
		<div style="float: left; width: 20%;">
			<input type="radio" name="duration" value="DAY" required> Good For Today<br> 
			<input type="radio" name="duration" value="GTC"> Good Till Canceled<br> 
			<input type="radio" name="duration" value="GTX"> Good Till Executed<br>
		</div>
		<div style="clear: both; text-align: center;">
			<br>
			<hr />
			<input type="submit" name="submit" value="Place Order"/>
		</div>
	</form>
	<ul class="navList"><li class="navItem"><a href="account_home.jsp">Home</a></li></ul>
	<script>
		document.getElementById("mkt").addEventListener('change', function(){
		    document.getElementById("stopPr").disabled = this.checked;
		    document.getElementById("stopPr").value = "";
		    document.getElementById("stopPr").required = (this.checked) ? false : true ;
		});
		
		document.getElementById("lmt").addEventListener('change', function(){
		    document.getElementById("stopPr").disabled = (this.checked) ? false : true;
		    document.getElementById("stopPr").required = this.checked;
		});
		document.getElementById("stp").addEventListener('change', function(){
		    document.getElementById("stopPr").disabled = (this.checked) ? false : true;
		    document.getElementById("stopPr").required = this.checked;
		});
	</script>
</body>

</html>