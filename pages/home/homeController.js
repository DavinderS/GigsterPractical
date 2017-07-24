var module = angular.module('expenseModule')
module.controller('homeController', ['$scope', '$http', '$location', '$route', function($scope, $http, $location, $route) {
    $scope.expenseContainerOpen = false;


    var convertDate = function(date) {
        // date object to YYYY-MM-DD HH:MM
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        var day = date.getDate();
        var hours = date.getHours();
        var minutes = date.getMinutes();
        var dateString = year + "-" + month + "-" + day + " " + hours + ":" + minutes
        return dateString;
    }


    if (sessionStorage.getItem('userid') != null) {
        $scope.userid = sessionStorage.getItem('userid');
        // If the user is logged in, get the expenses
        $http({
            method: 'GET',
            url: "http://localhost:8080/getExpenses?userid=" + sessionStorage.getItem('userid') + "&admin=" + sessionStorage.getItem('admin')
        }).then(function onSuccess(response) {
            for (i in response.data) {
                response.data[i].dateCreated = new Date(response.data[i].dateCreated)
            }
            $scope.model = response.data;
        }, function onError(response) {
            console.log("Failed to load expenses");
        })



        $scope.showNewExpenseContainer = function() {
            $scope.expenseContainerOpen = true;
        }


        $scope.hideNewExpenseContainer = function() {
            $scope.expenseContainerOpen = false;
        }


        $scope.createNewExpense = function() {
            if ($scope.newExpenseAmount == "" || $scope.newExpenseDatetime == null) {
                alert("Please fill out the amount and date fields")
            } else {
                var dateString = convertDate($scope.newExpenseDatetime)
                $http({
                    method: 'POST',
                    url: "http://localhost:8080/createExpense?datecreated=" + dateString + "&amount=" + $scope.newExpenseAmount + "&description=" + $scope.newExpenseDescription + "&userid=" + $scope.userid
                }).then(function onSuccess(response) {
                    $route.reload();
                }, function onError(response) {
                    alert("Invalid Entry, please review your input");
                })
            }
        }


        $scope.delete = function(expenseId) {
            if (confirm("Are you sure you want to delete this expense") == true) {
                $http({
                    method: 'POST',
                    url: "http://localhost:8080/deleteExpense?expenseid=" + expenseId
                }).then(function onSuccess(response) {
                    // TODO Running out of time, just refresh page for now. Actually need to update the grid without refreshing
                    $route.reload();
                }, function onError(response) {
                    alert("Something went wrong, please refresh the page");
                })
            }
        }


        $scope.save = function(row) {
            if (row.amount == "" || row.dateCreated == null) {
                alert("Please fill out the amount and date fields")
            } else {
                var dateString = convertDate(row.dateCreated);
                $http({
                    method: 'POST',
                    url: "http://localhost:8080/updateExpense?expenseid=" + row.expenseId + "&datecreated=" + dateString + "&amount=" + row.amount + "&description=" + row.description
                }).then(function onSuccess(response) {
                    // TODO Running out of time, just refresh page for now. Actually need to update the grid without refreshing
                    $route.reload();
                }, function onError(response) {
                    alert("Invalid Entry, please review your input");
                })
            }
        }

    } else {
        // if the user is not logged in, send them to the home page
        $location.path('/');
    }

    
    $scope.logOut = function() {
        sessionStorage.clear();
        $location.path('/');
    }

}]);