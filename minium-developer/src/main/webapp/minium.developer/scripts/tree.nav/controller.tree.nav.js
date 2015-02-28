'use strict';

angular.module('minium.developer')
    .controller('TreeNavController', function( $scope, $state, FS, GENERAL_CONFIG) {

        /**
         *   Tree view  controller
         */

        //data for the tree
        $scope.dataForTheTree = [];
        //current file selected
        $scope.fs = {
            current: {}
        };

        var firstLoad = true;
        //////////////////////////////////////////////////////////////////
        //
        // Load the files from the file system
        //
        //////////////////////////////////////////////////////////////////
        var asyncLoad = function(node) {

            var params = {
                path: node.relativeUri || ""
            };

            node.children = FS.list(params, function() {
                //sort the child by name
                node.children.sort(predicatBy("name"))
                _.each(node.children, function(item) {
                    // tree navigation needs a label property
                    item.label = item.name;
                    if (firstLoad) {
                        $scope.dataForTheTree.push(item);
                    }
                });
                firstLoad = false;
            });
        };

        //////////////////////////////////////////////////////////////////
        //
        // Load childrens of an item
        //
        //////////////////////////////////////////////////////////////////
        $scope.loadChildren = function(item) {
            // if (item.childrenLoaded) return;
            asyncLoad(item);
            // item.childrenLoaded = true;
        };



        $scope.expandedNodes = [];
        //console.log($scope.expandedNodes);
        $scope.showSelected = function(node) {

            $scope.selectedNode = node;
            //console.log($scope.selectedNode)
            if (node.type == "FILE") {
                $scope.loadFile($scope.selectedNode.relativeUri);
                
                $state.go("global.editorarea.sub", {
                    path: $scope.selectedNode.relativeUri
                }, {
                    location: 'replace', //  update url and replace
                    inherit: false,
                    notify: false
                });
            } else { //if the is on click on a file
                $scope.loadChildren(node);
                //expand the node
                $scope.expandedNodes.push(node)

            }

        };

        $scope.showToggle = function(node, expanded) {
            //console.log(node.children)
            $scope.loadChildren(node);
        };

        /**
         * NAVIGATION FOLDERS Functions
         */
        $scope.opts = {
            injectClasses: {
                ul: "a1",
                li: "a2",
                liSelected: "a7",
                iExpanded: "a3",
                iCollapsed: "a4",
                iLeaf: "a5",
                isFeature: "a2",
                label: "a6",
                labelSelected: "a8"
            },
            isLeaf: function(node) {
                if (node.type === "DIR")
                    return false;
                else
                    return true;
            },
            isFeature: function(node) {
                if (node.type === "DIR")
                    return false;
                else
                    return true;
            },
            dirSelectable: false
        };

        $scope.collapseAll = function() {
            $scope.expandedNodes = [];
        };

        function predicatBy(prop) {
            return function(a, b) {
                if (a[prop] > b[prop]) {
                    return 1;
                } else if (a[prop] < b[prop]) {
                    return -1;
                }
                return 0;
            }
        }

        //////////////////////////////////////////////////////////////////
        //
        // Initialize functions
        //
        //////////////////////////////////////////////////////////////////
        asyncLoad($scope.fs.current);

    });