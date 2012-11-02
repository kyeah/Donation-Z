<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>PayPal SDK - GetTransactionDetails</title>
</head>
<body>
	<div id="wrapper">
		<div id="header">
			<h3>GetTransactionDetails</h3>
			<div id="apidetails">GetTransactionDetails API operation
				obtains information about a specific transaction.</div>
		</div>
		<form method="POST">
			<div id="request_form">
				<div class="params">
					<div class="param_name">
						Transaction ID*(Get Transaction ID via <a
							href="../DCC/DirectPayment">Direct Payment</a> or <a
							href="../EC/SetExpressCheckout">ExpressCheckout</a>)
					</div>
					<div class="param_value">
						<input type="text" name="transID" value="" size="50"
							maxlength="260" />
					</div>
				</div>
				<div class="submit">
					<input type="submit" name="GetTransactionDetailsBtn"
						value="GetTransactionDetails" /><br />
				</div>
				<a href="/merchant-sample/index.html">Home</a>
			</div>
		</form>

		<div id="relatedcalls">
			See also
			<ul>
				<li><a href="TransactionSearch">TransactionSearch</a></li>
				<li><a href="GetPalDetails">GetPalDetails</a></li>
				<li><a href="GetBalance">GetBalance</a>
				</li>
				<li><a href="AddressVerify">AddressVerify</a>
				</li>


			</ul>
		</div>
	</div>
</body>
</html>