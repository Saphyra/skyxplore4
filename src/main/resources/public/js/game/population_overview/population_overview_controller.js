(function PopulationOverviewController(){
    scriptLoader.loadScript("/js/game/population_overview/functions/create/population_overview_create_function.js");
    scriptLoader.loadScript("/js/game/population_overview/functions/population_overview_order_rule.js");

    events.OPEN_POPULATION_OVERVIEW = "open_population_overview";
    events.POPULATION_OVERVIEW_FILTER_CHANGED = "POPULATION_OVERVIEW_FILTER_CHANGED";

    const skillTypeLocalization = localizations.skillTypeLocalization;

    window.populationOverviewController = new function(){
        this.filters = {};
        this.orderRules = {};
        this.citizenMap = {};
    }

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.OPEN_POPULATION_OVERVIEW},
        function(event){
            const starId = event.getPayload();
            const containerId = createContainerId(starId);
            const contentId = createContentId(starId);

            const controller = new WindowController(WindowType.POPULATION_OVERVIEW);
                controller.create = populationOverviewCreateFunction.createFunction(starId, controller, containerId, contentId);
                controller.refresh = refreshFunction(starId);
                controller.close = closeFunction(starId, controller.getId(), containerId);
            pageController.openWindow(controller);
        }
    ));

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.POPULATION_OVERVIEW_FILTER_CHANGED},
        function(event){
            const starId = event.getPayload();
            displayCitizens(starId, window.populationOverviewController.citizenMap[starId]);
        }
    ));

    function refreshFunction(starId){
        return function(){
            spinner.open();
            const request = new Request(HttpMethod.GET, Mapping.concat(Mapping.GET_POPULATION_OVERVIEW, starId));
                request.convertResponse = function(response){
                    return JSON.parse(response.body);
                }
                request.processValidResponse = function(citizens){
                    window.populationOverviewController.citizenMap[starId] = citizens;
                    displayCitizens(starId, citizens);
                    spinner.increment();
                }

            dao.sendRequestAsync(request);
        }
    }

    function closeFunction(starId, controllerId, containerId){
        return function(){
            document.getElementById("pages").removeChild(document.getElementById(containerId));
            pageController.removeFromList(controllerId);
            delete window.populationOverviewController.filters[starId];
            delete citizenMap[starId];
            delete window.populationOverviewController.orderRules[starId];
        }
    }

    function displayCitizens(starId, citizens){
        const container = document.getElementById(createContentId(starId));
            container.innerHTML = "";

        new Stream(citizens)
            .sorted(window.populationOverviewController.orderRules[starId].getComparator())
            .map(createCitizen)
            .forEach(function(item){container.appendChild(item)});

        function createCitizen(citizen){
            const item = document.createElement("div");
                item.classList.add("population-overview-item");

            item.appendChild(createHeader(citizen));
            item.appendChild(createStats(citizen));

            item.appendChild(createSkills(citizen.skills, getFilters(starId)))
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

            function getFilters(starId){
                return new MapStream(window.populationOverviewController.filters[starId])
                    .map(function(skillType, inputField){return inputField.checked})
                    .toMap();
            }

            function createSkills(skills, filterInputs){
                const skillContainer = document.createElement("div");
                    skillContainer.classList.add("population-overview-item-skill-container");

                    new Stream(skills)
                        .filter(function(skill){return filterInputs[skill.skillType]})
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