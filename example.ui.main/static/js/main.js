'use strict'

require
    .config({
        paths: {
            'angular': 'lib/angular.min',
            'angular-route': 'lib/angular-route.min',
            'domReady': 'lib/require-domready',
            'lodash': 'lib/lodash'
        },

        shim: {
            'angular': {
                exports: 'angular'
            }
        }
    });

require(['bootstrap'], function() {
    // nothing to do here...see app.bootstrap.js
});