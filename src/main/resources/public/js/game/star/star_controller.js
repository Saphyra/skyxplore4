(function StarController(){
    events.SHOW_STAR = "show_star";

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.SHOW_STAR},
        function(event){
            const controller = new WindowController();
                controller.create = createFunction(event.getPayload());
                controller.refresh = refreshFunction(event.getPayload());
            pageController.openWindow(controller);
        }
    ));

    function createFunction(starId){
        return function(){
            const container = document.createElement("DIV");
                container.classList.add("page");
                container.classList.add("star-view-container");
                container.id = createContainerId(starId);
            document.getElementById("pages").appendChild(container);
            this.refresh();
        };
    }

    function refreshFunction(starId){
        return function(){
            const request = new Request(HttpMethod.GET, Mapping.concat(Mapping.GET_SURFACES_OF_STAR, starId));
                request.convertResponse = function(response){
                    return JSON.parse(response.body);
                }
                request.processValidResponse = function(surfaces){
                    displaySurfaces(surfaces);
                }
            dao.sendRequestAsync(request);
        }

        function displaySurfaces(surfaces){
            //TODO implement
        }
    }

    function createContainerId(starId){
        return "star-view-container-" + starId;
    }
})();