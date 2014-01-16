define(['angular', 'angular-route', '/profile/js/controller.js'], function(ng) {
  return ng.module('profile.app', ['ngRoute', 'profile.controller']).config([
    '$routeProvider',
    function($routeProvider) {
      $routeProvider.when('/profile', {
        controller: 'ProfileController',
        templateUrl: 'profile/view/profile.html'
      })
    }
  ]);

});