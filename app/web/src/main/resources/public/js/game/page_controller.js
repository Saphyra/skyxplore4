(function PageController(){
    scriptLoader.loadScript("/js/game/localizations.js");
    scriptLoader.loadScript("/js/common/animation/spinner.js");
    scriptLoader.loadScript("/js/game/window_controller.js");
    scriptLoader.loadScript("/js/game/new_round_controller.js");
    scriptLoader.loadScript("/js/game/map/map_controller.js");
    scriptLoader.loadScript("/js/game/star/star_controller.js");
    scriptLoader.loadScript("/js/game/edit_surface/edit_surface_controller.js");
    scriptLoader.loadScript("/js/game/population_overview/population_overview_controller.js");
    scriptLoader.loadScript("/js/game/storage_settings/storage_settings_controller.js");

    events.REFRESH_WINDOWS = "refresh-windows";

    const windows = [];

    window.pageController = new function(){
        this.openWindow = openWindow;
        this.removeFromList = removeFromList;
    }

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.REFRESH_WINDOWS},
        function(event){
            const windowTypes = event.getPayload();
            new Stream(windows)
                .filter(function(window){return shouldRefresh(window, windowTypes)})
                .forEach(function(window){window.refresh()});

            function shouldRefresh(window, windowTypes){
                if(windowTypes == null || windowTypes.length == 0){
                    return true;
                }

                if(windowTypes == WindowType.ALL || (windowTypes.indexOf(WindowType.ALL) > -1 && windowTypes.length == 1)){
                    return true;
                }

                return windowTypes.indexOf(window.getType()) > -1;
            }
        }
    ));

    function openWindow(windowController){
        windowController.create();
        windows.push(windowController);
    }

    function removeFromList(windowId){
        windows.splice(getIndex(windowId, 1));

        function getIndex(windowId){
            for(let index in windows){
                if(windows[index].getId() == windowId){
                    return index;
                }
            }

            throwException("IllegalArgument", "Window not found with id " + windowId);
        }
    }

    $(document).ready(function(){
        eventProcessor.processEvent(new Event(events.LOAD_LOCALIZATION, "game"));
        return; //TODO restore when map is migrated
        openWindow(mapController.createMapController());
        document.addEventListener('contextmenu', event => event.preventDefault());
    });
})();

const PLAYER_ID = function(){
    const response = dao.sendRequest(HttpMethod.GET, Mapping.GET_PLAYER_ID);
    return response.body;
}();