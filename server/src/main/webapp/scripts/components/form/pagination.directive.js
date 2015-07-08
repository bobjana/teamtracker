/* globals $ */
'use strict';

angular.module('teamtrackerApp')
    .directive('teamtrackerAppPagination', function() {
        return {
            templateUrl: 'scripts/components/form/pagination.html'
        };
    });
