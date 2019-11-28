(function TerraformSurfaceController(){
    const resourceLocalization = localizations.resourceLocalization;
    const surfaceTypeLocalization = localizations.surfaceTypeLocalization;

    window.terraformSurfaceController = new function(){
        this.loadTerraformingPossibilities = loadTerraformingPossibilities;
    }

    function loadTerraformingPossibilities(surfaceId, surfaceType, container, windowController){
        const request = new Request(HttpMethod.GET, Mapping.replace(Mapping.GET_TERRAFORMING_POSSIBILITIES, {surfaceId: surfaceId}));
            request.convertResponse = function(response){
                const possibilities = JSON.parse(response.body);
                    possibilities.sort(function(a, b){return surfaceTypeLocalization.get(a.surfaceType).localeCompare(surfaceTypeLocalization.get(b.surfaceType))});
                return possibilities;
            }
            request.processValidResponse = function(possibilities){
                container.innerHTML = "";
                if(possibilities.length){
                    new Stream(possibilities)
                        .map(function(possibility){return createPossibilityItem(surfaceId, possibility, windowController)})
                        .forEach(function(i){container.appendChild(i)});
                }else{
                    container.appendChild(createNoPossibility());
                }
            };
        dao.sendRequestAsync(request);

        function createPossibilityItem(surfaceId, possibility, windowController){
            const container = document.createElement("div");
                container.classList.add("bar-list-item");

                container.appendChild(createHeader(possibility.surfaceType));
                container.appendChild(createResourceContainer(possibility.constructionRequirements));
                container.appendChild(createTerraformButton(surfaceId, possibility.surfaceType, windowController));
            return container;

            function createHeader(surfaceType){
                const header = document.createElement("div");
                    header.classList.add("centered");
                    header.innerHTML = surfaceTypeLocalization.get(possibility.surfaceType);
                return header;
            }

            function createResourceContainer(constructionRequirements){
                const resourceContainer = document.createElement("table");
                    resourceContainer.appendChild(createHeaderRow());
                    resourceContainer.appendChild(createWorkPointsRow(constructionRequirements.requiredWorkPoints));

                    new Stream(entryList(constructionRequirements.requiredResources))
                        .sorted(function(a, b){return resourceLocalization.get(a.getKey()).localeCompare(resourceLocalization.get(b.getKey()))})
                        .map(createResourceItem)
                        .forEach(function(r){resourceContainer.appendChild(r)});
                return resourceContainer;

                function createHeaderRow(){
                    const headerRow = document.createElement("tr");
                            const resourceHeaderCell = document.createElement("th");
                                resourceHeaderCell.innerHTML = Localization.getAdditionalContent("resource");
                        headerRow.appendChild(resourceHeaderCell);
                            const amountHeaderCell = document.createElement("th");
                                amountHeaderCell.innerHTML = Localization.getAdditionalContent("amount");
                        headerRow.appendChild(amountHeaderCell);
                    return headerRow;
                }

                function createWorkPointsRow(workPoints){
                    const row = document.createElement("tr");
                        const nameCell = document.createElement("td");
                            nameCell.innerHTML = Localization.getAdditionalContent("work-points");
                    row.appendChild(nameCell);
                        const valueCell = document.createElement("td");
                            valueCell.innerHTML = workPoints;
                    row.appendChild(valueCell);
                    return row;
                }

                function createResourceItem(resourceEntry){
                    const row = document.createElement("tr");
                        const resourceNameCell = document.createElement("td");
                            resourceNameCell.innerHTML = resourceLocalization.get(resourceEntry.getKey());
                    row.appendChild(resourceNameCell);
                        const resourceAmountCell = document.createElement("td");
                            resourceAmountCell.innerHTML = resourceEntry.getValue();
                    row.appendChild(resourceAmountCell);
                    return row;
                }
            }

            function createTerraformButton(surfaceId, surfaceType, windowController){
                const terraformButton = document.createElement("div");
                    terraformButton.classList.add("start-terraforming-button");
                    terraformButton.classList.add("button");
                    terraformButton.innerHTML = Localization.getAdditionalContent("start-terraforming");
                    terraformButton.onclick = function(){
                        terraform(surfaceId, surfaceType, windowController);
                    }
                return terraformButton;
            }
        }

        function createNoPossibility(){
            const container = document.createElement("div");
                container.classList.add("bar-list-item");
                container.classList.add("centered");
                container.innerHTML = Localization.getAdditionalContent("no-terraform-possibilities");
            return container;
        }
    }

    function terraform(surfaceId, surfaceType, windowController){
        const request = new Request(HttpMethod.POST, Mapping.concat(Mapping.TERRAFORM_SURFACE, surfaceId), {value: surfaceType});
            request.processValidResponse = function(){
                notificationService.showSuccess(Localization.getAdditionalContent("terraforming-started"));
                eventProcessor.processEvent(new Event(events.REFRESH_WINDOWS, [WindowType.STAR]));
                windowController.close();
            }
        dao.sendRequestAsync(request);
    }
})();