<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="resources/js/jquery-min.js"></script>
<script type="text/javascript" src="resources/js/angular.min.js"></script>
<script type="text/javascript" src="resources/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="resources/js/AngularController/homeController.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h1>Rocket Chat</h1>
	<iframe id="rocketFrame" onload="afterLoading()" width="700px" height="500px"
		src="http://192.168.1.95:3000">
		<!-- <iframe src="http://localhost:3000/" width="700px" height="500px"> -->
		<p>Your browser does not support iframes.</p>
	</iframe>
	<input type="button" onclick="logout()" value="Logout">
</body>
<script type="text/javascript">
<!-- // 	$(document) -->
	<!-- // 					function() { -->
	<!-- // 	document.getElementById("tokenId").value = localStorage.username; -->
	<!-- // 						document.getElementById('rocketFrame').src = "http://192.168.1.226:3000?userToken=" -->
	<!-- // 							\	+ localStorage.username; -->
	<!-- // 	document.getElementById('rocketFrame').src="http://192.168.1.226:3000"; -->

	<!-- // 						  $.post("getUserStatus?username="+localStorage.uname+"&pwd" -->
	<!-- // 								  +localStorage.pwd, function(data, status){ -->
	<!-- // 						    }); -->

	<!-- // 					});  -->
	

// 	 afterLoading();
	function afterLoading(){
		if(localStorage.username){
			document.getElementById('rocketFrame').contentWindow.postMessage({
				  event: 'login-with-token',
				  loginToken: localStorage.username
				},"http://192.168.1.95:3000");
		}
		
	}
	
	window.onload = function() {
		  // A function to process messages received by the window.
		  function receiveMessage(e) {
		    // Check to make sure that this message came from the correct domain.
		    if (e.origin == "http://192.168.1.95:3000")
		      return;
		    else
		    	afterLoading();
		  }

		  // Setup an event listener that calls receiveMessage() when the window
		  // receives a new MessageEvent.
		  window.addEventListener('message', receiveMessage);
		}
	
	function logout(){
		$.ajax({
			  url: "logout"
			});
		localStorage.removeItem("username");
		window.location.href = ("home");
	}
	
		function checkIframeLoaded() {
		    // Get a handle to the iframe element
		    var iframe = document.getElementById('rocketFrame');
		   /*  var iframeDoc = iframe.contentDocument; */

		    // Check if loading is complete
		        //iframe.contentWindow.alert("Hello");
// 		        iframe.contentWindow.onload = function(){
// 		            alert("I am loaded");
// 		            afterLoading();
// 		        };
		        // The loading is complete, call the function we want executed once the iframe is loaded

		    // If we are here, it is not loaded. Set things up so we check   the status again in 100 milliseconds
		    window.setTimeout(checkIframeLoaded, 100);
		}

</script>
</html>