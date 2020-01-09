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
    }
})();