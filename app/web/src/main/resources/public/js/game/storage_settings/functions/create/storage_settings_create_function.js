(function StorageSettingsCreateFunction(){
    window.storageSettingsCreateFunction = new function(){
        this.createFunction = createFunction;
    }

    function createFunction(controller, starId, ids){
        return function(){
            const container = createContainer(ids.containerId);
                container.appendChild(createHeader(controller));
                container.appendChild(createCreateSettingContainer(ids.createSettingContainerId));
                container.appendChild(createSettingsListContainer(ids.settingsListContainerId));
            document.getElementById("pages").appendChild(container);
            this.refresh();
        }

        function createContainer(containerId){
            const container = document.createElement("div");
                container.id = containerId;
                container.classList.add("page");
            return container;
        }

        function createHeader(controller){
            const header = document.createElement("DIV");
                header.classList.add("page-header");

                const headerLabel = document.createElement("h2");
                    headerLabel.innerHTML = Localization.getAdditionalContent("storage-settings-page-header");
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

        function createCreateSettingContainer(createSettingContainerId){
            const createSettingContainer = document.createElement("div");
                createSettingContainer.classList.add("storage-settings-create-setting-container");
                createSettingContainer.id = createSettingContainerId;
            return createSettingContainer;
        }

        function createSettingsListContainer(settingsListContainerId){
            const settingsListContainer = document.createElement("div");
                settingsListContainer.classList.add("storage-settings-settings--list-container");
                settingsListContainer.id = settingsListContainerId;
            return settingsListContainer;
        }
1    }
})();