<html>
	<head>
		<title>View Past Transactions</title>
		<link rel="stylesheet" href="css/airplan_css.css" />
	</head>
	<body class="bg-gray">
		<div class="bg-header">
			<div class="header alt-font" style="background-image:url(imgs/3.png)">
				<div class="header-logo">
					<img width="176" height="62" alt="AirPlan" src="css/logoairplan.PNG">
				</div>
				<div class="header-links">
					<div class="header-pers fs-xs">
						<div class="header-banner">
						</div>
						<div id="header-user-links" class="header-user-links">
							<ul class="menu top-menu header-user-link" id="signin-menu">
								<li class="mm-item fs-s">
									Welcome, ${user.name} &nbsp; &nbsp; <a class="mm-item-link c9 fs-n" href="signIn.action">Sign Out</a>
								</li>
							</ul>
						</div>
					</div>
					<ul class="header-menus fs-m menu">
						<li class="header-menu mm-item mm-item-link hand" onclick="window.location.href='searchFlightsGui.action?sessionToken=${sessionToken}'">Search / Reserve Flights
						</li>
						<li class="header-menu mm-item mm-item-link hand" onclick="window.location.href='viewBookingList.action?sessionToken=${sessionToken}'">Manage Bookings
						</li>
						<li class="header-menu mm-item mm-item-link-sel hand" onclick="window.location.href='viewTransactions.action?sessionToken=${sessionToken}'">View History
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
						<table id="txn_table" class="row-focus-fancy ta-c fs-n">
						<#if transactionList??>
							<thead>
								<tr>
									<th>Date</th>
									<th>Booking ID</th>
									<th>Billing ID</th>
									<th>Credit Card Number</th>
									<th>Cardholder Name</th>
									<th>Amount</th>
									<th>Points Awarded</th>
									<th>Reason Points Updated</th>
								</tr>
							</thead>
							<tbody>
							<#list transactionList as transaction>
								<tr>
									<td>${transaction.dateCreated?substring(0,12)}</td>
									<td>${transaction.bookingId}</td>
									<td>${transaction.billingId}</td>									
								<#if transaction.creditCardNumber??>
									<td>${transaction.creditCardNumber}</td>
									<td>${transaction.name}</td>
									<td>$ ${transaction.amount}</td>
								<#else>
									<td>N/A</td>
									<td>N/A</td>
									<td>${transaction.amount} Points</td>
								</#if>
								<#if transaction.pointsAwarded??>
									<td>${transaction.pointsAwarded}</td>
								<#else>
									<td>-</td>
								</#if>
								<#if transaction.reason??>
									<td>${transaction.reason}</td>
								<#else>
									<#if transaction.amount >= 0 >
										<td>Points will be awarded after boarding pass is printed.</td>
									<#else>									
										<td>Transaction Cancelled.</td>
									</#if>
								</#if>
								</tr>
							</#list>
							</tbody>
						</table>
						<#else>
							Transactions not defined
						</#if>
						<div class="p-basic"></div>
						<#if points = -1>
						Points Not Set For User
						<#else>
						<div class="col-768">Total Points: ${points}</div>
						</#if>
						<div class="p-basic"></div>
					</div>
				</div>
			</div>
		</div>
		<div class="ftr">
			<div class="p-27-t">
				<div class="col-768">
					<p class="fs-xxs">(C)2012 pod10</p>
					<script src="js/jquery-1.8.2.js"/></script>
					<script src="js/jquery.tablescroll.js"></script>
					<script>
					jQuery(document).ready(function($) {
						$('#txn_table').tableScroll({height:340,width:1000});
					});
					</script>
				</div>
			</div>
		</div>
  	</body>
</html>	