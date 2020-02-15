(function PageController(){
    scriptLoader.loadScript("/js/index/login_controller.js");
    scriptLoader.loadScript("/js/index/registration_controller.js");

    $(document).ready(function(){
        init();
    });
    
    function init(){
        eventProcessor.processEvent(new Event(events.LOAD_LOCALIZATION, "index"));
    }
})();