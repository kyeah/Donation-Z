<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>PayPal SDK - DoVoid</title>

</head>
<body>
	<div id="wrapper">
		<div id="header">
			<h3>DoVoid</h3>
			<div id="apidetails">Used to void an order or an authorization.</div>
		</div>
		<form method="POST">
			<div id="request_form">
				<div class="params">
					<div class="param_name">AuthorizationID*</div>
					<div class="param_value">
						<input type="text" name="authID" value="" size="50"
							maxlength="260" />
					</div>
				</div>
				<div class="submit">
					<input type="submit" name="DoVoidBtn" value="DoVoid" /><br />
				</div>
				<a href="/merchant-sample/index.html">Home</a>
			</div>
		</form>
		<div id="relatedcalls">
			See also
			<ul>
				<li><a href="DoCapture">DoCapture</a></li>
				<li><a href="DoAuthorization">DoAuthorization</a></li>
				<li><a href="DoReauthorization">DoReauthorization</a></li>
				<li><a href="Refund">Refund</a></li>
				<li><a href="ReverseTransaction">ReverseTransaction</a></li>
				<li><a href="DoNonReferencedCredit">DoNonReferencedCredit</a></li>
				<li><a href="ManagePendingTransactionStatus">ManagePendingTransactionStatus</a>
				</li>
			</ul>
		</div>
	</div>
</body>
</html>