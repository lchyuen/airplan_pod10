<html>
	<head>
		<title>Admin: Flight Statistics</title>
		<link rel="stylesheet" href="css/airplan_css.css" />
	</head>
	<body class="bg-gray">
		<div class="bg-header">
			<div class="header alt-font" style="background-image:url(imgs/3.png)">
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
						<li class="header-menu mm-item mm-item-link hand" onclick="window.location.href='staffManage.action?sessionToken=${sessionToken}'">Manage User Bookings
						</li>
						<li class="header-menu mm-item mm-item-link hand" onclick="window.location.href='selectManifest.action?sessionToken=${sessionToken}'">View Flight Manifests
						</li>
						<li class="header-menu mm-item mm-item-link-sel hand" onclick="window.location.href='viewStatistics.action?sessionToken=${sessionToken}'">View Flight Statistics
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
						<#if user?? && user.userType == 2>
						<#if Stat??>
						<table id="ms_table" class="row-focus ta-c fs-n">
							<thead>
								<tr>
									<th>Flights Delayed</th>
									<th>Flights Early</th>
									<th>Flights On Time</th>
									<th>Flights Full</th>
									<th>Flights Half Empty</th>
					           	</tr>
					         </thread>
							<tbody>
							<#if Stat.flightsDelayed?? && Stat.flightsEarly?? && Stat.flightsOnTime?? && Stat.flightsFull?? && Stat.flightsHalfEmpty??>
								<tr>
									<td>${Stat.flightsDelayed}</td>
									<td>${Stat.flightsEarly}</td>
									<td>${Stat.flightsOnTime}</td>
									<td>${Stat.flightsFull}</td>
									<td>${Stat.flightsHalfEmpty}</td>
								</tr>
							</#if>
							</tbody>
						<#else>
							Statistics Not Available.
						</#if>
						</table>
						<div class="p-basic"></div>
						<div class="fl-r">
							<table id="ft_table" class="row-focus ta-c t_tbl fs-n">
								<thead>
									<tr>
										<th>Flight To</th>
										<th>Number of Flights</th>
									</tr>
								</thead>
								<tbody>
								<#list Stat.flightOriginMap?keys as origin>
									<tr>
										<td>${origin}</td>
										<td>${Stat.flightOriginMap.get(origin)}</td>
									</tr>
								</#list>
								</tbody>
							</table>
						</div>
						<div class="fl_l">
							<table id="ff_table" class="row-focus ta-c t_tbl fs-n">
								<thead>
									<tr>
										<th>Flight From</th>
										<th>Number of Flights</th>
									</tr>
								</thead>
								<tbody>
								<#list Stat.flightDestinationMap?keys as destination>
									<tr>
										<td>${destination}</td>
										<td>${Stat.flightDestinationMap.get(destination)}</td>
									</tr>
								</#list>
								</tbody>
							</table>
						</div>
						<div class="p-basic"></div>
						<table id="mpd_table" class="row-focus ta-l fs-n">
							<thead>
								<tr>
									<th>Most Popular Destination</th>
								</tr>
							 </thread>
							<tbody>
							<#if Stat.mostPopular??>
								<tr>
									<td>${Stat.mostPopular}</td>
								</tr>
							</#if>
							</tbody>
						</table>
						<#else>
						<div id="corner-rnd" class="col-600 ta-c fs-s bg-gray">
							Sorry, only managers have access to flight statistics.
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
					<script src="js/jquery-1.8.2.js"/></script>
					<script src="js/jquery.tablescroll.js"></script>
					<script>
					jQuery(document).ready(function($) {
						$('#ft_table').tableScroll({height:222});
						$('#ff_table').tableScroll({height:222});
					});
					</script>
				</div>
			</div>
		</div>
	</body>
</html>