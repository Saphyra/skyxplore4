(function StarController(){
    scriptLoader.loadScript("/js/game/star/surface_controller.js");
    scriptLoader.loadScript("/js/game/star/system_details_controller.js");
    scriptLoader.loadScript("/js/game/star/queue_controller.js");

    events.SHOW_STAR = "show_star";

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.SHOW_STAR},
        function(event){
            const controller = new WindowController(WindowType.STAR);
                controller.create = createFunction(event.getPayload(), controller);
                controller.refresh = refreshFunction(event.getPayload());
                controller.close = closeFunction(event.getPayload(), controller.getId());
            pageController.openWindow(controller);
        }
    ));

    function createFunction(starId, controller){
        return function(){
            const container = createContainer(starId);
                container.appendChild(createHeader(starId, controller));

                const contentContainer = document.createElement("DIV");
                    contentContainer.classList.add("content-container");
                    contentContainer.classList.add("star-view-content-container");

                    contentContainer.appendChild(createLeftBar(starId));
                    contentContainer.appendChild(createSurfaceTableContainer(starId));
                    contentContainer.appendChild(createRightBar(starId));
            container.appendChild(contentContainer);
            document.getElementById("pages").appendChild(container);
            addRightClickMove(createSurfaceTableId(starId), createSurfaceTableContainerId(starId), false);
            this.refresh();
        };

        function createContainer(starId){
            const container = document.createElement("DIV");
                container.classList.add("page");
                container.classList.add("star-view-container");
                container.id = createContainerId(starId);
            return container;
        }

        function createHeader(starId, controller){
            const header = document.createElement("DIV");
                header.classList.add("page-header");
                header.classList.add("star-view-header");

                const starName = document.createElement("h2");
                    const nameSpan = document.createElement("span");
                        nameSpan.classList.add("star-view-star-name");
                        nameSpan.id = createStarNameId(starId);
                starName.appendChild(nameSpan);
            header.appendChild(starName);

                const closeButton = document.createElement("BUTTON");
                    closeButton.classList.add("close-button");
                    closeButton.innerHTML = "X";
                    closeButton.onclick = function(){
                        controller.close();
                    }
            header.appendChild(closeButton);
            return header;
        }

        function createLeftBar(starId){
            const leftBar = document.createElement("DIV");
                leftBar.classList.add("bar");
                leftBar.classList.add("left-bar");

                const title = document.createElement("DIV");
                    title.classList.add("bar-title");
                    title.innerHTML = Localization.getAdditionalContent("star-details-title");
            leftBar.appendChild(title);

                const contentContainer = document.createElement("DIV");
                    contentContainer.classList.add("bar-content");
                    contentContainer.id = createLeftBarId(starId);
            leftBar.appendChild(contentContainer);
            return leftBar;
        }

        function createRightBar(starId){
                const rightBar = document.createElement("DIV");
                    rightBar.classList.add("bar");
                    rightBar.classList.add("right-bar");
                    rightBar.classList.add("star-view-queue-container")

                    const title = document.createElement("DIV");
                        title.classList.add("bar-title");
                        title.innerHTML = Localization.getAdditionalContent("queue-title");
                rightBar.appendChild(title);

                    const contentContainer = document.createElement("DIV");
                        contentContainer.classList.add("bar-content");
                        contentContainer.id = createRightBarId(starId);
                rightBar.appendChild(contentContainer);
                return rightBar;
            }

        function createSurfaceTableContainer(starId){
            const surfaceTableContainer = document.createElement("DIV");
                surfaceTableContainer.classList.add("surface-table-container");
                surfaceTableContainer.id = createSurfaceTableContainerId(starId);

                const surfaceTable = document.createElement("DIV");
                    surfaceTable.id = createSurfaceTableId(starId);
                    surfaceTable.classList.add("surface-table");
            surfaceTableContainer.appendChild(surfaceTable);
            return surfaceTableContainer;
        }
    }

    function refreshFunction(starId){
        return function(){
            spinner.open(1); //TODO set to 4 when domains are migrated
            loadStarName(starId);
            return; //TODO restore when domains are migrated
            systemDetailsController.showSystemDetails(starId, createLeftBarId(starId));
            surfaceController.showSurfaces(starId, createSurfaceTableId(starId));
            queueController.showQueue(starId, createRightBarId(starId));
        }

        function loadStarName(starId){
            const starDetailsRequest = new Request(HttpMethod.GET, Mapping.concat(Mapping.GET_STAR, starId));
                starDetailsRequest.convertResponse = function(response){
                    return JSON.parse(response.body);
                }
                starDetailsRequest.processValidResponse = function(star){
                    spinner.increment();

                    let originalName = star.starName;
                    const starName = document.getElementById(createStarNameId(star.starId));
                        starName.innerHTML = originalName;

                        starName.onclick = function(){
                            starName.contentEditable = true;
                            starName.focus();
                        }
                        $(starName).on("focusin", function(){selectElementText(starName);});
                        $(starName).on("focusout", function(){
                            const newName = starName.innerHTML;
                            if(originalName != newName){
                                const validationResult = validateStarName(newName);
                                if(validationResult.valid){
                                    renameStar(starId, newName);
                                    originalName = newName;
                                }else{
                                    starName.innerHTML = originalName;
                                    notificationService.showError(Localization.getAdditionalContent(validationResult.errorCode));
                                }
                            }
                            starName.contentEditable = false;
                            clearSelection();
                            starName.style.borderColor = null;
                        })

                        starName.onkeyup = function(){
                            if(validateStarName(starName.innerHTML).valid){
                                starName.style.borderColor = "green";
                            }else{
                                starName.style.borderColor = "red";
                            }
                         }
                }
            dao.sendRequestAsync(starDetailsRequest);
        }

        function validateStarName(starName){
            let valid = true;
            let errorCode = null;
            if(starName.length < 3){
                valid = false;
                errorCode = "star-name-too-short";
            }else if(starName.length > 30){
                valid = false;
                errorCode = "star-name-too-long";
            }

            return {
                valid: valid,
                errorCode: errorCode
            };
        }

        function renameStar(starId, starName){
            const request = new Request(HttpMethod.POST, Mapping.concat(Mapping.RENAME_STAR, starId), {value: starName});
                request.processValidResponse = function(){
                    notificationService.showSuccess(Localization.getAdditionalContent("star-renamed"));
                    eventProcessor.processEvent(new Event(events.REFRESH_WINDOWS, WindowType.MAP));
                }
            dao.sendRequestAsync(request);
        }
    }

    function closeFunction(starId, controllerId){
        return function(){
            document.getElementById("pages").removeChild(document.getElementById(createContainerId(starId)));
            pageController.removeFromList(controllerId);
        }
    }

    function createContainerId(starId){
        return "star-view-container-" + starId;
    }

    function createLeftBarId(starId){
        return "star-view-left-bar-" + starId;
    }

    function createRightBarId(starId){
            return "star-view-right-bar-" + starId;
        }

    function createStarNameId(starId){
        return "star-name-" + starId;
    }

    function createSurfaceTableContainerId(starId){
        return "surface-table-container-" + starId;
    }

    function createSurfaceTableId(starId){
        return "surface-table-" + starId;
    }
})();