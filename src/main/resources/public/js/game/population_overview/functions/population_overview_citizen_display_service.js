(function PopulationOverviewCitizenDisplayService(){
    const skillTypeLocalization = localizations.skillTypeLocalization;

    window.populationOverviewCitizenDisplayService = new function(){
        this.displayCitizens = function(starId, citizens, contentId){
             const container = document.getElementById(contentId);
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
                    let originalName = citizen.name;

                     const header = document.createElement("h3");
                         header.classList.add("population-overview-item-header");
                         header.innerHTML = originalName;

                         header.onclick = function(){
                            header.contentEditable = true;
                            header.focus();
                         }
                         $(header).on("focusin", function(){selectElementText(header);});
                         $(header).on("focusout", function(){
                            const newName = header.innerHTML;
                            if(originalName != newName){
                                const validationResult = validateCitizenName(newName);
                                if(validationResult.valid){
                                    renameCitizen(citizen.citizenId, newName);
                                    originalName = newName;
                                    citizen.name = newName;
                                }else{
                                    header.innerHTML = originalName;
                                    notificationService.showError(Localization.getAdditionalContent(validationResult.errorCode));
                                }
                            }
                            header.contentEditable = false;
                            clearSelection();
                            header.style.borderColor = null;
                         });
                         header.onkeyup = function(){
                            if(validateCitizenName(header.innerHTML).valid){
                                header.style.borderColor = "green";
                            }else{
                                header.style.borderColor = "red";
                            }
                         }
                     return header;

                     function validateCitizenName(characterName){
                        let valid = true;
                        let errorCode = null;
                        if(characterName.length < 3){
                            valid = false;
                            errorCode = "citizen-name-too-short";
                        }else if(characterName.length > 30){
                            valid = false;
                            errorCode = "citizen-name-too-long";
                        }

                        return {
                            valid: valid,
                            errorCode: errorCode
                        };
                     }
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
    }

    function renameCitizen(citizenId, newName){
        const request = new Request(HttpMethod.POST, Mapping.concat(Mapping.RENAME_CITIZEN, citizenId), {value: newName});
            request.processValidResponse = function(){
                notificationService.showSuccess(Localization.getAdditionalContent("citizen-renamed"));
            }
        dao.sendRequestAsync(request);
    }
})();