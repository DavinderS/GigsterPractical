var module = angular.module('expenseModule', ['ngRoute', 'lightGrid', 'lightGridControls']);
module.config(function ($routeProvider,$locationProvider) {
	// Use this to remove the #! from the URL
	$locationProvider.html5Mode(true);

    $routeProvider.when('/', {
		templateUrl: 'pages/login/login.html',
		controller: 'loginController'
	}).when('/register', {
		templateUrl: 'pages/register/register.html',
		controller: 'registerController'
	}).when('/home', {
		templateUrl: 'pages/home/home.html',
		controller: 'homeController'
	}).when('/report', {
		templateUrl: 'pages/report/report.html',
		controller: 'reportController'
	});
})

