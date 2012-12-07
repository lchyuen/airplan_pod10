<html>
	<head>
		<title>Admin: Cancel Booking</title>
		<link rel="stylesheet" href="css/airplan_css.css" />
	</head>
	<body class="bg-gray">
		<div class="bg-header">
			<div class="header alt-font" style="background-image:url(imgs/1.png)">
				<div class="header-logo">
					<a href="staffHome.action?sessionToken=${sessionToken}">
						<img width="176" height="62" alt="AirPlan" src="css/logoairplan.PNG">
					</a>
				</div>
				<div class="header-links">
					<div class="header-pers fs-xs">
						<div class="header-banner">
						</div>
						<div id="header-user-links" class="header-user-links">
							<ul class="menu top-menu header-user-link" id="signin-menu">
								<li class="mm-item fs-s">
									Welcome, ${user.name} &nbsp; &nbsp; <a class="mm-item-link c9 fs-n" href="staffSignIn.action">Sign Out</a>
								</li>
							</ul>
						</div>
					</div>
					<ul class="header-menus fs-m menu">
						<li class="header-menu mm-item mm-item-link-sel hand" onclick="window.location.href='staffManage.action?sessionToken=${sessionToken}'">Manage User Bookings
						</li>
						<li class="header-menu mm-item mm-item-link hand" onclick="window.location.href='selectManifest.action?sessionToken=${sessionToken}'">View Flight Manifests
						</li>
						<li class="header-menu mm-item mm-item-link hand" onclick="window.location.href='viewStatistics.action?sessionToken=${sessionToken}'">View Flight Statistics
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
						<#if cancelState??>						
						<#if cancelState==0> 
						<div id="corner-rnd" class="col-600 ta-c fs-s bg-gray">
							The ticket cannot be canceled because boarding pass is already printed!!!
						</div>
						<#else>
						<div id="corner-rnd" class="col-600 fs-s bg-gray">Billing summary of selected booking:</div>
						<div class="p-basic">
							<table id="b_table" class="row-focus ta-c fs-n">
								<thead>
									<tr>
										<th>Bill ID</th>
										<th>Date Created</th>
										<th>Amount</th>
										<th>Name</th>
										<th>Credit Card</th>
										<th>Points Used?</th>
									</tr>	
								</thead>
							<#if bill??>
								<tbody>
								<#if bill.id?? && bill.dateCreated?? && bill.amount?? && bill.isLoyaltyPointsUsed??>
									<tr>
										<td>${bill.id}</td>
										<td>${bill.dateCreated}</td>
										<td>${bill.amount}</td>
										<td><#if bill.name??>${bill.name}<#else>-</#if></td>
										<td><#if bill.creditCardNum??>${bill.creditCardNum}<#else>-</#if></td>
										<td>${bill.isLoyaltyPointsUsed()?string("yes", "no")}</td>
									</tr>
								</#if>
								</tbody>
							<#else>
								Bill was not set
							</#if>
							</table>
						</div>
						<div id="corner-rnd" class="col-600 fs-s bg-gray">This booking contains the following reservations:</div>
						<div class="p-basic">
							<table id="r_table" class="row-focus ta-c fs-n">
								<thead>
									<tr>
										<th>Ticket ID</th>
										<th>Flight ID</th>
										<th>Seat Type</th>
										<th>Seat Preference</th>
										<th>Meal Preference</th>
										<th>Passenger Name</th>
									</tr>	
								</thead>
							<#if ticketList??>	
							<#list ticketList as ticket>
								<tbody>
								<#if ticket.id?? && ticket.flightId?? && ticket.seatType?? && ticket.seatPref?? && ticket.mealPref?? && ticket.passengerName??>
									<tr>
										<td>${ticket.id}</td>
										<td>${ticket.flightId}</td>
										<td>${ticket.seatType}</td>
										<td>${ticket.seatPref}</td>
										<td>${ticket.mealPref}</td>
										<td>${ticket.passengerName}</td>
									</tr>
								</#if>
								</tbody>
							</#list>
							<#else>
								Tickets were not set
							</#if>
							</table>
						</div>
						<div class="col-768">
							<a href="staffConfirmCancel.action?sessionToken=${sessionToken}&bookingId=${bookingId}&billingId=${bill.id}&customerToken=${customerToken}"><button type="submit" class="positive btn-gen fl-r">Confirm Cancellation</button></a>
						</div>
						</#if>
						</#if>
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
						$('#r_table').tableScroll({height:130,width:1000});
					});
					</script>
				</div>
			</div>
		</div>
	</body>
</html>