'use strict';

angular.module('teamtrackerApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


