var module = angular.module('expenseModule')
module.controller('reportController', ['$scope', '$http', '$location', function($scope, $http, $location) {
    var amountPerWeekDict = {};
    if (sessionStorage.getItem('userid') != null) {
        $http({
            method: 'GET',
            // We set admin to 0 because we only want our own items
            url: "http://localhost:8080/getExpenses?userid=" + sessionStorage.getItem('userid') + "&admin=" + 0
        }).then(function onSuccess(response) {
            // It is better to filter on the server side, but no time
            $scope.weeks = 3;
            $scope.model = [];
            var currentDate = new Date();
            var startingDate = new Date(currentDate.getTime() - ($scope.weeks * 7 * 24 * 60 * 60 * 1000));
            for (i in response.data) {
                var expenseTime = new Date(response.data[i].date_created).getTime();
                // This returns the number of weeks the expense's date is behind the current date
                var weeksBehind = Math.floor((currentDate.getTime() - (expenseTime)) / (1 * 7 * 24 * 60 * 60 * 1000));
                if (amountPerWeekDict[weeksBehind] != null) {
                    amountPerWeekDict[weeksBehind] += response.data[i].amount;
                } else {
                    amountPerWeekDict[weeksBehind] = response.data[i].amount;
                }
            }
            for (j in amountPerWeekDict) {
                if (j <= $scope.weeks && j >= 0) {
                    $scope.model.push({
                        amount: amountPerWeekDict[j],
                        week: j
                    });
                }
            }
        }, function onError(response) {
            console.log("Failed to load expenses");
        })
        $scope.refresh = function() {
            $scope.model = [];
            for (j in amountPerWeekDict) {
                if (j <= $scope.weeks && j >= 0) {
                    $scope.model.push({
                        amount: amountPerWeekDict[j],
                        week: j
                    });
                }

            }
        }
    } else {
        $location.path('/');
    }
}]);