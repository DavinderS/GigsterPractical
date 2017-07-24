var module = angular.module('expenseModule')
module.controller('loginController', ['$scope', '$http', '$location', function($scope, $http, $location) {
	$scope.login = function() {
		if ($scope.userName && $scope.password) {
			$http({
				method: 'POST',
				url: 'http://localhost:8080/login?username='+$scope.userName+'&password='+$scope.password
			}).then(function onSuccess(response) {
				// This is obviously not the best way to do it. Need to have a token as well as server side validation, but not enough time
				console.log(response.data)
				sessionStorage.setItem('userid',response.data.userId);
				sessionStorage.setItem('admin',response.data.admin);
				$location.path("/home")
			}, function onError(response) {
				console.log(response.data)
			})
		} else {
			alert("Please fill out both fields");
		}
	}
}]);
