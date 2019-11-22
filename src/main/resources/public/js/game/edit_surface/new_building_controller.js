(function NewBuildingController(){
    const buildingDescriptionLocalization = localizations.buildingDescriptionLocalization;
    const buildingLocalization = localizations.buildingLocalization;
    const resourceLocalization = localizations.resourceLocalization;

    window.newBuildingController = new function(){
        this.loadNewBuildingPossibilities = loadNewBuildingPossibilities;
    }

    function loadNewBuildingPossibilities(surfaceId, surfaceType, container, windowController){
        const request = new Request(HttpMethod.GET, Mapping.concat(Mapping.GET_BUILDABLE_BUILDINGS, surfaceId));
            request.convertResponse = function(response){
                return new Stream(JSON.parse(response.body))
                    .sorted(function(a, b){return buildingLocalization.get(a.dataId).localeCompare(buildingLocalization.get(b.dataId))})
                    .toList();
            }
            request.processValidResponse = function(buildings){
                container.innerHTML = "";
                new Stream(buildings)
                    .map(function(building){return createBuildingItem(surfaceId, building, windowController)})
                    .forEach(function(item){container.appendChild(item)});
            }
        dao.sendRequestAsync(request);

        function createBuildingItem(surfaceId, building, windowController){
            const container = document.createElement("div");
                container.classList.add("new-building-item")
                const name = document.createElement("h3");
                    name.innerHTML = buildingLocalization.get(building.dataId);
            container.appendChild(name);

                const description = document.createElement("div");
                    description.classList.add("new-building-description");
                    description.innerHTML = buildingDescriptionLocalization.get(building.dataId);
            container.appendChild(description);

                const resourceTable = document.createElement("table");
                    resourceTable.appendChild(createTableHeader());
                    resourceTable.appendChild(createWorkPoints(building.constructionRequirements.workPoints));
            container.appendChild(resourceTable);

                new Stream(entryList(building.constructionRequirements.resources))
                    .sorted(function(a, b){return resourceLocalization.get(a.getKey()).localeCompare(resourceLocalization.get(b.getKey()))})
                    .map(createResource)
                    .forEach(function(item){resourceTable.appendChild(item)});

            container.appendChild(createBuildButton(surfaceId, building.dataId, windowController));
            return container;

            function createTableHeader(){
                const row = document.createElement("TR");
                    const resourceCell = document.createElement("TH");
                        resourceCell.innerHTML = Localization.getAdditionalContent("resource");
                row.appendChild(resourceCell);
                    const valueCell = document.createElement("TH");
                        valueCell.innerHTML = Localization.getAdditionalContent("amount");
                row.appendChild(valueCell);
                return row;
            }

            function createWorkPoints(workPoints){
                const row = document.createElement("tr");
                    const resourceCell = document.createElement("TD");
                        resourceCell.innerHTML = Localization.getAdditionalContent("work-points");
                row.appendChild(resourceCell);
                    const valueCell = document.createElement("TD");
                        valueCell.innerHTML = workPoints;
                row.appendChild(valueCell);
                return row;
            }

            function createResource(entry){
                const row = document.createElement("TR");
                    const resourceCell = document.createElement("TD");
                        resourceCell.innerHTML = resourceLocalization.get(entry.getKey());
                row.appendChild(resourceCell);
                    const valueCell = document.createElement("TD");
                        valueCell.innerHTML = entry.getValue();
                row.appendChild(valueCell);
                return row;
            }
        }

        function createBuildButton(surfaceId, dataId, windowController){
            const button = document.createElement("div");
                button.classList.add("build-new-building-button");
                button.classList.add("button");
                button.innerHTML = Localization.getAdditionalContent("build-building-button");
                button.onclick = function(){
                    build(surfaceId, dataId, windowController);
                }
            return button;
        }
    }

    function build(surfaceId, dataId, windowController){
        const request = new Request(HttpMethod.POST, Mapping.concat(Mapping.BUILD_BUILDING, surfaceId), {value: dataId});
            request.processValidResponse = function(){
                notificationService.showSuccess(Localization.getAdditionalContent("building-started"));
                eventProcessor.processEvent(new Event(events.REFRESH_WINDOWS, [WindowType.STAR]));
                windowController.close();
            }
        dao.sendRequestAsync(request);
    }
})();