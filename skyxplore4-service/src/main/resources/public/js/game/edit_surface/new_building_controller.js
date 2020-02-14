(function NewBuildingController(){
    const buildingDescriptionLocalization = localizations.buildingDescriptionLocalization;
    const buildingLocalization = localizations.buildingLocalization;
    const researchLocalization = localizations.researchLocalization;
    const resourceLocalization = localizations.resourceLocalization;

    window.newBuildingController = new function(){
        this.loadNewBuildingPossibilities = loadNewBuildingPossibilities;
    }

    function loadNewBuildingPossibilities(surfaceId, surfaceType, container, windowController){
        const request = new Request(HttpMethod.GET, Mapping.concat(Mapping.GET_BUILDABLE_BUILDINGS, surfaceId));
            request.convertResponse = function(response){
                return new Stream(JSON.parse(response.body))
                    .sorted(function(a, b){
                        const aHasResearchRequirements = a.constructionRequirements.researchRequirements.length == 0;
                        const bHasResearchRequirements = b.constructionRequirements.researchRequirements.length == 0;
                        return aHasResearchRequirements == bHasResearchRequirements
                            ? buildingLocalization.get(a.dataId).localeCompare(buildingLocalization.get(b.dataId))
                            : aHasResearchRequirements ? -1 : 1
                        }
                    )
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

            container.appendChild(createResourceTable(building));
            let buildButtonDisabled = false;
            if(building.constructionRequirements.researchRequirements.length){
                container.appendChild(createResearchRequirements(building.constructionRequirements.researchRequirements));
                buildButtonDisabled = true;
            }
            container.appendChild(createBuildButton(surfaceId, building.dataId, windowController, buildButtonDisabled));
            return container;
        }

        function createResourceTable(building){
            const resourceTable = document.createElement("table");
                    resourceTable.appendChild(createTableHeader());
                    resourceTable.appendChild(createWorkPoints(building.constructionRequirements.requiredWorkPoints));

                     new Stream(entryList(building.constructionRequirements.requiredResources))
                        .sorted(function(a, b){return resourceLocalization.get(a.getKey()).localeCompare(resourceLocalization.get(b.getKey()))})
                        .map(createResource)
                        .forEach(function(item){resourceTable.appendChild(item)});
            return resourceTable;

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

        function createResearchRequirements(researchRequirements){
            const container = document.createElement("div");
                container.classList.add("missing-research");
                const header = document.createElement("div");
                    header.classList.add("missing-research-header");
                    header.innerHTML = Localization.getAdditionalContent("missing-research");
            container.appendChild(header);

                new Stream(researchRequirements)
                    .map(createResearchItem)
                    .forEach(function(item){container.appendChild(item)});

            return container;

            function createResearchItem(dataId){
                const item = document.createElement("div");
                    item.innerHTML = researchLocalization.get(dataId);
                    item.classList.add("missing-research-item");
                return item;
            }
        }

        function createBuildButton(surfaceId, dataId, windowController, buildButtonDisabled){
            const button = document.createElement("div");
                button.classList.add("build-new-building-button");
                button.classList.add("button");
                button.innerHTML = Localization.getAdditionalContent("build-building-button");
                if(buildButtonDisabled){
                    button.classList.add("disabled");
                }else{
                    button.onclick = function(){
                        build(surfaceId, dataId, windowController);
                    }
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