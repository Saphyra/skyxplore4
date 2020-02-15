(function PopulationOverviewRefreshFunction(){
    scriptLoader.loadScript("/js/game/population_overview/functions/population_overview_citizen_display_service.js");

    window.populationOverviewRefreshFunction = new function(){
        this.refreshFunction = function(starId, contentId){
            return function(){
                spinner.open();
                const request = new Request(HttpMethod.GET, Mapping.concat(Mapping.GET_POPULATION_OVERVIEW, starId));
                    request.convertResponse = function(response){
                        return JSON.parse(response.body);
                    }
                    request.processValidResponse = function(citizens){
                        window.populationOverviewController.citizenMap[starId] = citizens;
                        window.populationOverviewCitizenDisplayService.displayCitizens(starId, citizens, contentId);
                        spinner.increment();
                    }
                dao.sendRequestAsync(request);
            }
        }
    }
})();