(function NewBuildingController(){
    const buildingLocalization = localizations.buildingLocalization;

    window.newBuildingController = new function(){
        this.loadNewBuildingPossibilities = loadNewBuildingPossibilities;
    }

    function loadNewBuildingPossibilities(surfaceId, surfaceType, container){
        const request = new Request(HttpMethod.GET, Mapping.concat(Mapping.GET_BUILDABLE_BUILDINGS, surfaceId));
            request.convertResponse = function(response){
                return new Stream(JSON.parse(response.body))
                    .sorted(function(a, b){return buildingLocalization.get(a.dataId).localeCompare(buildingLocalization.get(b.dataId))})
                    .toList();
            }
            request.processValidResponse = function(buildings){
                new Stream(buildings)
                    .map(function(building){return createBuildingItem(building)})
                    .forEach(function(item){container.appendChild(item)});
            }
        dao.sendRequestAsync(request);

        function createBuildingItem(building){
            const container = document.createElement("div");
                container.innerHTML = buildingLocalization.get(building.dataId);
            return container;
        }
    }
})();