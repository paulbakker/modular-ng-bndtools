define(['angular', 'angular-route', '/curricula/js/controller.js'], function(ng) {
  return ng.module('curricula.app', ['ngRoute', 'curricula.controller']).config([
    '$routeProvider',
    function($routeProvider) {
      $routeProvider.when('/curriculum', {
        controller: 'CurriculumController',
        templateUrl: 'curricula/view/curricula.html'
      })
    }
  ]);

});