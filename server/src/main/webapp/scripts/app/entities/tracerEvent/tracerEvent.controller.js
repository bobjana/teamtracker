'use strict';

angular.module('teamtrackerApp')
    .controller('TracerEventController', function ($scope, TracerEvent, ParseLinks) {
        $scope.tracerEvents = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            TracerEvent.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.tracerEvents = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            TracerEvent.get({id: id}, function(result) {
                $scope.tracerEvent = result;
                $('#saveTracerEventModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.tracerEvent.id != null) {
                TracerEvent.update($scope.tracerEvent,
                    function () {
                        $scope.refresh();
                    });
            } else {
                TracerEvent.save($scope.tracerEvent,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            TracerEvent.get({id: id}, function(result) {
                $scope.tracerEvent = result;
                $('#deleteTracerEventConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            TracerEvent.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteTracerEventConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $('#saveTracerEventModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.tracerEvent = {date: null, customerId: null, representativeId: null, type: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
