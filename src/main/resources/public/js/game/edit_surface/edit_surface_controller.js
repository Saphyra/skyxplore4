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
            const container = createContainer(surfaceId);
                container.appendChild(createHeader(surfaceId, controller));

            document.getElementById("pages").appendChild(container);
            this.refresh();
        };

        function createContainer(surfaceId){
            const container = document.createElement("div");
                container.id = createEditSurfaceContainerId(surfaceId);
                container.classList.add("page");
                container.classList.add("edit-surface-container");
            return container;
        }

        function createHeader(surfaceId, controller){
            const header = document.createElement("DIV");
                header.classList.add("star-view-header");

                const title = document.createElement("h2");
                    title.id = createEditSurfaceTitleId(surfaceId);
            header.appendChild(title);

                const closeButton = document.createElement("BUTTON");
                    closeButton.classList.add("close-button");
                    closeButton.innerHTML = "X";
                    closeButton.onclick = function(){
                        controller.close();
                    }
            header.appendChild(closeButton);
            return header;
        }
    }

    function refreshFunction(surfaceId){
        return function(){
            const request = new Request(HttpMethod.GET, Mapping.concat(Mapping.GET_SURFACE_DETAILS, surfaceId));
                request.convertResponse = function(response){
                    return JSON.parse(response.body);
                }
                request.processValidResponse = function(surfaceDetails){
                    document.getElementById(createEditSurfaceTitleId(surfaceId)).innerHTML = Localization.getAdditionalContent("edit-surface-title") + " - " + localizations.surfaceTypeLocalization.get(surfaceDetails.surfaceType);
                }
            dao.sendRequestAsync(request);
        }
    }

    function closeFunction(surfaceId){
        return function(){
            document.getElementById("pages").removeChild(document.getElementById(createEditSurfaceContainerId(surfaceId)));
        }
    }

    function createEditSurfaceContainerId(surfaceId){
        return "edit-surface-container-" + surfaceId;
    }

    function createEditSurfaceTitleId(surfaceId){
        return "edit-surface-title-" + surfaceId;
    }
})();