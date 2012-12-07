<html>
	<head>
		<title>View Flight Manifests</title>
		<link rel="stylesheet" href="css/airplan_css.css" />
	</head>
	<body class="bg-gray">
		<div class="bg-header">
			<div class="header alt-font" style="background-image:url(imgs/2.png)">
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
								<li class="mm-item">
									Welcome, *${user.name}* &nbsp; &nbsp; <a class="mm-item-link c9" href="staffSignIn.action">Sign Out</a>
								</li>
							</ul>
						</div>
					</div>
					<ul class="header-menus fs-m menu">
						<li class="header-menu mm-item mm-item-link hand" onclick="window.location.href='staffManage.action?sessionToken=${sessionToken}'">Manage User Bookings
						</li>
						<li class="header-menu mm-item mm-item-link-sel hand" onclick="window.location.href='selectManifest.action?sessionToken=${sessionToken}'">View Flight Manifests
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
						<div id="corner-rnd" class="col-600 ta-c fs-s bg-gray">
							${message}
						</div>
						<div class="p-basic"></div>
						<table id="flights" class="row-focus ta-c fs-n">
							<thead>
								<tr>
									<th>Flight ID</th>
									<th>Origin</th>
									<th>Destination</th>
									<th>Departure Time</th>
									<th>Arrival Time</th>
									<th>Airline</th>
								</tr>
							</thead>
							<#if selectionList??>
							<tbody>
							<#list selectionList as selection>
								<tr>
									<td class="hand" onclick="window.location.href='viewManifest.action?sessionToken=${sessionToken}&flightManifest_flightId=${selection.id}'">${selection.id}</td>
									<td class="hand" onclick="window.location.href='viewManifest.action?sessionToken=${sessionToken}&flightManifest_flightId=${selection.id}'">${selection.origin}</td>
									<td class="hand" onclick="window.location.href='viewManifest.action?sessionToken=${sessionToken}&flightManifest_flightId=${selection.id}'">${selection.destination}</td>
									<td class="hand" onclick="window.location.href='viewManifest.action?sessionToken=${sessionToken}&flightManifest_flightId=${selection.id}'">${selection.departureTime}</td>
									<td class="hand" onclick="window.location.href='viewManifest.action?sessionToken=${sessionToken}&flightManifest_flightId=${selection.id}'">${selection.arrivalTime}</td>
									<td>${selection.airlineName}</td>
								</tr>
							</#list>
							</tbody>
							<#else>
								Error!
							</#if>
						</table>
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
						$('#flights').tableScroll({height:320,width:1024});
					});
					</script>
				</div>
			</div>
		</div>
	</body>
</html>