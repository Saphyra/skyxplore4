(function PopulationDetailsController(){
    window.populationDetailsController = new function(){
        this.createPopulationDetails = createPopulationDetails;
    }

    function createPopulationDetails(populationDetails){
        const container = document.createElement("div");
            container.classList.add("bar-container");

            const header = document.createElement("div");
                header.classList.add("bar-container-header");
                header.innerHTML = Localization.getAdditionalContent("population");
        container.appendChild(header);

            const barContainer = document.createElement("div");
                barContainer.classList.add("population-details-bar-container");

                const width = populationDetails.citizenNum / populationDetails.dwellingSpaceAmount * 100;
                const bar = document.createElement("div");
                    bar.classList.add("progress-bar");
                    bar.style.width = width + "%";
                    if(width == 100){
                        bar.style.background = "red";
                    }else if(width > 80){
                        bar.style.background = "orange";
                    }
            barContainer.appendChild(bar);

                const content = document.createElement("div");
                    content.classList.add("population-details-content");
                    content.classList.add("progress-bar-text");
                    content.innerHTML = populationDetails.citizenNum + " / " + populationDetails.dwellingSpaceAmount;
            barContainer.appendChild(content);
        container.appendChild(barContainer);

            const citizenOverviewButton = document.createElement("div");
                citizenOverviewButton.classList.add("button");
                citizenOverviewButton.innerHTML = Localization.getAdditionalContent("citizen-overview");
                citizenOverviewButton.onclick = function(){
                    //todo implement
                }
        container.appendChild(citizenOverviewButton);
        return container;
    }
})();