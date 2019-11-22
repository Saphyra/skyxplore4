(function SurfaceController(){
    window.surfaceController = new function(){
        this.showSurfaces = function(starId, surfaceTableId){
            const surfaceRequest = new Request(HttpMethod.GET, Mapping.concat(Mapping.GET_SURFACES_OF_STAR, starId));
                surfaceRequest.convertResponse = function(response){
                    return new Stream(JSON.parse(response.body))
                        .toMap(
                            function(surface){return surface.surfaceId},
                            function(surface){return surface}
                        );
                }
                surfaceRequest.processValidResponse = function(surfaces){
                    displaySurfaces(starId, surfaces, document.getElementById(surfaceTableId));
                }
            dao.sendRequestAsync(surfaceRequest);
        }
    }

    function displaySurfaces(starId, surfaces, surfaceTable){
        surfaceTable.innerHTML = "";
        const coordinateMapping = createCoordinateMapping(surfaces);

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
                        cell.appendChild(createBuildingContent(surface.building));
                    }else{
                        cell.appendChild(createBuildButton(surface.surfaceId));
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

        function createBuildingContent(building){
            const content = document.createElement("DIV");
                content.classList.add(createBuildingIdClass(building.dataId));
                content.classList.add("surface-content");

                const levelCell = document.createElement("DIV");
                    levelCell.innerHTML = Localization.getAdditionalContent("level") + ": " + building.level;
                    levelCell.classList.add("building-level");
            content.appendChild(levelCell);

                const footer = document.createElement("DIV");
                    footer.classList.add("surface-footer");
                    footer.appendChild(createFooterContent(building));
            content.appendChild(footer);
            return content;

            function createBuildingIdClass(dataId){
                return "building-" + dataId;
            }

            function createFooterContent(building){
                if(building.construction == null){
                    const upgradeButton = document.createElement("button");
                        upgradeButton.innerHTML = Localization.getAdditionalContent("upgrade");
                        upgradeButton.onclick = function(){
                            //TODO upgrade building
                        }
                    return upgradeButton;
                }else{
                    //TODO display progress bar
                }
            }
        }

        function createBuildButton(surfaceId){
            const footer = document.createElement("div");
                footer.classList.add("surface-footer");

                const buildButton = document.createElement("button");
                    buildButton.classList.add("build-button");
                    buildButton.onclick = function(){
                        eventProcessor.processEvent(new Event(events.SHOW_EDIT_SURFACE_WINDOW, surfaceId));
                    }
            footer.appendChild(buildButton);
            return footer;
        }
    }
})();