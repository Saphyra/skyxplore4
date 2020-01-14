(function StorageSettingUpdateService(){
    window.storageSettingUpdateService = new function(){
        this.updateSetting = updateSetting;
    }

    function updateSetting(starId, storageSettingId, amount, priority, maxAmount){
        if(amount > maxAmount){
            notificationService.showError(Localization.getAdditionalContent("not-enough-storage"));
            return;
        }

        const request = new Request(HttpMethod.POST, Mapping.concat(Mapping.UPDATE_STORAGE_SETTING, storageSettingId), {targetAmount: amount, priority: priority})
            request.processValidResponse = function(){
                notificationService.showSuccess(Localization.getAdditionalContent("storage-setting-updated"));
                eventProcessor.processEvent(new Event(events.REFRESH_WINDOWS, [WindowType.MAP, WindowType.STORAGE_SETTINGS, WindowType.STAR]));
            }
        dao.sendRequestAsync(request);
    }
})();