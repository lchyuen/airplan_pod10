<html>
	<head>
		<title>Update Ticket</title>
		<link rel="stylesheet" href="css/airplan_css.css" />
	</head>
	<body class="bg-gray">
		<div class="bg-header">
			<div class="header alt-font" style="background-image:url(imgs/2.png)">
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
						<li class="header-menu mm-item mm-item-link hand" onclick="window.location.href='searchFlightsGui.action?sessionToken=${sessionToken}'">Search / Reserve Flights</a>
						</li>
						<li class="header-menu mm-item mm-item-link-sel hand" onclick="window.location.href='viewBookingList.action?sessionToken=${sessionToken}'">Manage Bookings
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
						<#if ticket??>
						<table id="t_table" class="row-focus ta-c fs-n">
							<thead>
								<tr>
									<th>ID</th>
									<th>Flight ID</th>
									<th>Booking ID</th>
									<th>Seat Type</th>
									<th>Seat Preference</th>
									<th>Meal Preference</th>
									<th>Passenger Name</th>
								</tr>	
							</thead>
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
						<#else>
							Ticket was not set.
						</#if>
						</table>
						<div class="inputs">
							<input name="sessionToken" id="sessionToken" type="hidden" value=${sessionToken} />
							<input name="ticketId" id="ticketId" type="hidden" value=${ticketId} />
						</div>
						<div class="col-600">
							<a href="viewBooking.action?sessionToken=${sessionToken}&bookingId=${ticket.bookingId}"><button type="submit" class="positive btn-gen fl-r" />Return to Booking List</button></a>
						</div>
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