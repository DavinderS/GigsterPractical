var module = angular.module('expenseModule')
module.controller('registerController', ['$scope', '$http', '$location', function($scope, $http, $location) {
	$scope.admin = "0";
	$scope.register = function() {
		if ($scope.userName && $scope.password) {
			$http({
				method: 'POST',
				url: 'http://localhost:8080/createUser?username='+$scope.userName+'&password='+$scope.password+'&admin='+$scope.admin
			}).then(function onSuccess(response) {
				// Log the user in right after registering
				sessionStorage.setItem('username',response.data.userName);
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
