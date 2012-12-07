<html>
	<head>
		<title>Search for More Flights</title>
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
								<li class="mm-item fs-s">
									Welcome, ${user.name} &nbsp; &nbsp; <a class="mm-item-link c9 fs-n" href="signIn.action">Sign Out</a>
								</li>
							</ul>
						</div>
					</div>
					<ul class="header-menus fs-m menu">
						<li class="header-menu mm-item mm-item-link-sel hand" onclick="window.location.href='searchFlightsGui.action?sessionToken=${sessionToken}'">Search / Reserve Flights
						</li>
						<li class="header-menu mm-item mm-item-link hand" onclick="youSure2()">Manage Bookings
						</li>
						<li class="header-menu mm-item mm-item-link hand" onclick="youSure3()">View History
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
						<table id="search_table" class="row-focus-fancy ta-c fs-n">
						<#if flightList??>
							<#if (flightList)?has_content>
							<thead>
								<tr>
									<th>Flight Number</th>
					            	<th>Origin</th>
					            	<th>Destination</th>
					            	<th>Departure Time</th>
					            	<th>Arrival Time</th>
					            	<th>Airline</th>
					            	<th>Available Seats</th>
									<th>Fare Price</th>
					           	</tr>
							</thead>
							<#list flightList as flight>
							<tbody>
								<#if flight.id?? && flight.origin?? && flight.destination?? && flight.departureTime?? && flight.arrivalTime?? && flight.airlineName?? && flight.availableSeats?? && flight.cost??>
								<tr>
									<td class="hand" onclick="window.location.href='reserveFlightsLoopGui.action?sessionToken=${sessionToken}&bookingId=${bookingId}&flightId=${flight.id}&seatType=${seatType}'">${flight.id}</td>
									<td class="hand" onclick="window.location.href='reserveFlightsLoopGui.action?sessionToken=${sessionToken}&bookingId=${bookingId}&flightId=${flight.id}&seatType=${seatType}'">${flight.originName}</td>
									<td class="hand" onclick="window.location.href='reserveFlightsLoopGui.action?sessionToken=${sessionToken}&bookingId=${bookingId}&flightId=${flight.id}&seatType=${seatType}'">${flight.destinationName}</td>
									<td class="hand" onclick="window.location.href='reserveFlightsLoopGui.action?sessionToken=${sessionToken}&bookingId=${bookingId}&flightId=${flight.id}&seatType=${seatType}'">${flight.departureTime}</td>	
									<td class="hand" onclick="window.location.href='reserveFlightsLoopGui.action?sessionToken=${sessionToken}&bookingId=${bookingId}&flightId=${flight.id}&seatType=${seatType}'">${flight.arrivalTime}</td>
									<td class="hand" onclick="window.location.href='reserveFlightsLoopGui.action?sessionToken=${sessionToken}&bookingId=${bookingId}&flightId=${flight.id}&seatType=${seatType}'">${flight.airlineName}</td>
									<td class="hand" onclick="window.location.href='reserveFlightsLoopGui.action?sessionToken=${sessionToken}&bookingId=${bookingId}&flightId=${flight.id}&seatType=${seatType}'">${flight.availableSeats}</td>
									<td class="hand" onclick="window.location.href='reserveFlightsLoopGui.action?sessionToken=${sessionToken}&bookingId=${bookingId}&flightId=${flight.id}&seatType=${seatType}'">${flight.cost}</td>
								</tr>
								</#if>
							</tbody>
							</#list>
							<#else>
							<div class="ta-c">
								<div id="corner-rnd" class="col-600 bg-gray">No flights matching set criteria for selected date.</div>
								<div class="p-basic"></div>
								<a href="javascript:history.back()"><button type="submit" class="positive btn-gen fl-c">Search Again</button></a>
							</div>
							</#if>
						<#else>
							Flight List was not set
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
					<script>
					function youSure2() {
						var waitasec = confirm("If you navigate away from this page,\nyour current bookings will be lost.\nContinue?");
						if (waitasec == true){return window.location.href='viewBookingList.action?sessionToken=${sessionToken}'}
					}
					function youSure3() {
						var waitasec = confirm("If you navigate away from this page,\nyour current bookings will be lost.\nContinue?");
						if (waitasec == true){return window.location.href='viewTransactions.action?sessionToken=${sessionToken}'}
					}
				</div>
			</div>
		</div>
	</body>
</html>