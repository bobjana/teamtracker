'use strict';

angular.module('teamtrackerApp')
    .controller('TracerEventDetailController', function ($scope, $stateParams, TracerEvent) {
        $scope.tracerEvent = {};
        $scope.load = function (id) {
            TracerEvent.get({id: id}, function(result) {
              $scope.tracerEvent = result;
            });
        };
        $scope.load($stateParams.id);
    });
