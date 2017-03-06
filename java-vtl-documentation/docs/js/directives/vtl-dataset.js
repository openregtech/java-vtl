/*global define*/
'use strict';

define(['angular'], function (angular) {
    var moduleName = 'vtl.dataset';
    angular.module(moduleName, [])
        .directive('vtlDataset', function () {
            return {
                restrict: 'E',
                require:"^vtlExample",
                //bindToController: true,
                scope: {
                    name: '@',
                    // TODO: src: '&',
                },
                template:'<!-- directive: ng-transclude -->',
                transclude: true,
                link: function(scope, element, attrs, vtlController, transcludeFn) {
                    // Check URL or content or dataset.
                    // If url, use vtl controller
                    var text = transcludeFn().text();
                    if (text && text.length != 0) {
                        vtlController.addInput(createDataset(text, scope.name));
                    }
                }
            };

            function createDataset(text, name) {
                text = text.replace(/^[\r\n]+|\.|[\r\n]+$/g, ' ').trim(); // Remove tailing line bracks
                var lines = text.split(/\r\n|\n/); // Split
                var line, cells, cell, dataset;
                if (lines < 1)
                    throw new Error("no header found");
                line = lines.splice(0, 1)[0];

                dataset = {
                    name: name, // TODO: Use dataset attribute name.
                    structure: [], // {name: "Folketallet11", role: "MEASURE", type: "java.lang.Number", classType: "java.lang.Number"}
                    data: []
                };

                cells = line.split('],');

                if (cells.length < 1)
                    throw new Error("invalid header format");

                var columnRegex = /([^,\[]+)\[(I|M|A),(String|Number)]?/;
                for (var len = cells.length, i = 0; i < len; ++i) {
                    cell = columnRegex.exec(cells[i].trim());
                    if (cell.length != 4)
                        throw new Error("invalid header format");

                    var name = cell[1];
                    var role;
                    if (cell[2] == "I")
                        role = "IDENTIFIER";
                    if (cell[2] == "M")
                        role = "MEASURE";
                    if (cell[2] == "A")
                        role = "ATTRIBUTE";

                    var type = "java.lang." + cell[3];

                    dataset.structure.push({
                        name: name,
                        role: role,
                        type: type
                    });
                }

                for (var len = lines.length, i = 0; i < len; ++i) {
                    cells = lines[i].trim().split(',');
                    if (cells.length != dataset.structure.length)
                        throw new Error("row size inconsistent with header");

                    // TODO: Convert?
                    dataset.data.push(cells);
                    //for (var len = dataset.structure.length, j = 0; j<len; ++i) {
                    //}
                }

                return dataset;
            }
        });
    return moduleName;
});