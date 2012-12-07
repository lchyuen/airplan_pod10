<html>
	<head>
		<title>Admin: View Selected Flight Manifest</title>
		<link rel="stylesheet" href="css/airplan_css.css" />
	</head>
	<body class="bg-gray">
		<div class="bg-header">
			<div class="header alt-font" style="background-image:url(imgs/2.png)">
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
						<li class="header-menu mm-item mm-item-link hand" onclick="window.location.href='staffManage.action?sessionToken=${sessionToken}'">Manage User Bookings
						</li>
						<li class="header-menu mm-item mm-item-link-sel hand" onclick="window.location.href='selectManifest.action?sessionToken=${sessionToken}'">View Flight Manifests
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
						<table id="vm_table" class="row-focus ta-c fs-n">
							<thead>
								<tr>
									<th>Ticket ID</th>
									<th>Flight ID</th>
									<th>Booking ID</th>
									<th>Seat Type</th>
									<th>Seat Preference</th>
									<th>Meal Preference</th>
									<th>Passenger Name</th>
								</tr>	
							</thead>
						<#if ticketList??>
						<#list ticketList as ticket>
							<tbody>
							<#if ticket.id?? && ticket.flightId?? && ticket.bookingId?? && ticket.seatType?? && ticket.seatPref?? && ticket.mealPref?? && ticket.passengerName??>
								<tr>
									<td>${ticket.id}</td>
									<td>${ticket.flightId}</td>
									<td>${ticket.bookingId}</td>
									<td><#if ticket.seatType == "1">Business<#else>Economy</#if></td>
									<td>${ticket.seatPref}</td>
									<td>${ticket.mealPref}</td>
									<td>${ticket.passengerName}</td>
								</tr>
							</#if>
							</tbody>
						</#list>
						<#else>
							Flight Manifest was not set
						</#if>
						</table>
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
						$('#vm_table').tableScroll({height:400,width:1024});
					});
					</script>
				</div>
			</div>
		</div>
	</body>
</html>