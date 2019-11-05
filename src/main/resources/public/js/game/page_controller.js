(function PageController(){
    scriptLoader.loadScript("/js/game/localizations.js");
    scriptLoader.loadScript("/js/game/window_controller.js");
    scriptLoader.loadScript("/js/game/map/map_controller.js");
    scriptLoader.loadScript("/js/game/star/star_controller.js");
    scriptLoader.loadScript("/js/game/edit_surface/edit_surface_controller.js");

    const windows = [];

    window.pageController = new function(){
        this.openWindow = openWindow;
        this.removeFromList = function(page){
        }
    }

    function openWindow(windowController){
        windows.push(windowController.create());
    }

    $(document).ready(function(){
        eventProcessor.processEvent(new Event(events.LOAD_LOCALIZATION, "game"));
        openWindow(mapController.createMapController());
        document.addEventListener('contextmenu', event => event.preventDefault());
    });
})();

const PLAYER_ID = function(){
    const response = dao.sendRequest(HttpMethod.GET, Mapping.GET_PLAYER_ID);
    return response.body;
}();