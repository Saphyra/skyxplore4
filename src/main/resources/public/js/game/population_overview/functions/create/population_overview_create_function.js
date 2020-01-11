//TODO split
(function PopulationOverviewCreateFunction(){
    scriptLoader.loadScript("/js/game/population_overview/functions/population_overview_order_rule.js");
    scriptLoader.loadScript("/js/game/population_overview/functions/create/population_overview_create_filters_service.js");

    window.populationOverviewCreateFunction = new function(){
        this.createFunction = function(starId, controller, containerId, contentId){
            return function(){
                const container = createContainer(starId, containerId);
                    container.appendChild(createHeader(controller));
                    container.appendChild(createBody(starId, contentId));
                    container.appendChild(populationOverviewCreateFiltersService.createFilters(starId));
                document.getElementById("pages").appendChild(container);
                window.populationOverviewController.orderRules[starId] = new PopulationOverviewOrderRule();
                this.refresh();
            }

            function createContainer(starId, containerId){
                const container = document.createElement("div");
                    container.id = containerId;
                    container.classList.add("page");
                    container.classList.add("population-overview-container");
                return container;
            }

            function createHeader(controller){
                const header = document.createElement("DIV");
                    header.classList.add("page-header");

                    const headerLabel = document.createElement("h2");
                        headerLabel.innerHTML = Localization.getAdditionalContent("population-overview-page-header")
                header.appendChild(headerLabel);

                    const closeButton = document.createElement("BUTTON");
                        closeButton.classList.add("close-button");
                        closeButton.innerHTML = "X";
                        closeButton.onclick = function(){
                            controller.close();
                        }
                header.appendChild(closeButton);
                return header;
            }

            function createBody(starId, contentId){
                const body = document.createElement("div");
                    body.id = contentId;
                    body.classList.add("content-container");
                    body.classList.add("population-overview-content");
                return body;
            }
        }
    }
})();