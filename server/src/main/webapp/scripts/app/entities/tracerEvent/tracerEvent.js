'use strict';

angular.module('teamtrackerApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('tracerEvent', {
                parent: 'entity',
                url: '/tracerEvent',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'teamtrackerApp.tracerEvent.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/tracerEvent/tracerEvents.html',
                        controller: 'TracerEventController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('tracerEvent');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('tracerEventDetail', {
                parent: 'entity',
                url: '/tracerEvent/:id',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'teamtrackerApp.tracerEvent.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/tracerEvent/tracerEvent-detail.html',
                        controller: 'TracerEventDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('tracerEvent');
                        return $translate.refresh();
                    }]
                }
            });
    });
