(function EditSurfaceController(){
    scriptLoader.loadScript("/js/game/edit_surface/new_building_controller.js");
    scriptLoader.loadScript("/js/game/edit_surface/terraform_surface_controller.js");

    events.SHOW_EDIT_SURFACE_WINDOW = "show_edit_surface_window";

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.SHOW_EDIT_SURFACE_WINDOW},
        function(event){
            const controller = new WindowController(WindowType.EDIT_SURFACE);
                controller.create = createFunction(event.getPayload(), controller);
                controller.refresh = refreshFunction(event.getPayload(), controller);
                controller.close = closeFunction(event.getPayload(), controller.getId());
            pageController.openWindow(controller);
        }
    ));

    function createFunction(surfaceId, controller){
        return function(){
            const container = createContainer(surfaceId);

            document.getElementById("pages").appendChild(container);
            this.refresh();
        };

        function createContainer(surfaceId){
            const container = document.createElement("div");
                container.id = createEditSurfaceContainerId(surfaceId);
                container.classList.add("page");
                container.classList.add("edit-surface-container");

                container.appendChild(createHeader(surfaceId, controller));

                const contentContainer = document.createElement("div");
                    contentContainer.id = createEditSurfaceContentContainerId(surfaceId);
                    contentContainer.classList.add("edit-surface-content-controller");
                    contentContainer.classList.add("content-container");

                    contentContainer.appendChild(createTerraformBar(surfaceId));
                    contentContainer.appendChild(createBuildBar(surfaceId));
            container.appendChild(contentContainer);
            return container;

            function createHeader(surfaceId, controller){
                const header = document.createElement("DIV");
                    header.classList.add("page-header");

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

            function createTerraformBar(surfaceId){
                const bar = document.createElement("div");
                    bar.classList.add("bar");
                    bar.classList.add("left-bar");

                    const title = document.createElement("DIV");
                        title.classList.add("bar-title");
                        title.innerHTML = Localization.getAdditionalContent("terraform-surface-title");
                bar.appendChild(title);

                    const contentContainer = document.createElement("div");
                        contentContainer.classList.add("bar-content");
                        contentContainer.id = createTerraformContainerId(surfaceId);
                bar.appendChild(contentContainer);
                return bar;
            }

            function createBuildBar(surfaceId){
                const bar = document.createElement("div");
                    bar.classList.add("bar");
                    bar.classList.add("right-bar");

                    const title = document.createElement("DIV");
                        title.classList.add("bar-title");
                        title.innerHTML = Localization.getAdditionalContent("build-new-building");
                bar.appendChild(title);

                    const contentContainer = document.createElement("div");
                        contentContainer.classList.add("bar-content");
                        contentContainer.id = createBuildContainerId(surfaceId);
                bar.appendChild(contentContainer);
                return bar;
            }
        }
    }

    function refreshFunction(surfaceId, controller){
        return function(){
            const request = new Request(HttpMethod.GET, Mapping.concat(Mapping.GET_SURFACE_DETAILS, surfaceId));
                request.convertResponse = function(response){
                    return JSON.parse(response.body);
                }
                request.processValidResponse = function(surfaceDetails){
                    document.getElementById(createEditSurfaceTitleId(surfaceId)).innerHTML = Localization.getAdditionalContent("edit-surface-title") + " - " + localizations.surfaceTypeLocalization.get(surfaceDetails.surfaceType);
                    terraformSurfaceController.loadTerraformingPossibilities(surfaceId, surfaceDetails.surfaceType, document.getElementById(createTerraformContainerId(surfaceId)), controller);
                    newBuildingController.loadNewBuildingPossibilities(surfaceId, surfaceDetails.surfaceType, document.getElementById(createBuildContainerId(surfaceId)));
                }
            dao.sendRequestAsync(request);
        }
    }

    function closeFunction(surfaceId, controllerId){
        return function(){
            document.getElementById("pages").removeChild(document.getElementById(createEditSurfaceContainerId(surfaceId)));
            pageController.removeFromList(controllerId);
        }
    }

    function createEditSurfaceContainerId(surfaceId){
        return "edit-surface-container-" + surfaceId;
    }

    function createEditSurfaceContentContainerId(surfaceId){
        return "edit-surface-content-container-" + surfaceId;
    }

    function createEditSurfaceTitleId(surfaceId){
        return "edit-surface-title-" + surfaceId;
    }

    function createTerraformContainerId(surfaceId){
        return "terraform-container-" + surfaceId;
    }

    function createBuildContainerId(surfaceId){
        return "build-container-" + surfaceId;
    }
})();