(function PageController(){
    scriptLoader.loadScript("/js/main_menu/create_game_controller.js");

    $(document).ready(function(){
        init();
    });
    
    function init(){
        eventProcessor.processEvent(new Event(events.LOAD_LOCALIZATION, "main_menu"));
    }
})();