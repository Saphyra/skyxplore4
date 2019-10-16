(function StarController(){
    events.SHOW_STAR = "show_star";

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.SHOW_STAR},
        function(event){
            const controller = new WindowController();
                controller.create = createFunction(event.getPayload());
                controller.refresh = refreshFunction(event.getPayload());
            pageController.openWindow(controller);
        }
    ));

    function createFunction(starId){
        return function(){
            const container = document.createElement("DIV");
                container.classList.add("page");
                container.classList.add("star-view-container");
                container.id = createContainerId(starId);

                const surfaceTable = document.createElement("TABLE");
                    surfaceTable.id = createSurfaceTableId(starId);
                    surfaceTable.classList.add("surface-table");
            container.appendChild(surfaceTable);

            document.getElementById("pages").appendChild(container);
            this.refresh();
        };
    }

    function refreshFunction(starId){
        return function(){
            const request = new Request(HttpMethod.GET, Mapping.concat(Mapping.GET_SURFACES_OF_STAR, starId));
                request.convertResponse = function(response){
                    return new Stream(JSON.parse(response.body))
                        .toMap(
                            function(surface){return surface.surfaceId},
                            function(surface){return surface}
                        );
                }
                request.processValidResponse = function(surfaces){
                    displaySurfaces(starId, surfaces);
                }
            dao.sendRequestAsync(request);
        }

        function displaySurfaces(starId, surfaces){
            const coordinateMapping = createCoordinateMapping(surfaces);

            const surfaceTable = document.getElementById(createSurfaceTableId(starId));

            for(let xIndex in coordinateMapping){
                const row = document.createElement("TR");
                const x = coordinateMapping[xIndex];
                for(let yIndex in x){
                    const surfaceId = x[yIndex];
                    const surface = surfaces[surfaceId];
                    const cell = document.createElement("TD");
                        const content = document.createElement("DIV");
                            content.classList.add("surface-content");
                            content.classList.add(getSurfaceTypeClass(surface.surfaceType));
                    cell.appendChild(content);
                    row.appendChild(cell);
                }

                surfaceTable.appendChild(row);
            }

            function createCoordinateMapping(surfaces){
                const result = {};

                for(let surfaceId in surfaces){
                    const coordinate = surfaces[surfaceId].coordinate;
                    if(result[coordinate.x] == undefined){
                        result[coordinate.x] = {};
                    }

                    result[coordinate.x][coordinate.y] = surfaceId;
                }

                return result;
            }

            function getSurfaceTypeClass(surfaceType){
                switch (surfaceType){
                    case "COAL_MINE":
                        return "surface-type-coal";
                    case "CONCRETE":
                        return "surface-type-concrete";
                    case "DESERT":
                        return "surface-type-desert";
                    case "FOREST":
                        return "surface-type-forest";
                    case "LAKE":
                        return "surface-type-lake";
                    case "MOUNTAIN":
                        return "surface-type-mountain";
                    case "OIL_FIELD":
                        return "surface-type-oil-field";
                    case "ORE_MINE":
                        return "surface-type-ore-mine";
                    case "VULCAN":
                        return "surface-type-vulcan";
                    default:
                        logService.log("no backgroundColor found for surfaceType " + surfaceType);
                        return "white";
                };
            }
        }
    }

    function createContainerId(starId){
        return "star-view-container-" + starId;
    }

    function createSurfaceTableId(starId){
        return "surface-table-" + starId;
    }
})();