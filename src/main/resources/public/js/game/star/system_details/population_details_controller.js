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

            const content = document.createElement("div");
                //TODO add indicator bar
                content.classList.add("population-details-content");
                content.innerHTML = populationDetails.citizenNum + " / " + populationDetails.dwellingSpaceAmount;
        container.appendChild(content);

            const citizenOverviewButton = document.createElement("div");
                citizenOverviewButton.classList.add("button");
                citizenOverviewButton.innerHTML = Localization.getAdditionalContent("citizen-overview");
        container.appendChild(citizenOverviewButton);
        return container;
    }
})();