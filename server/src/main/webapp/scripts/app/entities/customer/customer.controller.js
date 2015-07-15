'use strict';

angular.module('teamtrackerApp')
    .controller('CustomerController', function ($scope, Customer, User, ParseLinks) {
        $scope.customers = [];
        $scope.users = User.query();
        $scope.page = 1;


        $scope.location = new google.maps.LatLng(0, 0)

        $scope.marker;
        $scope.coverageArea;


        $scope.loadAll = function() {
            Customer.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                for (var i = 0; i < result.length; i++) {
                    $scope.customers.push(result[i]);
                }
            });
        };
        $scope.reset = function() {
            $scope.page = 1;
            $scope.customers = [];
            $scope.loadAll();
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {

            Customer.get({id: id}, function(result) {
                $scope.customer = result;
                var loc = result.geoLocation.split(',');
                console.log(loc);
                $scope.location = new google.maps.LatLng(loc[0], loc[1]);

                $('#saveCustomerModal').modal('show');
            });


            $('#saveCustomerModal').on('shown.bs.modal', function (e) {
                google.maps.event.trigger($scope.map, 'resize');
                $scope.map.setCenter($scope.location);
                if ($scope.marker == null) {
                    $scope.marker = new google.maps.Marker({
                        map: $scope.map,
                        draggable: true,
                        animation: google.maps.Animation.DROP,
                    });
                }
                $scope.marker.setPosition($scope.location);


                if ($scope.coverageArea == null) {
                    var circleOptions = {
                        strokeColor: '#FF0000',
                        strokeOpacity: 0.6,
                        strokeWeight: 2,
                        fillColor: '#FF0000',
                        fillOpacity: 0.35,
                        map: $scope.map
                    };
                    $scope.coverageArea = new google.maps.Circle(circleOptions);
                }

                $scope.coverageArea.setRadius(Number($scope.customer.coverage));
                $scope.coverageArea.setCenter($scope.location);
            })


        };

        $scope.save = function () {
            if ($scope.customer.id != null) {
                Customer.update($scope.customer,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Customer.save($scope.customer,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Customer.get({id: id}, function(result) {
                $scope.customer = result;
                $('#deleteCustomerConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Customer.delete({id: id},
                function () {
                    $scope.reset();
                    $('#deleteCustomerConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.reset();
            $('#saveCustomerModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.customer = {name: null, physicalAddress: null, geoLocation: null, coverage: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
