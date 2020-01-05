(function PopulationOverviewController(){
    events.OPEN_POPULATION_OVERVIEW = "open_population_overview";

    const skillTypeLocalization = localizations.skillTypeLocalization;

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.OPEN_POPULATION_OVERVIEW},
        function(event){
            const starId = event.getPayload();
            const controller = new WindowController(WindowType.POPULATION_OVERVIEW);
                controller.create = createFunction(starId, controller);
                controller.refresh = refreshFunction(starId);
                controller.close = closeFunction(starId, controller.getId());
            pageController.openWindow(controller);
        }
    ));

    function createFunction(starId, controller){
        return function(){
            const container = createContainer(starId);
                container.appendChild(createHeader(controller));
                container.appendChild(createBody(starId));

            document.getElementById("pages").appendChild(container);
            this.refresh();
        }

        function createContainer(starId){
            const container = document.createElement("div");
                container.id = createContainerId(starId);
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

        function createBody(starId){
            const body = document.createElement("div");
                body.id = createContentId(starId);
                body.classList.add("content-container");
                body.classList.add("population-overview-content");
            return body;
        }
    }

    function refreshFunction(starId){
        return function(){
            const contentContainer = document.getElementById(createContentId(starId));
                contentContainer.innerHTML = "";
            spinner.open();
            const request = new Request(HttpMethod.GET, Mapping.concat(Mapping.GET_POPULATION_OVERVIEW, starId));
                request.convertResponse = function(response){
                    return new Stream(JSON.parse(response.body))
                        .sorted(function(a, b){return a.name.localeCompare(b.name)})
                        .toList();
                }
                request.processValidResponse = function(citizens){
                    displayCitizens(contentContainer, citizens);
                    spinner.increment();
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

    function displayCitizens(container, citizens){
        new Stream(citizens)
            .map(createCitizen)
            .forEach(function(item){container.appendChild(item)});

        function createCitizen(citizen){
            const item = document.createElement("div");
                item.classList.add("population-overview-item");

            item.appendChild(createHeader(citizen));
            item.appendChild(createStats(citizen));

            item.appendChild(createSkills(citizen.skills))
            return item;

            function createHeader(citizen){
                const header = document.createElement("h3");
                    header.classList.add("population-overview-item-header");
                    header.innerHTML = citizen.name;
                return header;
            }

            function createStats(citizen){
                const statContainer = document.createElement("div");
                    statContainer.classList.add("population-overview-item-stat-container");

                    statContainer.appendChild(createStat("morale", citizen.morale));
                    statContainer.appendChild(createStat("satiety", citizen.morale));
                return statContainer;

                function createStat(stat, value){
                    const statItem = document.createElement("div");
                        statItem.classList.add("population-overview-item-stat");

                        const progressBar = document.createElement("div");
                            progressBar.classList.add("progress-bar");
                            progressBar.style.width = value + "%";
                    statItem.appendChild(progressBar);

                        const label = document.createElement("div");
                            label.classList.add("progress-bar-text");
                            label.innerHTML = Localization.getAdditionalContent("population-overview-item-stat-" + stat) + ": " + value;
                    statItem.appendChild(label);
                    return statItem;
                }
            }

            function createSkills(skills){
                const skillContainer = document.createElement("div");
                    skillContainer.classList.add("population-overview-item-skill-container");

                    new Stream(skills)
                        .sorted(function(a, b){return skillTypeLocalization.get(a.skillType).localeCompare(skillTypeLocalization.get(b.skillType))})
                        .map(createSkill)
                        .forEach(function(item){skillContainer.appendChild(item)});

                return skillContainer;

                function createSkill(skill){
                    const skillItem = document.createElement("div");
                        skillItem.classList.add("population-overview-item-skill");

                        const width = skill.experience / skill.nextLevel * 100;
                        const progressBar = document.createElement("div");
                            progressBar.classList.add("progress-bar");
                            progressBar.style.width = width + "%";
                    skillItem.appendChild(progressBar);

                        const label = document.createElement("div");
                            label.classList.add("progress-bar-text");
                            label.innerHTML = skillTypeLocalization.get(skill.skillType) + " - lvl " + skill.level + " (" + Math.round(width) + "%)";
                    skillItem.appendChild(label);
                    return skillItem;
                }
            }
        }
    }

    function createContainerId(starId){
        return "population-overview-controller-" + starId;
    }

    function createContentId(starId){
        return "population-overview-content-" + starId;
    }
})();