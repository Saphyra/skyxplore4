(function PopulationOverviewController(){
    scriptLoader.loadScript("/js/game/population_overview/functions/create/population_overview_create_function.js");
    scriptLoader.loadScript("/js/game/population_overview/functions/refresh/population_overview_refresh_function.js");
    scriptLoader.loadScript("/js/game/population_overview/functions/close/population_overview_close_function.js");

    scriptLoader.loadScript("/js/game/population_overview/functions/population_overview_citizen_display_service.js");

    events.OPEN_POPULATION_OVERVIEW = "open_population_overview";
    events.POPULATION_OVERVIEW_FILTER_CHANGED = "POPULATION_OVERVIEW_FILTER_CHANGED";

    window.populationOverviewController = new function(){
        this.filters = {};
        this.orderRules = {};
        this.citizenMap = {};
    }

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.OPEN_POPULATION_OVERVIEW},
        function(event){
            const starId = event.getPayload();
            const containerId = createContainerId(starId);
            const contentId = createContentId(starId);

            const controller = new WindowController(WindowType.POPULATION_OVERVIEW);
                controller.create = populationOverviewCreateFunction.createFunction(starId, controller, containerId, contentId);
                controller.refresh = window.populationOverviewRefreshFunction.refreshFunction(starId, contentId);
                controller.close = window.populationOverviewCloseFunction.closeFunction(starId, controller.getId(), containerId);
            pageController.openWindow(controller);
        }
    ));

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.POPULATION_OVERVIEW_FILTER_CHANGED},
        function(event){
            const starId = event.getPayload();
            window.populationOverviewCitizenDisplayService.displayCitizens(starId, window.populationOverviewController.citizenMap[starId], createContentId(starId));
        }
    ));

    function createContainerId(starId){
        return "population-overview-controller-" + starId;
    }

    function createContentId(starId){
        return "population-overview-content-" + starId;
    }
})();