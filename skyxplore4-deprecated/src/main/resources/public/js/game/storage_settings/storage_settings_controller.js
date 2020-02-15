(function StorageSettingsController(){
    scriptLoader.loadScript("/js/game/storage_settings/functions/create/storage_settings_create_function.js");
    scriptLoader.loadScript("/js/game/storage_settings/functions/refresh/storage_settings_refresh_function.js");
    scriptLoader.loadScript("/js/game/storage_settings/functions/close/storage_settings_close_function.js");

    events.OPEN_STORAGE_SETTINGS = "open_storage_settings";

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.OPEN_STORAGE_SETTINGS},
        function(event){
            const starId = event.getPayload();
            const ids = {
                containerId: createContainerId(starId),
                createSettingContainerId: createCreateSettingContainerId(starId),
                settingsListContainerId: createSettingsListContainerId(starId)
            }

            const controller = new WindowController(WindowType.STORAGE_SETTINGS);
                controller.create = window.storageSettingsCreateFunction.createFunction(controller, starId, ids);
                controller.refresh = window.storageSettingsRefreshFunction.refreshFunction(starId, ids);
                controller.close = window.storageSettingsCloseFunction.closeFunction(controller.getId(), ids.containerId);
            pageController.openWindow(controller);
        }
    ));

    function createContainerId(starId){
        return "storage-settings-container-" + starId;
    }

    function createCreateSettingContainerId(starId){
        return "storage-settings-create-container-" + starId;
    }

    function createSettingsListContainerId(starId){
        return "storage-settings-list-container-" + starId;
    }
})();