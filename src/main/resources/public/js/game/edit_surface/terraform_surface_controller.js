(function TerraformSurfaceController(){
    const resourceLocalization = localizations.resourceLocalization;
    const surfaceTypeLocalization = localizations.surfaceTypeLocalization;

    window.terraformSurfaceController = new function(){
        this.loadTerraformingPossibilities = loadTerraformingPossibilities;
    }

    function loadTerraformingPossibilities(surfaceId, surfaceType, container){
        const request = new Request(HttpMethod.GET, Mapping.replace(Mapping.GET_TERRAFORMING_POSSIBILITIES, {surfaceId: surfaceId}));
            request.convertResponse = function(response){
                const possibilities = JSON.parse(response.body);
                    possibilities.sort(function(a, b){return surfaceTypeLocalization.get(a.surfaceType).localeCompare(surfaceTypeLocalization.get(b.surfaceType))});
                return possibilities;
            }
            request.processValidResponse = function(possibilities){
                if(possibilities.length){
                    new Stream(possibilities)
                        .map(function(possibility){return createPossibilityItem(surfaceId, possibility)})
                        .forEach(function(i){container.appendChild(i)});
                }else{
                    container.appendChild(createNoPossibility());
                }
            };
        dao.sendRequestAsync(request);

        function createPossibilityItem(surfaceId, possibility){
            const container = document.createElement("div");
                container.classList.add("bar-list-item");

                container.appendChild(createHeader(possibility.surfaceType));
                container.appendChild(createResourceContainer(possibility.resources));
                container.appendChild(createTerraformButton());
            return container;

            function createHeader(surfaceType){
                const header = document.createElement("div");
                    header.classList.add("centered");
                    header.innerHTML = surfaceTypeLocalization.get(possibility.surfaceType);
                return header;
            }

            function createResourceContainer(resources){
                const resourceContainer = document.createElement("table");
                    resourceContainer.appendChild(createHeaderRow());

                    new Stream(entryList(resources))
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

            function createTerraformButton(){
                const terraformButton = document.createElement("div");
                    terraformButton.classList.add("start-terraforming-button");
                    terraformButton.classList.add("button");
                    terraformButton.innerHTML = Localization.getAdditionalContent("start-terraforming");
                    terraformButton.onclick = function(){
                        //todo implement
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
})();