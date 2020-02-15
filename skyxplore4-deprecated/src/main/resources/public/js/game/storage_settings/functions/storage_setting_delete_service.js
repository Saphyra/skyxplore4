(function StorageSettingDeleteService(){
    window.storageSettingDeleteService = new function(){
        this.deleteSetting = deleteSetting;
    }

    function deleteSetting(storageSettingId){
        if(!confirm(Localization.getAdditionalContent("confirm-delete-storage-setting"))){
            return;
        };

        const request = new Request(HttpMethod.DELETE, Mapping.concat(Mapping.DELETE_STORAGE_SETTING, storageSettingId));
            request.processValidResponse = function(){
                notificationService.showSuccess(Localization.getAdditionalContent("storage-setting-deleted"));
                eventProcessor.processEvent(new Event(events.REFRESH_WINDOWS, [WindowType.MAP, WindowType.STORAGE_SETTINGS, WindowType.STAR]));
            }
        dao.sendRequestAsync(request);
    }
})();