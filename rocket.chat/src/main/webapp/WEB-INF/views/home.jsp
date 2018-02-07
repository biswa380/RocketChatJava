<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<title>Home</title>
<link rel="stylesheet" href="resources/css/bootstrap.min.css">
<link rel="stylesheet" href="resources/css/loginModal.css">
<script type="text/javascript" src="resources/js/jquery-min.js"></script>
<script type="text/javascript" src="resources/js/angular.min.js"></script>
<script type="text/javascript" src="resources/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="resources/js/AngularController/homeController.js"></script>
</head>
<body>
	<h1>Welcome to Rocket.Chat demo</h1>

	<P>The time on the server is ${serverTime}.</P>

	<!-- <iframe src="http://192.168.1.226:3000/" width="700px" height="500px">
<iframe src="http://localhost:3000/" width="700px" height="500px">
  <p>Your browser does not support iframes.</p>
</iframe> -->
	<!-- <input type="submit" value="Login" id="submit" /> -->


<!-- 	<iframe src="http://192.168.1.226:3000/" width="700px" height="500px"> -->
<!-- <!-- 		<p>Your browser does not support iframes.</p> --> 
<!-- 	</iframe> -->
	<div class="row" ng-app="chatApp" ng-controller="ChatController">

		<!-- Signin & Signup -->
		<a class="btn btn-primary" data-toggle="modal"
			data-target=".login-register-form" style="margin-left: 100px;"> Login - Registration Modal </a>
		<a class="btn btn-primary" href="chatPage"> Let's Chat</a>
			
		<!-- Login / Register Modal-->
		<div class="modal fade login-register-form" id="loginModal"
			role="dialog">
			<div class="modal-dialog modal-sm">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<span class="glyphicon glyphicon-remove"></span>
						</button>
						<ul class="nav nav-tabs">
							<li class="active"><a data-toggle="tab" href="#login-form">
									Login <span class="glyphicon glyphicon-user"></span>
							</a></li>
							<li><a data-toggle="tab" href="#registration-form">
									Register <span class="glyphicon glyphicon-pencil"></span>
							</a></li>
						</ul>
					</div>
					<div class="modal-body">
						<div class="tab-content">
							<div id="login-form" class="tab-pane fade in active">
								<form>
									<div class="form-group">
										<label for="email">Username:</label> <input type="text"
											class="form-control" id="username"
											placeholder="Enter username" ng-model="username">
									</div>
									<div class="form-group">
										<label for="pwd">Password:</label> <input type="password"
											class="form-control" id="pwd" placeholder="Enter password"
											ng-model="pwd">
									</div>
									<div class="checkbox">
										<label><input type="checkbox" name="remember">
											Remember me</label>
									</div>
									<button type="submit" class="btn btn-default"
										ng-click='login()'>Login</button>
								</form>
							</div>
							<div id="registration-form" class="tab-pane fade">
								<form>
									<div class="form-group">
										<label for="name">Your Name:</label> <input type="text"
											class="form-control" id="name" placeholder="Enter your name" ng-model="user.name"
											name="name">
									</div>
									<div class="form-group">
										<label for="username">Username:</label> <input type="text"
											class="form-control" id="registerUsername" placeholder="Enter your name" ng-model="user.username"
											name="name">
									</div>
									<div class="form-group">
										<label for="newemail">Email:</label> <input type="email"
											class="form-control" id="newemail" ng-model="user.email"
											placeholder="Enter new email" name="newemail">
									</div>
									<div class="form-group">
										<label for="newpwd">Password:</label> <input type="password"
											class="form-control" id="newpwd" placeholder="New password" ng-model="user.pwd"
											name="newpwd">
									</div>
									<button type="submit" class="btn btn-default" ng-click="rocketChatRegister()">Register</button>
								</form>
							</div>

						</div>
						
					</div>
					<!--                                    <div class="modal-footer">-->
					<!--                                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>-->
					<!--                                    </div>-->
				</div>
			</div>
		</div>
	</div>
	<script>
		$("#submit").click(function() {
			$.post("http://192.168.1.219:3000/api/v1/login", {
				username : "biswabhusa.pradhan",
				password : "pass@123"
			}, function(data, status) {
				alert("Data: " + data + "\nStatus: " + status);
				window.parent.postMessage({
					event : 'login-with-token',
					loginToken : data.data.authToken
				}, "*");
			});
			/* window.parent.postMessage({
				  event: 'try-iframe-login'
				},"*"); */
		});
	</script>
	
</body>
</html>
