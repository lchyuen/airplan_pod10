<html>
	<head>
		<title>Please Log In</title>
		<link rel="stylesheet" href="css/airplan_css.css" />
	</head>
	<body class="bg-gray">
		<div class="bg-header">
			<div class="header alt-font">
				<div class="header-logo">
					<img width="176" height="62" alt="AirPlan" src="css/logoairplan.PNG">
				</div>
				<div class="header-links">
					<div class="header-pers fs-xs">
						<div class="header-banner">
						</div>
					</div>
				</div>
			</div>
		</div>		
		<div class="bg">
			<div class="wrap">
				<div class="content-1024">
					<div class="col-1024">
						<div class="p-basic"></div>
						<fieldset class="col-480" id="corner-rnd">
						<legend>Login</legend>
						<form name="form1" method="get" onsubmit="return isEmpty()" action="login.action">
							<div class="labels-wide ta-r">
								<p><label for="emailAddress">Email: &nbsp;</label>
								<p><label for="password">Password: &nbsp;</label></p>
							</div>
							<div class="inputs-wide">
								<p><input name="emailAddress" class="textbox-gen" id="emailAddress" type="email" value="" required /></p>
								<p><input name="password" class="textbox-gen" id="password" type="password" value="" required /></p>
								<p class="ta-c">
									<input type="submit" class="positive btn-gen" value="Login" />
									<hr><center><a href="staffSignIn.action">Administrator?</a> | <a href="createUserInput.action">I am a new customer...</a></center>
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
						var inputfld_eml = document.getElementById('emailAddress');
						var inputfld_pwd = document.getElementById('password');
						if (inputfld_eml.value == "" || inputfld_pwd.value == "") {
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