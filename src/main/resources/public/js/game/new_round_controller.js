(function NewRoundController(){
    events.NEW_ROUND = "new_round";

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.NEW_ROUND},
        function(){
            spinner.open();
            const request = new Request(HttpMethod.POST, Mapping.NEW_ROUND);
                request.processValidResponse = function(){
                    spinner.close();
                    eventProcessor.processEvent(new Event(events.REFRESH_WINDOWS, WindowType.ALL));
                }
            dao.sendRequestAsync(request);
        }
    ));
})();