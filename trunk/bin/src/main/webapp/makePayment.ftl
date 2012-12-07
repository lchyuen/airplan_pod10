<html>
	<head>
		<title>Flight Reservation Payment</title>
		<link rel="stylesheet" href="css/airplan_css.css" />
	</head>
	<body class="bg-gray">
		<div class="bg-header">
			<div class="header alt-font" style="background-image:url(imgs/1.png)">
				<div class="header-logo">
					<img width="176" height="62" alt="AirPlan" src="css/logoairplan.PNG">
				</div>
				<div class="header-links">
					<div class="header-pers fs-xs">
						<div class="header-banner">
						</div>
						<div id="header-user-links" class="header-user-links">
							<ul class="menu top-menu header-user-link" id="signin-menu">
								<li class="mm-item">
									Welcome, *${user.name}* &nbsp; &nbsp; <a class="mm-item-link c9" href="signIn.action">Sign Out</a>
								</li>
							</ul>
						</div>
					</div>
					<ul class="header-menus fs-m menu">
						<li class="header-menu mm-item mm-item-link-sel hand" onclick="window.location.href='searchFlightsGui.action?sessionToken=${sessionToken}'">Search / Reserve Flights
						</li>
						<li class="header-menu mm-item mm-item-link hand" onclick="window.location.href='viewBookingList.action?sessionToken=${sessionToken}'">Manage Bookings
						</li>
						<li class="header-menu mm-item mm-item-link hand" onclick="window.location.href='viewTransactions.action?sessionToken=${sessionToken}'">View History
						</li>
					</ul>
				</div>
			</div>
		</div>		
		<div class="bg">
			<div class="wrap">
				<div class="content-1024">
					<div class="col-1024">
						<div class="p-basic"></div>
						<#if sessionToken??>
						<div id="corner-rnd" class="col-600 fs-s bg-gray">
							You have successfully paid for your booking.<br>
							A summary of your payment is shown below.<br>
						</div>
						</#if>
						<table id="pay_table" class="row-focus ta-c fs-n">
							<thead>
								<tr>
									<th>Payment ID</th>
									<th>Booking ID</th>									
									<th>Payment Method</th>
									<th>Amount</th>
									<th>Credit Card Holder</th>
									<th>Credit Card Number</th>
								</tr>
							</thead>
						<#if billing?? >
							<tbody>
								<td>${billing.id}</td>
								<td>${billing.bookingId}</td>							
								<td><#if isPayByPoints == "1">Loyalty Points<#else>Credit Card</#if></td>
								<td>${billing.amount}</td>
								<td><#if isPayByPoints == "0">${billing.name}<#else>-</#if></td>
								<td><#if isPayByPoints == "0">${billing.creditCardNum}<#else>-</#if></td>
							</tbody>
						<#else>
							Billing was not set
						</#if>
						</table>
						<div class="p-basic"></div>
					</div>
				</div>
			</div>
		</div>
		<div class="ftr">
			<div class="p-27-t">
				<div class="col-768">
					<p class="fs-xxs">(C)2012 pod10</p>
				</div>
			</div>
		</div>
	</body>
</html>