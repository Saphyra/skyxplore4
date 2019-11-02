(function SurfaceBuildingDetailsController(){
    const buildingLocalization = localizations.buildingLocalization;
    const surfaceTypeLocalization = localizations.surfaceTypeLocalization;

    window.surfaceBuildingDetailsController = new function(){
        this.createSurfaceBuildingDetails = createSurfaceBuildingDetails;
    }

    function createSurfaceBuildingDetails(surfaceOverview){
        const container = document.createElement("div");
            container.classList.add("bar-container");

            const header = document.createElement("div");
                header.classList.add("bar-container-header");
                header.innerHTML = Localization.getAdditionalContent("building-summary");
        container.appendChild(header);

            new Stream(surfaceOverview)
                .sorted(function(a, b){return surfaceTypeLocalization.get(a.surfaceType).localeCompare(surfaceTypeLocalization.get(b.surfaceType))})
                .map(createSurfaceBuildingSummary)
                .forEach(function(item){container.appendChild(item)});

        return container;

        function createSurfaceBuildingSummary(surfaceOverview){
            const surfaceTypeContainer = document.createElement("div");
                surfaceTypeContainer.classList.add("bar-list-item");

                const buildingSummaryContainer = createDetailedBuildingList(surfaceOverview.buildingSummary)
                surfaceTypeContainer.appendChild(createSurfaceOverview(surfaceOverview, buildingSummaryContainer));
                surfaceTypeContainer.appendChild(buildingSummaryContainer);
            return surfaceTypeContainer;

            function createDetailedBuildingList(buildingSummary){
                const buildingSummaryContainer = document.createElement("table");
                    buildingSummaryContainer.classList.add("building-summary-table");

                    const tableHead = document.createElement("TR");
                        const nameHeader = document.createElement("TH");
                            nameHeader.innerHTML = Localization.getAdditionalContent("building");
                    tableHead.appendChild(nameHeader);
                        const levelHeader = document.createElement("TH");
                            levelHeader.innerHTML = Localization.getAdditionalContent("level-summary");
                    tableHead.appendChild(levelHeader);
                        const usedSlotsHeader = document.createElement("TH");
                            usedSlotsHeader.innerHTML = Localization.getAdditionalContent("used-slots");
                    tableHead.appendChild(usedSlotsHeader);
                buildingSummaryContainer.appendChild(tableHead);

                    new Stream(buildingSummary)
                        .sorted(function(a, b){return buildingLocalization.get(a.dataId).localeCompare(buildingLocalization.get(b.dataId))})
                        .map(createBuildingSummary)
                        .forEach(function(item){buildingSummaryContainer.appendChild(item)});
                return buildingSummaryContainer;

                function createBuildingSummary(buildingDetails){
                    const r = document.createElement("TR");
                        const nameCell = document.createElement("TD");
                            nameCell.innerHTML = buildingLocalization.get(buildingDetails.dataId);
                    r.appendChild(nameCell);

                        const levelCell = document.createElement("TD");
                            levelCell.innerHTML = buildingDetails.levelSum;
                    r.appendChild(levelCell);

                        const usedSlotsCell = document.createElement("TD");
                            usedSlotsCell.innerHTML = buildingDetails.usedSlots;
                    r.appendChild(usedSlotsCell);
                    return r;
                }
            }

            function createSurfaceOverview(surfaceOverview, buildingSummaryContainer){
                const surfaceOverviewContainer = document.createElement("div");
                    surfaceOverviewContainer.innerHTML = surfaceTypeLocalization.get(surfaceOverview.surfaceType) + ": " + surfaceOverview.usedSlots + " / " + surfaceOverview.slots;

                    const extendButton = document.createElement("button");
                        extendButton.innerHTML = "+";
                        extendButton.classList.add("close-button");
                        extendButton.onclick = function(){
                            $(buildingSummaryContainer).toggle();
                        }
                surfaceOverviewContainer.appendChild(extendButton);
                return surfaceOverviewContainer;
            }
        }
    }
})();