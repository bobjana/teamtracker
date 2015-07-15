'use strict';


angular.module('teamtrackerApp')
    .controller('CustomerController', function ($scope, Customer, User, ParseLinks) {
        $scope.customers = [];
        $scope.users = User.query();
        $scope.page = 1;


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
                $('#saveCustomerModal').modal('show');
            });


            $('#saveCustomerModal').on('shown.bs.modal', function (e) {
                initMap();
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

            var pos = "-29.118235, 26.209712";

            if(navigator.geolocation) {
                navigator.geolocation.getCurrentPosition(function(position) {
                    $scope.customer.geoLocation = position.coords.latitude + ", " + position.coords.longitude;
                });
            }


            $scope.customer = {name: null, physicalAddress: null, geoLocation: null, coverage: 100, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();

            $('#saveCustomerModal').on('shown.bs.modal', function (e) {
                initMap();
            })
        };


        $scope.reposition = function(){
            if ($scope.customer.geoLocation == null || $scope.customer.geoLocation.length < 3 || $scope.customer.geoLocation.indexOf(',') == -1){
                return;
            }
            var loc = $scope.customer.geoLocation.split(',');
            var location = new google.maps.LatLng(loc[0], loc[1]);

            $scope.marker.setPosition(location);
            $scope.map.setCenter(location);
            $scope.coverageArea.setCenter(location);
            $scope.coverageArea.setRadius(Number($scope.customer.coverage));
        }

        $scope.upateGeoLocation = function(){
            $scope.customer.geoLocation =  this.getPlace().geometry.location.A + ", " + this.getPlace().geometry.location.F;
            $scope.$apply();
            $scope.reposition();
        }


        function initMarker($scope) {
            if ($scope.marker == null) {
                $scope.marker = new google.maps.Marker({
                    map: $scope.map,
                    draggable: true,
                    animation: google.maps.Animation.DROP,
                });
            }
        }

        function initCoverageArea($scope) {
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
        }


        function initMap() {
            google.maps.event.trigger($scope.map, 'resize');
            initMarker($scope);
            initCoverageArea($scope);

            google.maps.event.addListener($scope.marker, 'drag', function(event) {
                $scope.coverageArea.setRadius(0);
            });

            google.maps.event.addListener($scope.marker, 'dragend', function(event) {
                var location = event.latLng.lat() + ", " + event.latLng.lng();
                $scope.customer.geoLocation = location;
                $scope.$apply();
                $scope.reposition();
            });

            $scope.reposition();
        }

    });
