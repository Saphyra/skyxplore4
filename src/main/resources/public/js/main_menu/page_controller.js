(function PageController(){
    scriptLoader.loadScript("/js/main_menu/create_game_controller.js");
    scriptLoader.loadScript("/js/main_menu/game_display_service.js");

    $(document).ready(function(){
        init();
    });
    
    function init(){
        eventProcessor.processEvent(new Event(events.LOAD_LOCALIZATION, "main_menu"));
    }
})();

function selectGame(gameId){
    window.location.href = "/web/game/" + gameId;
}