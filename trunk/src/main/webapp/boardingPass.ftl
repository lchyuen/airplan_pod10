<html>
	<head>
		<title>Print Boarding Pass</title>
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
						<li class="header-menu mm-item mm-item-link hand" onclick="window.location.href='searchFlightsGui.action?sessionToken=${sessionToken}'">Search / Reserve Flights
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
						<table id="pass_table" class="row-focus ta-c fs-n">
							<thead>
								<tr>
									<th>Passenger Name</th>
									<th>Ticket ID</th>
									<th>Flight ID</th>
									<th>Booking ID</th>
									<th>Seat Type</th>
									<th>Seat Preference</th>
									<th>Meal Preference</th>
									<th>Flight Origin</th>
									<th>Flight Destination</th>
									<th>Departure Time</th>
									<th>Arrival Time</th>
									<th>Airline Name</th>
								</tr>	
							</thead>
							<tbody>
							<#list boardingPassList as boardingPass>
							<#if boardingPass.myTicket?? && boardingPass.myTicket.id?? && boardingPass.myTicket.seatType?? && boardingPass.myTicket.seatPref?? && boardingPass.myTicket.mealPref?? && boardingPass.myTicket.passengerName?? && boardingPass.myFlight.id?? && boardingPass.myBooking.id?? && boardingPass.myFlight.origin?? && boardingPass.myFlight.destination?? && boardingPass.myFlight.departureTime?? && boardingPass.myFlight.arrivalTime?? && boardingPass.myFlight.airlineName??>
								<tr>
									<td>${boardingPass.myTicket.passengerName}</td>
									<td>${boardingPass.myTicket.id}</td>
									<td>${boardingPass.myFlight.id}</td>
									<td>${boardingPass.myBooking.id}</td>
									<td><#if boardingPass.myTicket.seatType == "1">Business<#else>Economy</#if></td>
									<td>${boardingPass.myTicket.seatPref}</td>
									<td>${boardingPass.myTicket.mealPref}</td>
									<td>${boardingPass.myFlight.origin}</td>
									<td>${boardingPass.myFlight.destination}</td>
									<td>${boardingPass.myFlight.departureTime}</td>
									<td>${boardingPass.myFlight.arrivalTime}</td>
									<td>${boardingPass.myFlight.airlineName}</td>
								</tr>
							</#if>
							</#list>
							</tbody>
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