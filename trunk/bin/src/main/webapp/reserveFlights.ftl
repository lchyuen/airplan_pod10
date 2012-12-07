<html>
	<head>
		<title>Flight Reservation</title>
		<link rel="stylesheet" type="text/css" href="css/airplan_css.css" />
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
						<li class="header-menu mm-item mm-item-link-sel hand" onclick="return youSure();window.location.href=''">Search / Reserve Flights
						</li>
						<li class="header-menu mm-item mm-item-link hand" onclick="return youSure();window.location.href='viewBookingList.action?sessionToken=${sessionToken}'">Manage Bookings
						</li>
						<li class="header-menu mm-item mm-item-link hand" onclick="return youSure();window.location.href='viewTransactions.action?sessionToken=${sessionToken}'">View History
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
						<#if user??>
							<div id="corner-rnd" class="col-600 bg-gray">
								You have successfully set up your booking options.<br>
								Please review your flight settings below, and select Confirm to proceed to payment.<br>
								If you wish to add another flight, please select Add Another Flight.
							</div>
						</#if>
						<table id="reserve_table" class="row-focus ta-c fs-n">
						<#if flightList??>
							<thead>
								<tr>
									<th>Passenger Name</th>
									<th>Flight Number</th>
									<th>Origin</th>
									<th>Destination</th>
									<th>Departure Time</th>
									<th>Arrival	Time</th>
									<th>Airline</th>
									<th>Travel Class</th>
									<th>Meal Preference</th>
									<th>Seat Preference</th>
									<th>Fare Price</th>
								</tr>
							</thead>
						<#list flightList as flight>
							<tbody>
							<#if user.name?? && passengerName?? && seatType?? && flight.id?? && flight.origin?? && flight.destination?? && flight.departureTime?? && flight.arrivalTime?? && flight.airlineName?? && flight.cost??>
								<tr>
									<td>${passengerName}</td>
									<td>${flight.id}</td>
									<td>${flight.originName}</td>
									<td>${flight.destinationName}</td>
									<td>${flight.departureTime}</td>
									<td>${flight.arrivalTime}</td>
									<td>${flight.airlineName}</td>
									<td><#if seatType == "1">Business<#else>Economy</#if></td>
									<td>${mealPreference}</td>
									<td>${seatPreference}</td>
									<td>${flight.cost}</td>
								</tr>
							</#if>
							</tbody>
						</#list>
						<#else>
							Flight List was not set
						</#if>
						</table>
						<div class="col-600">
							<form name="form1" method="get" action="/airplan_repo/searchFlightsLoopGui.action">
								<input name="sessionToken" id="sessionToken" type="hidden" value=${sessionToken} />
								<input name="bookingId" id="bookingId" type="hidden" value=${bookingId} />
								<button type="submit" class="positive btn-gen fl-l" name="Submit">Add Another Flight</button>
							</form>
							<form name="form2" method="get" action="/airplan_repo/searchFlightsReturnGui.action">
								<input name="sessionToken" id="sessionToken" type="hidden" value=${sessionToken} />
								<input name="bookingId" id="bookingId" type="hidden" value=${bookingId} />
								<input name="origin" id="origin" type="hidden" value=${destination} />
								<input name="destination" id="destination" type="hidden" value=${origin} />
								<button type="submit" class="positive btn-gen fl-l" name="Submit">Find Return Flight</button>
							</form>
							<form name="form3" method="get" action="/airplan_repo/makePaymentGui.action">
								<input name="sessionToken" id="sessionToken" type="hidden" value=${sessionToken} />
								<input name="bookingId" id="bookingId" type="hidden" value=${bookingId} />
								<button type="submit" class="positive btn-gen fl-r" name="Submit">Checkout</button>
							</form>
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
					<script>
					function confirmThis() {
						var confirmed = confirm("You are already here...\nClicking Okay will clear the current page");
						return confirmed;
					}
					function youSure() {
						var waitasec = confirm("If you navigate away from this page,\nyour current bookings will be lost.\nContinue?");
						return waitasec;
					}
					</script>
				</div>
			</div>
		</div>
	</body>
</html>