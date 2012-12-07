<html>
	<head>
		<title>Flight Reservation Payment</title>
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
								<li class="mm-item">
									Welcome, *${user.name}* &nbsp; &nbsp; <a class="mm-item-link c9" href="signIn.action">Sign Out</a>
								</li>
							</ul>
						</div>
					</div>
					<ul class="header-menus fs-m menu">
						<li class="header-menu mm-item mm-item-link-sel hand" onclick="window.location.href=''">Search / Reserve Flights
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
						<#if sessionToken??>
							<div id="corner-rnd" class="col-600 bg-gray">
								Please review your reservation details below.<br>
								You may modify your booking(s) after payment has been processed.
							</div>
						</#if>
						<table id="pay_table" class="row-focus ta-c fs-n">
							<thead>
								<tr>
									<th>Flight Number</th>
									<th>Passenger Name</th>
									<th>Travel Class</th>
									<th>Seat Preference</th>
									<th>Meal Preference</th>
									<th>Fare Price</th>
								</tr>
							</thead>
						<#if ticketList?? >
						<#list ticketList as ticket>
							<tbody>
							<#if ticket.flightId?? && ticket.seatType?? && ticket.seatPref?? && ticket.mealPref?? && ticket.passengerName?? && ticket.cost??>
								<tr>
									<td>${ticket.flightId}</td>
									<td>${ticket.passengerName}</td>
									<td><#if ticket.seatType == "1">Business<#else>Economy</#if></td>
									<td>${ticket.seatPref}</td>
									<td>${ticket.mealPref}</td>									
									<td>${ticket.cost}</td>
								</tr>
							</#if>
							</tbody>
						</#list>
						<#else>
							Ticket List was not set
						</#if>
						</table>
						<fieldset class="col-600" id="corner-rnd">
						<legend>Reservation Details</legend>
							<form name="form1" method="get" action="/airplan_repo/makePayment.action">
								<div class="labels-wide">
									<p><label for="creditCardName">Credit Card Holder:</label></p>
									<p><label for="creditCardNum">Credit Card Number:</label></p>
									<p><label for="isPayByPoints">Payment Method:</label></p>
									<p><label for="subtotal">Total:</label></p>
								</div>
								<div class="inputs-wide">
									<input name="sessionToken" id="sessionToken" type="hidden" value=${sessionToken} />
									<input name="bookingId" id="bookingId" type="hidden" value=${bookingId} />
									<input name="subtotal" id="subtotal" type="hidden" value=${subtotal} />
									<p><input name="creditCardName" class="textbox-gen" id="creditCardName" type="text" /></p>
									<p><input name="creditCardNum" class="textbox-gen" id="creditCardNum" type="text" /></p>
									<p><input type="radio" name="isPayByPoints" checked value="0" /><label for="cc">Credit Card</label>
									<#if canPayByPoints == "false">
										<input type="radio" disabled /><label for="lp">Loyalty Points</label>
									<#else>
										<input type="radio" name="isPayByPoints" value="1" /><label for="lp">Loyalty Points(${availPoints} points)</label>
									</#if></p>
									<p><input name="subtotal_show" type="text" style="background-color:#fff" value=${subtotal} disabled /></p>
									<div style="width:111px"><input type="submit" class="positive btn-gen fl-l" value="Pay" /></div>
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