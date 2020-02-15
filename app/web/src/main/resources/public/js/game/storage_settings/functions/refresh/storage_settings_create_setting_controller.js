(function StorageSettingsCreateSettingController(){
    const resourceLocalization = localizations.resourceLocalization;

    window.storageSettingsCreateSettingController = new function(){
        this.refresh = refresh;
    }

    function refresh(starId, containerId){
        const request = new Request(HttpMethod.GET, Mapping.concat(Mapping.GET_STORAGE_SETTINGS_CREATION_DETAILS, starId));
            request.convertResponse = function(response){
                return new MapStream(JSON.parse(response.body))
                    .sorted(function(entry1, entry2){return resourceLocalization.get(entry1.getKey()).localeCompare(resourceLocalization.get(entry2.getKey()))})
                    .toMap();
            }
            request.processValidResponse = function(availableSettings){
                const container = document.getElementById(containerId);
                    container.innerHTML = "";
                    if(!Object.keys(availableSettings).length){
                        container.innerHTML = Localization.getAdditionalContent("no-storage-settings");
                        return;
                    }

                    const resourceSelectLabel = document.createElement("LABEL");
                        resourceSelectLabel.appendChild(document.createTextNode(Localization.getAdditionalContent("resource") +": "));

                        const resourceSelect = document.createElement("SELECT");
                            new MapStream(availableSettings)
                                .toListStream(function(key, value){return key})
                                .map(function(resourceId){return createOption(resourceId)})
                                .forEach(function(option){resourceSelect.appendChild(option)});
                    resourceSelectLabel.appendChild(resourceSelect);
                container.appendChild(resourceSelectLabel);

                    const amountLabel = document.createElement("LABEL");
                        amountLabel.appendChild(document.createTextNode(Localization.getAdditionalContent("amount") +": "));

                        const amountInput = document.createElement("INPUT");
                            amountInput.type = "number";
                            amountInput.min = 1;
                            amountInput.max = availableSettings[resourceSelect.value];
                            amountInput.value = 1;
                    amountLabel.appendChild(amountInput);
                container.appendChild(amountLabel);

                        const batchSizeLabel = document.createElement("LABEL");
                            batchSizeLabel.appendChild(document.createTextNode(Localization.getAdditionalContent("batch-size") +": "));

                            const batchSizeInput = document.createElement("INPUT");
                                batchSizeInput.type = "number";
                                batchSizeInput.min = 1;
                                batchSizeInput.value = 10;
                        batchSizeLabel.appendChild(batchSizeInput);
                    container.appendChild(batchSizeLabel);

                    const priorityLabel = document.createElement("LABEL");
                        priorityLabel.appendChild(document.createTextNode(Localization.getAdditionalContent("priority") + ": "));

                        const priorityInput = document.createElement("INPUT");
                            priorityInput.type = "range";
                            priorityInput.min = 1;
                            priorityInput.max = 10;
                            priorityInput.step = 1;
                            priorityInput.value = 5;
                    priorityLabel.appendChild(priorityInput);

                        const prioritySpan = document.createElement("SPAN");
                            prioritySpan.innerHTML = priorityInput.value;
                    priorityLabel.appendChild(prioritySpan);
                container.appendChild(priorityLabel);

                    const createButton = document.createElement("BUTTON");
                        createButton.innerHTML = Localization.getAdditionalContent("create-storage-setting");
                container.appendChild(createButton);
                    resourceSelectLabel.onchange = function(){
                        amountInput.max = availableSettings[resourceSelect.value];
                    }

                    priorityInput.onchange = function(){
                        prioritySpan.innerHTML = priorityInput.value;
                    }

                    createButton.onclick = function(){
                        createSetting(resourceSelect.value, amountInput.value, priorityInput.value, batchSizeInput.value, starId, availableSettings);
                    }

                function createOption(resourceId){
                    const option = document.createElement("OPTION");
                        option.value = resourceId;
                        option.innerHTML = resourceLocalization.get(resourceId)
                    return option;
                }
            }
        dao.sendRequestAsync(request);
    }

    function createSetting(resourceId, amount, priority, batchSize, starId, availableSettings){
        if(amount <= 0){
            notificationService.showError(Localization.getAdditionalContent("invalid-value"));
            return;
        }

        if(batchSize <= 0){
            notificationService.showError(Localization.getAdditionalContent("invalid-value"));
            return;
        }

        if(availableSettings[resourceId] < amount){
            notificationService.showError(Localization.getAdditionalContent("not-enough-storage"));
            return;
        }

        const requestBody = {
            dataId: resourceId,
            targetAmount: amount,
            batchSize: batchSize,
            priority: priority
        }

        const request = new Request(HttpMethod.PUT, Mapping.concat(Mapping.CREATE_STORAGE_SETTING, starId), requestBody);
            request.processValidResponse = function(){
                notificationService.showSuccess(Localization.getAdditionalContent("storage-setting-created"));
                eventProcessor.processEvent(new Event(events.REFRESH_WINDOWS, [WindowType.MAP, WindowType.STORAGE_SETTINGS, WindowType.STAR]));
            }
        dao.sendRequestAsync(request);
    }
})();