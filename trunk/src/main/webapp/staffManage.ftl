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
						<fieldset class="col-480" id="corner-rnd">
						<legend>Search for Customer Bookings</legend>
						<form name="form1" method="get" onsubmit="return isEmpty()" action="staffGetCustomerId.action">
							<div class="labels-wide ta-r">
								<p><label for="email">Customer E-mail: &nbsp;</label>
							</div>
							<div class="inputs-wide">
								<p><input name="email" class="textbox-gen" id="email" type="email" value="" required /></p>
								<p><input name="sessionToken" id="sessionToken" type="hidden" value=${sessionToken} /></p>
								<p class="ta-c">
									<input type="submit" class="positive btn-gen fl-c" value="Retrieve Bookings" />
								</p>
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
					function isEmpty() {
						var inputfld_email = document.getElementById('email');
						if (inputfld_email.value == "") {
							alert("Please enter the customer's email");
							return false;
						}
					}
					</script>
				</div>
			</div>
		</div>
	</body>
</html>