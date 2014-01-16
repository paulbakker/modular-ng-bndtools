define(['angular', '/curricula/js/module.js', '/profile/js/module.js'], function(ng) {
  'use strict';

  var appModule = ng.module('demo.app', ['ngRoute', 'curricula.app', 'profile.app'], [
    '$locationProvider', '$routeProvider',
    function($locationProvider, $routeProvider) {
      //$locationProvider.html5Mode(true);
      $routeProvider.otherwise({redirectTo: '/curriculum'});
    }
  ]);

  return appModule;
});