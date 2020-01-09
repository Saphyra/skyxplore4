//TODO split
(function PopulationOverviewCreateFunction(){
    scriptLoader.loadScript("/js/game/population_overview/functions/population_overview_order_rule.js");

    const skillTypeLocalization = localizations.skillTypeLocalization;

    window.populationOverviewCreateFunction = new function(){
        this.createFunction = function(starId, controller, containerId, contentId){
            return function(){
                const container = createContainer(starId, containerId);
                    container.appendChild(createHeader(controller));
                    container.appendChild(createBody(starId, contentId));
                    container.appendChild(createFilters(starId));
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

            function createFilters(starId){
                const filterContainer = document.createElement("div");
                    filterContainer.classList.add("population-overview-filter-container");

                    filterContainer.appendChild(createSkillHideFilter(starId));
                    filterContainer.appendChild(createOrderByFilter(starId));
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
                                    window.populationOverviewController.filters[starId] = map;
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
                                new MapStream(window.populationOverviewController.filters[starId])
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
                        const filterElement = document.createElement(inputField && inputField.type == "checkbox" ? "label" : "div");
                            filterElement.classList.add("population-overview-filter-list-item");
                            if(inputField){
                                filterElement.appendChild(inputField);
                            }
                            const label = document.createElement("span");
                                label.innerHTML = labelText;
                        filterElement.appendChild(label);
                        return filterElement;
                    }
                }

                function createOrderByFilter(starId){
                    const filterListContainer = document.createElement("div");
                        filterListContainer.classList.add("population-overview-filter-list-container");

                        const labelButton = document.createElement("div");
                            labelButton.classList.add("button")
                            labelButton.classList.add("population-overview-filter-label")
                            labelButton.innerHTML = Localization.getAdditionalContent("order-by");
                    filterListContainer.appendChild(labelButton);

                        const listContainer = document.createElement("div");
                            listContainer.classList.add("population-overview-filter-list-item-container");

                            listContainer.appendChild(createOrderByNameAscFilter(starId));
                            listContainer.appendChild(createOrderByNameDescFilter(starId));
                            listContainer.appendChild(createOrderBySkillFilter(starId));

                    filterListContainer.appendChild(listContainer);

                    labelButton.onclick = function(){
                        $(listContainer).toggle();
                    }

                    return filterListContainer;

                    function createOrderByNameAscFilter(starId){
                        const button = document.createElement("div");
                            button.classList.add("population-overview-filter-list-item");
                            button.innerHTML = Localization.getAdditionalContent("order-by-name-asc");

                            button.onclick = function(){
                                const rule = window.populationOverviewController.orderRules[starId];
                                    rule.setRuleType(PopulationOverviewOrderRuleType.NAME);
                                    rule.setByNameOrderMethod(PopulationOverviewOrderMethod.ASC);
                                eventProcessor.processEvent(new Event(events.POPULATION_OVERVIEW_FILTER_CHANGED, starId));
                            }
                        return button;
                    }

                    function createOrderByNameDescFilter(starId){
                        const button = document.createElement("div");
                            button.classList.add("population-overview-filter-list-item");
                            button.innerHTML = Localization.getAdditionalContent("order-by-name-desc");

                            button.onclick = function(){
                                const rule = window.populationOverviewController.orderRules[starId];
                                    rule.setRuleType(PopulationOverviewOrderRuleType.NAME);
                                    rule.setByNameOrderMethod(PopulationOverviewOrderMethod.DESC);
                                eventProcessor.processEvent(new Event(events.POPULATION_OVERVIEW_FILTER_CHANGED, starId));
                            }
                        return button;
                    }

                    function createOrderBySkillFilter(starId){
                        const filterContainer = document.createElement("div");
                            filterContainer.classList.add("centered");

                            const label = document.createElement("div");
                                label.classList.add("button");
                                label.innerHTML = Localization.getAdditionalContent("order-by-skill");
                                label.onclick = updateRule;
                        filterContainer.appendChild(label);

                        const skillSelectMenu = createSkillSelectMenu(starId);
                            skillSelectMenu.onchange = updateRule;
                        const ascOrDesc = createAscOrDesc(starId);
                            ascOrDesc.ascInput.onchange = updateRule;
                            ascOrDesc.descInput.onchange = updateRule;

                        filterContainer.appendChild(skillSelectMenu);
                        filterContainer.appendChild(ascOrDesc.container);

                        function updateRule(){
                            const rule = window.populationOverviewController.orderRules[starId];
                                rule.setRuleType(PopulationOverviewOrderRuleType.SKILL);
                                rule.setBySkillOrderMethod(ascOrDesc.ascInput.checked ? PopulationOverviewOrderMethod.ASC : PopulationOverviewOrderMethod.DESC);
                                rule.setSelectedSkillType(skillSelectMenu.value);
                            eventProcessor.processEvent(new Event(events.POPULATION_OVERVIEW_FILTER_CHANGED, starId));
                        }

                        return filterContainer;

                        function createSkillSelectMenu(starId){
                            const selectMenu = document.createElement("select");
                                new Stream(skillTypeLocalization.getKeys())
                                    .sorted(function(a, b){return skillTypeLocalization.get(a).localeCompare(skillTypeLocalization.get(b))})
                                    .map(createOption)
                                    .forEach(function(option){selectMenu.appendChild(option)});
                            return selectMenu;

                            function createOption(skillType){
                                const option = document.createElement("option");
                                    option.innerHTML = skillTypeLocalization.get(skillType);
                                    option.value = skillType;
                                return option;
                            }
                        }

                        function createAscOrDesc(starId){
                            const ascDescContainer = document.createElement("div");
                                const ascLabel = document.createElement("label");
                                    const ascRadio = document.createElement("input");
                                        ascRadio.type = "radio";
                                        ascRadio.name = "asc-desc-" + starId;
                                        ascRadio.checked = true;
                                ascLabel.appendChild(ascRadio);
                                ascLabel.appendChild(document.createTextNode(Localization.getAdditionalContent("asc")));
                            ascDescContainer.appendChild(ascLabel);

                                const descLabel = document.createElement("label");
                                    const descRadio = document.createElement("input");
                                        descRadio.type = "radio";
                                        descRadio.name = "asc-desc-" + starId;
                                descLabel.appendChild(descRadio);
                                descLabel.appendChild(document.createTextNode(Localization.getAdditionalContent("desc")));
                            ascDescContainer.appendChild(descLabel);

                            return {
                                ascInput: ascRadio,
                                descInput: descRadio,
                                container: ascDescContainer
                            };
                        }
                    }
                }
            }
        }
    }
})();