<html>
	<head>
		<title>Admin: View Bookings</title>
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
						<#if booking??>
						<table id="b_table" class="row-focus ta-c fs-n">
							<thead>
								<tr>
									<th>Booking ID</th>
									<th>Date Created</th>
									<th>Cancel Booking</th>
								</tr>	
							</thead>
							<tbody>
								<#if booking?? && booking.id??>
									<tr>
										<td>${booking.id}</td></a>
										<#if booking.dateCreated??>
										<td>${booking.dateCreated?string("EEE, MMM d, yyyy")}</td>
										</#if>
										<#if booking.cancelState == 1>
										<td><a href="staffCancelBooking.action?sessionToken=${sessionToken}&bookingId=${booking.id}&customerToken=${customerToken}">Cancel Booking ${booking.id}</a></td>
										<#elseif booking.cancelState == 0>
										<td>Cannot Cancel</td>
										<#else>
										<td>Cancelled</td>
										</#if>
									</tr>
								</#if>
							</tbody>
						</table>
						<#else>
							Bookings Were Not Displayed
						</#if>
						<div class="p-basic"></div>
						<table id="t_table" class="row-focus ta-c fs-n">
							<thead>
								<tr>
									<th>Ticket ID</th>
									<th>Flight ID</th>
									<th>Seat Type</th>
									<th>Seat Preference</th>
									<th>Meal Preference</th>
									<th>Passenger Name</th>
									<th>Cost</th>
									<th>Origin</th>
									<th>Destination</th>
									<th>Departure Time</th>
									<th>Arrival Time</th>
									<th>Airline Name</th>
									<th>Manage Ticket</th>
								</tr>	
							</thead>
						<#if ticketSetList??>
						<#list ticketSetList as ts>
							<tbody>
							<#if ts.ticket.id?? && ts.ticket.flightId?? && ts.ticket.seatType?? && ts.ticket.seatPref?? && ts.ticket.mealPref?? && ts.ticket.passengerName?? && ts.ticket.state?? && ts.ticket.cost?? && ts.flight.origin??  && ts.flight.destination?? && ts.flight.departureTime?? && ts.flight.arrivalTime?? && ts.flight.airlineName??>
								<tr>
									<td>${ts.ticket.id}</td>
									<td>${ts.ticket.flightId}</td>
									<td><#if ts.ticket.seatType == "1">Business<#else>Economy</#if></td>
									<td>${ts.ticket.seatPref}</td>
									<td>${ts.ticket.mealPref}</td>
									<td>${ts.ticket.passengerName}</td>
									<td>${ts.ticket.cost}</td>
									<td>${ts.flight.origin}</td>
									<td>${ts.flight.destination}</td>
									<td>${ts.flight.departureTime}</td>
									<td>${ts.flight.arrivalTime}</td>
									<td>${ts.flight.airlineName}</td>
									<#if ts.ticket.state == 1>
									<td><a href="staffModifyTicketGui.action?ticketId=${ts.ticket.id}&customerToken=${customerToken}&sessionToken=${sessionToken}">Modify Ticket</a></td>
									<#elseif ts.ticket.state == 2>
									<td>Cannot Manage Ticket</td>
									<#elseif ts.ticket.state == 3>
									<td>Ticket Cancelled</td>
									</#if>
								</tr>
							</#if>
							</tbody>
						</#list>
						<#else>
							Tickets were not set
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
					<script src="js/jquery-1.8.2.js"/></script>
					<script src="js/jquery.tablescroll.js"></script>
					<script>
					jQuery(document).ready(function($) {
						$('#t_table').tableScroll({height:300,width:1024});
					});
					</script>
				</div>
			</div>
		</div>
	</body>
</html>