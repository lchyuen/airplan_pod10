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
									Welcome, ${user.name} &nbsp; &nbsp; <a class="mm-item-link c9 fs-n" href="signIn.action">Sign Out</a>
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
						<#if customerId != -1 && customerId != -2>
							The customer ID is ${customerId}. If you are not redirected, click <a href="staffViewBookingList.action?sessionToken=${sessionToken}&customerToken=${customerToken}">here</a>.
     						${response.sendRedirect("staffViewBookingList.action?sessionToken=${sessionToken}&customerToken=${customerToken}")}
						<#elseif customerId == -2>
							<div class="ta-c">
								<div id="corner-rnd" class="col-600 bg-gray">Sorry, you cannot log in as a staff member.</div>
								<div class="p-basic"></div>
								<a href="javascript:history.back()"><button type="submit" class="positive btn-gen fl-c">Try Again</button></a>
							</div>
						<#else> 
    						<div class="ta-c">
								<div id="corner-rnd" class="col-600 bg-gray">There is no customer matching that email. Please ensure that it was entered correctly.</div>
								<div class="p-basic"></div>
								<a href="javascript:history.back()"><button type="submit" class="positive btn-gen fl-c">Try Again</button></a>
							</div>
						</#if> 
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