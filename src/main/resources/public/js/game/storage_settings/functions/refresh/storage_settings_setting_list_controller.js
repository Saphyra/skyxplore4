(function StorageSettingsSettingListController(){
    scriptLoader.loadScript("/js/game/storage_settings/functions/storage_setting_update_service.js");
    scriptLoader.loadScript("/js/game/storage_settings/functions/storage_setting_delete_service.js");

    const resourceLocalization = localizations.resourceLocalization;

    window.storageSettingsSettingListController = new function(){
        this.refresh = refresh;
    }

    function refresh(starId, containerId){
        const request = new Request(HttpMethod.GET, Mapping.concat(Mapping.GET_STORAGE_SETTINGS, starId));
            request.convertResponse = function(response){
                return new Stream(JSON.parse(response.body))
                    .sorted(function(a, b){return resourceLocalization.get(a.dataId).localeCompare(resourceLocalization.get(b.dataId))})
                    .toList();
            }
            request.processValidResponse = function(storageSettings){
                const container = document.getElementById(containerId);
                    container.innerHTML = "";

                    if(!storageSettings.length){
                        container.innerHTML = Localization.getAdditionalContent("no-storage-settings");
                    }

                    new Stream(storageSettings)
                        .map(function(storageSetting){return createSetting(storageSetting)})
                        .forEach(function(item){container.appendChild(item)});
            }
        dao.sendRequestAsync(request);

        function createSetting(storageSetting){
            const settingContainer = document.createElement("DIV");
                settingContainer.classList.add("storage-setting");

                const settingsHeader = document.createElement("H3");
                    settingsHeader.innerHTML = resourceLocalization.get(storageSetting.dataId);
            settingContainer.appendChild(settingsHeader);

                const detailsContainer = document.createElement("DIV");
                    const amountLabel = document.createElement("LABEL");
                        amountLabel.appendChild(document.createTextNode(Localization.getAdditionalContent("amount") + ": "));

                        const amountInput = document.createElement("INPUT");
                            amountInput.type = "number";
                            amountInput.min = 1;
                            amountInput.max = storageSetting.maxAmount;
                            amountInput.value = storageSetting.targetAmount
                    amountLabel.appendChild(amountInput);
                detailsContainer.appendChild(amountLabel);

                    const batchSizeLabel = document.createElement("LABEL");
                        batchSizeLabel.appendChild(document.createTextNode(Localization.getAdditionalContent("batch-size") + ": "));
                        const batchSizeInput = document.createElement("INPUT");
                            batchSizeInput.type = "number";
                            batchSizeInput.min = 1;
                            batchSizeInput.value = storageSetting.batchSize
                    batchSizeLabel.appendChild(batchSizeInput);
                detailsContainer.appendChild(batchSizeLabel);

                    const priorityLabel = document.createElement("LABEL");
                        priorityLabel.appendChild(document.createTextNode(Localization.getAdditionalContent("priority") + ": "));

                        const priorityInput = document.createElement("INPUT");
                            priorityInput.type = "range";
                            priorityInput.min = 1;
                            priorityInput.max = 10;
                            priorityInput.step = 1;
                            priorityInput.value = storageSetting.priority;
                    priorityLabel.appendChild(priorityInput);

                        const prioritySpan = document.createElement("SPAN");
                            prioritySpan.innerHTML = priorityInput.value;
                    priorityLabel.appendChild(prioritySpan);

                        priorityInput.onchange = function(){
                            prioritySpan.innerHTML = priorityInput.value;
                        }
                detailsContainer.appendChild(priorityLabel);
            settingContainer.appendChild(detailsContainer);

                const buttonContainer = document.createElement("DIV");
                    const updateButton = document.createElement("BUTTON");
                        updateButton.innerHTML = Localization.getAdditionalContent("save");
                        updateButton.onclick = function(){
                            storageSettingUpdateService.updateSetting(starId, storageSetting.storageSettingId, amountInput.value, priorityInput.value, batchSizeInput.value, storageSetting.maxAmount);
                        }
                buttonContainer.appendChild(updateButton);

                    const deleteButton = document.createElement("BUTTON");
                        deleteButton.innerHTML = Localization.getAdditionalContent("delete");
                        deleteButton.onclick = function(){
                            storageSettingDeleteService.deleteSetting(storageSetting.storageSettingId);
                        }
                buttonContainer.appendChild(deleteButton);

            settingContainer.appendChild(buttonContainer);
            return settingContainer;
        }
    }
})();