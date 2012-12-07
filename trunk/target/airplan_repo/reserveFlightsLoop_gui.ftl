<html>
	<head>
		<title>Reserve Another Flight</title>
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
						<li class="header-menu mm-item mm-item-link-sel hand" onclick="window.location.href=''">Search / Reserve Flights</a>
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
						<fieldset class="col-600" id="corner-rnd">
						<legend>Reservation Details</legend>
							<form name="form1" method="get" onsubmit="return isEmpty()" action="reserveFlightsLoop.action">
								<div class="labels-wide ta-r">
									<p><label for="passengerName">Passenger Name: &nbsp;</label></p>
									<p><label for="seatPreference">Seat Preference: &nbsp;</label></p>
									<p><label for="mealPreference">Meal Preference: &nbsp;</label></p>
								</div>
								<div class="inputs-wide">
									<input name="flightId" id="flightId" type="hidden" value=${flightId} />
									<input name="sessionToken" id="sessionToken" type="hidden" value=${sessionToken} />
									<input name="bookingId" id="bookingId" type="hidden" value=${bookingId} />
									<input name="seatType" id="seatType" type="hidden" value=${seatType} />
									<input name="passengerName" class="textbox-gen" id="passengerName" type="text" required />
									<div style="width:300px">
										<input type="radio" name="seatPreference" value="No Preference" checked /><label for="np">No Preference</label>
										<input type="radio" name="seatPreference" value="Window Seat" /><label for="window">Window Seat</label>
										<input type="radio" name="seatPreference" value="Aisle Seat" /><label for="aisle">Aisle Seat</label>
									</div>
									<div style="width:300px">
										<input type="radio" name="mealPreference" value="No Preference" checked /><label for="np">No Preference</label>
										<input type="radio" name="mealPreference" value="Vegetarian" /><label for="veg">Vegetarian</label>
									</div>
									<input type="submit" class="positive btn-gen" value="Reserve" />
								</div>
							</form>
						</fieldset>
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
					function isEmpty() {
						var inputfld_pname = document.getElementById('passengerName');
						if (inputfld_pname.value == "") {
							alert("Please enter the passenger's name");
							return false;
						}
					}
					</script>
				</div>
			</div>
		</div>
	</body>
</html>