<html>
	<head>
		<title>Search for Flights</title>
		<link rel="stylesheet" href="css/airplan_css.css" />
		<link rel="stylesheet" href="css/jquery-ui.css" />
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
						<legend>Search for a Flight</legend>
							<form name="form1" method="get" onsubmit="return isEmpty()" action="searchFlights.action">
								<div class="labels">
									<p><label for="destination">Destination:</label></p>
									<p><label for="origin">Origin:</label></p>
									<p><label for="departureTime">Departure Date:</label></p>
									<p><label for="requiredSeats">Required Seats:</label></p> 
									<p><label for="seatType">Seat Type:</label></p>
								</div>
								<div class="inputs">
									<input name="sessionToken" id="sessionToken" type="hidden" value=${sessionToken} />
									<input name="destination" class="textbox-gen places" id="destination" type="text" required />
									<input name="origin" class="textbox-gen places" id="origin" type="text" required />
									<input name="departureTime" class="textbox-gen datepicker" id="departureTime" type="text" required />
									<input name="requiredSeats" class="textbox-gen" id="requiredSeats" type="number" min="1" max="250" required />
									<div>
										<input type="radio" name="seatType" value="2" checked /><label for="2">Economy</label>
										<input type="radio" name="seatType" value="1" /><label for="1">Business</label>
									</div>
									<input type="submit" class="positive btn-gen" value="Search" />
								</div>
								<div><img class="fl-r" src="css/map-icon.png" /></div>
							<form>
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
					<script src="js/jquery-1.8.2.js"></script>
					<script src="js/jquery-ui.js"></script>
					<script>
					$(function() {
						var places = [
							"Atlanta","Bangkok","Beijing","Calgary","Chicago","Edmonton","Frankfurt","Hong Kong","Houston","Kelowna",
							"London","Los Angeles","Madrid","Montreal","New York","Orlando","Osaka","Ottawa","Paris","Phoenix","San Francisco",
							"Saskatoon","Seattle","Shanghai","Singapore","Tokyo","Toronto","Vancouver","Whitehorse","Winnipeg","Yellowknife"
						];
						$( ".datepicker" ).datepicker({dateFormat: "yy-mm-dd"});
						$( ".places" ).autocomplete({source: places});
					});
					function isEmpty() {
						var inputfld_dest = document.getElementById('destination');
						var inputfld_orig = document.getElementById('origin');
						var inputfld_dtime = document.getElementById('departureTime');
						var inputfld_reqst = document.getElementById('requiredSeats');
						if (inputfld_dest.value == "" || inputfld_orig.value == "" || inputfld_dtime.value == "" || inputfld_reqst.value == "") {
							alert("Missing Field(s)");
							return false;
						}
					}
					</script>
				</div>
			</div>
		</div>
	</body>
</html>