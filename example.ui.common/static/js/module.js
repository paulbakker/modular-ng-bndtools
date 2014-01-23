define(['angular', 'lodash'], function(ng, _) {
  var module = ng.module('common.app', []);
  
  module.directive("menubar", function ($http) {
    return {
        restrict: "E",
        templateUrl: "common/partials/menu.html",
        link: function(scope, element, attrs) {
        	console.log("test");
        	$http.get('/ngmodules').success(function(data) {
        		scope.modules = _.filter(data, function(module) {
        			return module.linkName != null; 
        		});
        	});
        }
    }
});
  
  return module;
});