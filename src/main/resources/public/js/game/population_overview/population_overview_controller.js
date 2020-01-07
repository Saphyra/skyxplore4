(function PopulationOverviewController(){
    events.OPEN_POPULATION_OVERVIEW = "open_population_overview";
    events.POPULATION_OVERVIEW_FILTER_CHANGED = "POPULATION_OVERVIEW_FILTER_CHANGED";

    const skillTypeLocalization = localizations.skillTypeLocalization;

    const filters = {};
    const citizenMap = {};

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

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.POPULATION_OVERVIEW_FILTER_CHANGED},
        function(event){
            const starId = event.getPayload();
            displayCitizens(starId, citizenMap[starId]);
        }
    ));

    function createFunction(starId, controller){
        return function(){
            const container = createContainer(starId);
                container.appendChild(createHeader(controller));
                container.appendChild(createBody(starId));
                container.appendChild(createFilters(starId));
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

        function createFilters(starId){
            const filterContainer = document.createElement("div");
                filterContainer.classList.add("population-overview-filter-container");

                filterContainer.appendChild(createSkillHideFilter(starId));
            return filterContainer;

            function createSkillHideFilter(starId){
                const filterListContainer = document.createElement("div");
                    filterListContainer.classList.add("population-overview-filter-list-container");

                    const labelButton = document.createElement("div");
                        labelButton.classList.add("button")
                        labelButton.classList.add("population-overview-filter-label")
                        labelButton.innerHTML = Localization.getAdditionalContent("hide-and-show-skills");
                filterListContainer.appendChild(labelButton);

                    const skillListContainer = document.createElement("div");
                        skillListContainer.classList.add("population-overview-filter-list-item-container");

                        skillListContainer.appendChild(createSelectButton(starId, "select-all", true));
                        skillListContainer.appendChild(createSelectButton(starId, "deselect-all", false));

                        new Stream(skillTypeLocalization.getKeys())
                            .sorted(function(a, b){return skillTypeLocalization.get(a).localeCompare(skillTypeLocalization.get(b))})
                            .toMapStream(
                                function(skillType){return skillType},
                                function(skillType){return createInputField(starId)}
                            )
                            .applyOnAllValues(function(map){
                                filters[starId] = map;
                            })
                            .map(function(skillType, inputField){return createFilterElement(skillTypeLocalization.get(skillType), inputField)})
                            .toListStream()
                            .forEach(function(filterElement){skillListContainer.appendChild(filterElement)});

                filterListContainer.appendChild(skillListContainer);

                labelButton.onclick = function(){
                    $(skillListContainer).toggle();
                }
                return filterListContainer;

                function createSelectButton(starId, additionalContent, inputFieldValue){
                    const filterElement = createFilterElement(Localization.getAdditionalContent(additionalContent));
                        filterElement.onclick = function(){
                            new MapStream(filters[starId])
                                .toListStream()
                                .forEach(function(inputField){inputField.checked = inputFieldValue});

                            eventProcessor.processEvent(new Event(events.POPULATION_OVERVIEW_FILTER_CHANGED, starId));
                        }
                    return filterElement;
                }

                function createInputField(starId){
                    const inputField = document.createElement("input");
                        inputField.type = "checkbox";
                        inputField.checked = true;

                        inputField.onchange = function(){
                            eventProcessor.processEvent(new Event(events.POPULATION_OVERVIEW_FILTER_CHANGED, starId));
                        }
                    return inputField;
                }

                function createFilterElement(labelText, inputField){
                    const filterElement = document.createElement("label");
                        if(inputField){
                            filterElement.appendChild(inputField);
                        }
                        const label = document.createElement("span");
                            label.innerHTML = labelText;
                    filterElement.appendChild(label);
                    return filterElement;
                }
            }
        }
    }

    function refreshFunction(starId){
        return function(){
            spinner.open();
            const request = new Request(HttpMethod.GET, Mapping.concat(Mapping.GET_POPULATION_OVERVIEW, starId));
                request.convertResponse = function(response){
                    return new Stream(JSON.parse(response.body))
                        .sorted(function(a, b){return a.name.localeCompare(b.name)})
                        .toList();
                }
                request.processValidResponse = function(citizens){
                    citizenMap[starId] = citizens;
                    displayCitizens(starId, citizens);
                    spinner.increment();
                }

            dao.sendRequestAsync(request);
        }
    }

    function closeFunction(starId, controllerId){
        return function(){
            document.getElementById("pages").removeChild(document.getElementById(createContainerId(starId)));
            pageController.removeFromList(controllerId);
            delete filters[starId];
            delete citizenMap[starId];
        }
    }

    function displayCitizens(starId, citizens){
        const container = document.getElementById(createContentId(starId));
            container.innerHTML = "";

        new Stream(citizens)
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
                return new MapStream(filters[starId])
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