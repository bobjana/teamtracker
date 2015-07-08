'use strict';

angular.module('teamtrackerApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
