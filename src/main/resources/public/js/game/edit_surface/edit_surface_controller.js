(function EditSurfaceController(){
    events.SHOW_EDIT_SURFACE_WINDOW = "show_edit_surface_window";

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.SHOW_EDIT_SURFACE_WINDOW},
        function(event){
            const controller = new WindowController();
                controller.create = createFunction(event.getPayload(), controller);
                controller.refresh = refreshFunction(event.getPayload());
                controller.close = closeFunction(event.getPayload());
            pageController.openWindow(controller);
        }
    ));

    function createFunction(surfaceId, controller){
        return function(){
            //TODO implement
        };
    }

    function refreshFunction(surfaceId){
        return function(){
            //TODO implement
        }
    }

    function closeFunction(surfaceId){
        return function(){
            //TODO implement
        }
    }
})();