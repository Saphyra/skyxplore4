(function StorageDetails(){
    const resourceLocalization = localizations.resourceLocalization;
    const storageTypeLocalization = localizations.storageTypeLocalization;

    window.storageDetailsController = new function(){
        this.createStorageDetails = createStorageDetails;
    }

    function createStorageDetails(storages){
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
                //TODO add indicators
                const summaryContainer = document.createElement("div");
                    summaryContainer.innerHTML = storageTypeLocalization.get(storage.storageType) + ": " + storage.actual + " (" + storage.allocated + ") / " + storage.capacity + " - " + Localization.getAdditionalContent("reserved") + ": " + storage.reserved;

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
    }
})();