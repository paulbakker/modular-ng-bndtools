// Boot the webshop programmatically (rather than using ng-app on the <html> element)
define(['require', 'angular', 'app'], function(require, ng) {
  'use strict';

  require(['domReady!'], function(document) {
    /* everything is loaded...go! */
    return ng.bootstrap(document, ['demo.app']);
  });
});