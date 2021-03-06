'use strict';

miniumDeveloper.factory('ProjectFactory', function($http) {
    return {
        create: function(project) {
            return $http.post("/app/rest/project/new", project);
        },
        open: function(path) {
            return $http.post("/app/rest/project/import", path);
        },
        isValid: function(path) {
            return $http.post("/app/rest/project/valid", path);
        },
        isValidName: function(path) {
            return $http.post("/app/rest/project/valid/name", path);
        },
        hasProject: function() {
            return $http.get("/app/rest/project/hasProject");
        },
        getProjects: function() {
            return $http.get("/app/rest/project/");
        },
        getProjectName: function() {
            return $http.get("/app/rest/project/name");
        }
    };
});


// this service load and store open tabs from cookies
miniumDeveloper.service('ProjectService', function(ProjectFactory, $window, $location, $cookieStore) {
    //todo put in config.js
    var OPEN_TABS_COOKIES = 'openTabs';
    var LAST_PROJECTS_COOKIES = 'lastProjects';
    var PROJECT_COOKIE = 'project';

    var reload = function(path) {
        $.removeCookie(OPEN_TABS_COOKIES); // remove the tab with the open tabs
        $cookieStore.put(PROJECT_COOKIE, path)
        $window.location.reload();
    };

    var getOpenProjects = function(){
        var projects = $cookieStore.get(LAST_PROJECTS_COOKIES);
        return projects;

    }

    var storeOpenProjects = function(path){
        var projects = getOpenProjects() || [];
        projects.push(path);
        $cookieStore.put(LAST_PROJECTS_COOKIES,_.uniq(projects).slice(-4));
    }

    this.open = function(path) {
        ProjectFactory.open(path).success(function(data) {
            $cookieStore.put(PROJECT_COOKIE, path);
            storeOpenProjects(path);
            reload(path);
        }).error(function(data, status) {
            $cookieStore.remove(PROJECT_COOKIE)
            toastr.error(data)
        });
    };

    this.reload = function(path) {
        reload(path);
    };

    /*
    store the last open projects in a cookie
    */
    this.storeOpenProjects = function(path){
        storeOpenProjects(path);
    }

    /*
    store the last open projects from a cookie
    */
    this.getOpenProjects = function(path){
        return getOpenProjects();
    }

     /*
    store the last open projects from a cookie
    */
    this.clearOpenProjects = function(path){
        $cookieStore.remove(LAST_PROJECTS_COOKIES);
    }

});
