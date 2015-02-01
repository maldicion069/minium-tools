'use strict';

pupinoIDE
    .config(function($stateProvider, $httpProvider, $translateProvider, modalStateProvider, $urlRouterProvider) {

        $stateProvider
            .state('global.editorarea', {
                url: "/editor/*path",
                templateUrl: "pupino-ide/views/editor.area/index.html",
                controller: "EditorAreaController"
            })
            .state('global.multi', {
                url: "/editor-multi/*path",
                templateUrl: "pupino-ide/views/editor.area/index.html",
                controller: "EditorAreaMultiTabController"
            })


        modalStateProvider
            .state('global.multi.newFile', {
                templateUrl: "pupino-ide/views/files/new-file.html",
                controller: "NewFileController"
            });

        modalStateProvider
            .state('global.multi.open', {
                templateUrl: "pupino-ide/views/files/open.file.html",
                controller: "OpenFileController"
            });

        modalStateProvider
            .state('global.editorarea.results', {
                templateUrl: "pupino-ide/views/editor.area/modal/launch.html",
                controller: "LaunchController"
            })

        //configuration to execute the file
        modalStateProvider.state('global.multi.configs', {
            templateUrl: "pupino-ide/views/editor.area/modal/configs.html",
            controller: "ConfigsController"
        });

        //register backend
        modalStateProvider.state('global.editorarea.registerBackend', {
            templateUrl: "pupino-ide/views/editor.area/modal/register-backend.html",
            controller: "BackendsController"
        });

        //register backend
        modalStateProvider.state('global.multi.help', {
            templateUrl: "pupino-ide/views/editor.area/modal/help.html"
        });

         //register backend
        modalStateProvider.state('global.multi.preferences', {
            templateUrl: 'pupino-ide/views/preferences/preferences.html',
            controller: 'PreferencesController'
        });
    });
