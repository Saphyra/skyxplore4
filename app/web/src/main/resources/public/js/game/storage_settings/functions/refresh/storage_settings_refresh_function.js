(function StorageSettingsRefreshFunction(){
    scriptLoader.loadScript("/js/game/storage_settings/functions/refresh/storage_settings_create_setting_controller.js");
    scriptLoader.loadScript("/js/game/storage_settings/functions/refresh/storage_settings_setting_list_controller.js");

    window.storageSettingsRefreshFunction = new function(){
        this.refreshFunction = refreshFunction;
    }

    function refreshFunction(starId, ids){
        return function(){
            storageSettingsCreateSettingController.refresh(starId, ids.createSettingContainerId);
            storageSettingsSettingListController.refresh(starId, ids.settingsListContainerId);
        }
    }
})();