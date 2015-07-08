'use strict';

angular.module('teamtrackerApp')
    .controller('CustomerDetailController', function ($scope, $stateParams, Customer, User) {
        $scope.customer = {};
        $scope.load = function (id) {
            Customer.get({id: id}, function(result) {
              $scope.customer = result;
            });
        };
        $scope.load($stateParams.id);
    });
