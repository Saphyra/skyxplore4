(function PageController(){
    scriptLoader.loadScript("/js/game/window_controller.js");
    scriptLoader.loadScript("/js/game/map/map_controller.js");

    const windows = [];

    window.pageController = new function(){
        this.openWindow = openWindow;
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