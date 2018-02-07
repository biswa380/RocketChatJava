/**
 * 
 */
var app = angular.module('chatApp',[]).controller('ChatController', 
function($scope, $http, $location){
	$scope.login = function(){
		$http.post("login?username="+$scope.username+'&pwd='+$scope.pwd)
		.then(function(response){
			$scope.username=response.data;
			localStorage.uname = $scope.username;
			localStorage.pwd = $scope.pwd;
			if($scope.username!='failed' && $scope.username!=undefined){
				$('#loginModal').modal('hide');
//				$location.path('/chatPage').replace();
//				$scope.$apply()
				localStorage.username = $scope.username.loginToken;
			}
//			else
//				$location.path('/home');
		},function(error){
			console.log(error);
		});
	};
	
	function genericSuccess (res) {
		  return res.data.data; // yes, really.
		}

	$scope.rocketChatRegister = function(){
		$http.post("register?name="+$scope.user.name+"&username="+$scope.user.username+'&newpwd='+$scope.user.pwd+
				'&newemail='+$scope.user.email)
			.then(function(res){
				
			console.log("success");
		});	
		
//		$http.post('http://192.168.1.226:3000/api/v1/users.register', $scope.user).then(function(success) {
//		    return genericSuccess(success);
//		  });
//		$http.post("http://192.168.1.226:3000/api/v1/users.register?name="+$scope.user.name+'&pass='+$scope.user.pwd+
//				'&email='+$scope.user.email+'&username='+$scope.user.email.split("@", 1)[0])
//		.then(function(response){
//			console.log("success");
//		});
		
	};
	
	/*$http.post("http://192.168.1.226:3000/api/v1/login?username="+$scope.username+'&password='+$scope.pwd)
		.then(function(response){
			$scope.result=response.data;
		},function(error){
			console.log(error);
		});*/
	
});
