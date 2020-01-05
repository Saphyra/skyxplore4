(function SystemDetailsController(){
    scriptLoader.loadScript("/js/game/star/system_details/storage_details_controller.js");
    scriptLoader.loadScript("/js/game/star/system_details/population_details_controller.js");
    scriptLoader.loadScript("/js/game/star/system_details/surface_building_details_controller.js");

    window.systemDetailsController = new function(){
        this.showSystemDetails = function(starId, containerId){
            const request = new Request(HttpMethod.GET, Mapping.concat(Mapping.GET_SYSTEM_DETAILS, starId));
                request.convertResponse = function(response){
                    return JSON.parse(response.body);
                }
                request.processValidResponse = function(systemDetails){
                    displayStarDetails(starId, systemDetails, containerId);
                    spinner.increment();
                }
            dao.sendRequestAsync(request);
        }
    }

    function displayStarDetails(starId, systemDetails, containerId){
        const container = document.getElementById(containerId);
            container.innerHTML = "";
            container.appendChild(storageDetailsController.createStorageDetails(systemDetails.storage));
            container.appendChild(populationDetailsController.createPopulationDetails(starId, systemDetails.population));
            container.appendChild(surfaceBuildingDetailsController.createSurfaceBuildingDetails(systemDetails.surfaceBuildings));
    }
})();