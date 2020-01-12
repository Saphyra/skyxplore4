(function StorageDetails(){
    const resourceLocalization = localizations.resourceLocalization;
    const storageTypeLocalization = localizations.storageTypeLocalization;

    window.storageDetailsController = new function(){
        this.createStorageDetails = createStorageDetails;
    }

    function createStorageDetails(starId, storages){
        storages.sort(function(a, b){
            return storageTypeLocalization.get(a.storageType).localeCompare(storageTypeLocalization.get(b.storageType));
        });

        const container = document.createElement("div");
            container.classList.add("bar-container");

            const header = document.createElement("DIV");
                header.classList.add("bar-container-header");
                header.innerHTML = Localization.getAdditionalContent("header-storage");
        container.appendChild(header);

            new Stream(storages)
                .map(createStorageItem)
                .forEach(function(storageItem){container.appendChild(storageItem)});
        container.appendChild(createOpenStorageSettingsButton(starId));
        return container;

        function createStorageItem(storage){
            const storageContainer = document.createElement("div");
                storageContainer.classList.add("bar-list-item");
                const detailedListContainer = createDetailedResourceList(storage.resources);
                storageContainer.appendChild(createStorageSummary(storage, detailedListContainer));
                storageContainer.appendChild(detailedListContainer);
            return storageContainer;

            function createDetailedResourceList(resources){
                const detailedListContainer = document.createElement("div");
                    detailedListContainer.classList.add("detailed-resource-list-container");

                    new Stream(resources)
                        .sorted(function(a, b){return resourceLocalization.get(a.dataId).localeCompare(resourceLocalization.get(b.dataId))})
                        .map(function(resource){return createResourceDetails(resource)})
                        .forEach(function(details){detailedListContainer.appendChild(details)});

                return detailedListContainer;

                function createResourceDetails(resource){
                    const resourceDetailContainer = document.createElement("DIV");
                        resourceDetailContainer.classList.add("resource-details-container");
                        resourceDetailContainer.appendChild(createResourceDetailList(resource));
                    return resourceDetailContainer;

                    function createResourceDetailList(resource){
                        const c = document.createElement("div");
                            c.classList.add("resource-detail")
                            c.appendChild(document.createTextNode(resourceLocalization.get(resource.dataId)));

                            const ul = document.createElement("ul");
                                const amount = document.createElement("li");
                                    amount.innerHTML = Localization.getAdditionalContent("amount") + ": " + resource.amount + " (" + resource.allocated + ") / " + getDifference(resource.difference);
                            ul.appendChild(amount);
                                const reserved = document.createElement("li");
                                    reserved.innerHTML = Localization.getAdditionalContent("reserved") + ": " + resource.reserved;
                            ul.appendChild(reserved);
                                const average = document.createElement("li");
                                    average.innerHTML = Localization.getAdditionalContent("average") + ": " + resource.average;
                            ul.appendChild(average)
                        c.appendChild(ul);

                        return c;

                        function getDifference(diff){
                            return diff > 0 ? "+" + diff : diff;
                        }
                    }
                }
            }

            function createStorageSummary(storage, detailedListContainer){
                const actualWidth = (storage.actual - storage.allocated) / storage.capacity * 100;
                const allocatedWidth = storage.allocated / storage.capacity * 100;
                const reservedWidth = storage.reserved / storage.capacity * 100;

                const summaryContainer = document.createElement("div");
                    summaryContainer.classList.add("star-view-storage-summary-container");

                    const actualProgressBar = document.createElement("div");
                        actualProgressBar.classList.add("progress-bar");
                        actualProgressBar.classList.add("actual-progress-bar");
                        actualProgressBar.style.width = actualWidth + "%";
                summaryContainer.appendChild(actualProgressBar);

                    const allocatedProgressBar = document.createElement("div");
                            allocatedProgressBar.classList.add("progress-bar");
                            allocatedProgressBar.classList.add("allocated-progress-bar");
                            allocatedProgressBar.style.left = actualWidth + "%";
                            allocatedProgressBar.style.width = allocatedWidth + "%";
                summaryContainer.appendChild(allocatedProgressBar);

                    const reservedProgressBar = document.createElement("div");
                            reservedProgressBar.classList.add("progress-bar");
                            reservedProgressBar.classList.add("reserved-progress-bar");
                            reservedProgressBar.style.left = (actualWidth + allocatedWidth) + "%";
                            reservedProgressBar.style.width = reservedWidth + "%";
                summaryContainer.appendChild(reservedProgressBar);

                    const summaryText = document.createElement("div");
                        summaryText.classList.add("progress-bar-text");
                        summaryText.innerHTML = storageTypeLocalization.get(storage.storageType) + ": " + storage.actual + " (" + storage.allocated + ") / " + storage.capacity + " - " + Localization.getAdditionalContent("reserved") + ": " + storage.reserved;
                summaryContainer.appendChild(summaryText);

                    const extendButton = document.createElement("button");
                        extendButton.innerHTML = "+";
                        extendButton.classList.add("close-button");
                        extendButton.onclick = function(){
                            $(detailedListContainer).toggle();
                        }
                summaryContainer.appendChild(extendButton);
                return summaryContainer;
            }
        }

        function createOpenStorageSettingsButton(starId){
            const button = document.createElement("div");
                button.classList.add("button");
                button.innerHTML = Localization.getAdditionalContent("open-storage-settings-button");
                button.onclick = function(){
                    eventProcessor.processEvent(new Event(events.OPEN_STORAGE_SETTINGS, starId));
                }
            return button;
        }
    }
})();