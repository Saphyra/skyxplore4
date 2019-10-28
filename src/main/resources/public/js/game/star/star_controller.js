(function StarController(){
    events.SHOW_STAR = "show_star";

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.SHOW_STAR},
        function(event){
            const controller = new WindowController();
                controller.create = createFunction(event.getPayload(), controller);
                controller.refresh = refreshFunction(event.getPayload());
                controller.close = closeFunction(event.getPayload());
            pageController.openWindow(controller);
        }
    ));

    function createFunction(starId, controller){
        return function(){
            const container = document.createElement("DIV");
                container.classList.add("page");
                container.classList.add("star-view-container");
                container.id = createContainerId(starId);

                const header = document.createElement("DIV");
                    header.classList.add("star-view-header");

                    const starName = document.createElement("h2");
                        starName.id = createStarNameId(starId);
                header.appendChild(starName);

                    const closeButton = document.createElement("BUTTON");
                        closeButton.classList.add("close-button");
                        closeButton.innerHTML = "X";
                        closeButton.onclick = function(){
                            controller.close();
                        }
                header.appendChild(closeButton);

            container.appendChild(header);

                const surfaceTableContainer = document.createElement("DIV");
                    surfaceTableContainer.classList.add("surface-table-container");
                    surfaceTableContainer.id = "surface-table-container-" + starId;
                    const surfaceTable = document.createElement("DIV");
                        surfaceTable.id = createSurfaceTableId(starId);
                        surfaceTable.classList.add("surface-table");
                surfaceTableContainer.appendChild(surfaceTable);
            container.appendChild(surfaceTableContainer);

            document.getElementById("pages").appendChild(container);
            addRightClickMove(surfaceTable.id, surfaceTableContainer.id, false);
            this.refresh();
        };
    }

    function refreshFunction(starId){
        return function(){
            const surfaceRequest = new Request(HttpMethod.GET, Mapping.concat(Mapping.GET_SURFACES_OF_STAR, starId));
                surfaceRequest.convertResponse = function(response){
                    return new Stream(JSON.parse(response.body))
                        .toMap(
                            function(surface){return surface.surfaceId},
                            function(surface){return surface}
                        );
                }
                surfaceRequest.processValidResponse = function(surfaces){
                    displaySurfaces(starId, surfaces);
                }
            dao.sendRequestAsync(surfaceRequest);

            const starDetailsRequest = new Request(HttpMethod.GET, Mapping.concat(Mapping.GET_STAR, starId));
                starDetailsRequest.convertResponse = function(response){
                    return JSON.parse(response.body);
                }
                starDetailsRequest.processValidResponse = function(star){
                    document.getElementById(createStarNameId(star.starId)).innerHTML = star.starName;
                }
            dao.sendRequestAsync(starDetailsRequest);

            function displaySurfaces(starId, surfaces){
                const coordinateMapping = createCoordinateMapping(surfaces);

                const surfaceTable = document.getElementById(createSurfaceTableId(starId));

                for(let xIndex in coordinateMapping){
                    const row = document.createElement("DIV");
                        row.classList.add("surface-table-row");
                    const x = coordinateMapping[xIndex];
                    for(let yIndex in x){
                        const surfaceId = x[yIndex];
                        const surface = surfaces[surfaceId];
                        const cell = document.createElement("span");
                            cell.classList.add(getSurfaceTypeClass(surface.surfaceType));
                            cell.classList.add("surface-table-cell");
                            if(surface.building){
                                const content = document.createElement("DIV");
                                    content.classList.add(createBuildingIdClass(surface.building.dataId));
                                    content.classList.add("surface-content");
                                    content.innerHTML = surface.building.dataId;
                                cell.appendChild(content);
                            }
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
                        case "VOLCANO":
                            return "surface-type-vulcan";
                        default:
                            logService.log("no backgroundColor found for surfaceType " + surfaceType);
                            return "white";
                    };
                }

                function createBuildingIdClass(dataId){
                    return "building-" + dataId;
                }
            }
        }
    }

    function closeFunction(starId){
        return function(){
            document.getElementById("pages").removeChild(document.getElementById(createContainerId(starId)));
        }
    }

    function createContainerId(starId){
        return "star-view-container-" + starId;
    }

    function createStarNameId(starId){
        return "star-name-" + starId;
    }

    function createSurfaceTableId(starId){
        return "surface-table-" + starId;
    }
})();