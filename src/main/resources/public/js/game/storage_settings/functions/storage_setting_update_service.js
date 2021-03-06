(function StorageSettingUpdateService(){
    window.storageSettingUpdateService = new function(){
        this.updateSetting = updateSetting;
    }

    function updateSetting(starId, storageSettingId, amount, priority, batchSize, maxAmount){
        if(amount > maxAmount){
            notificationService.showError(Localization.getAdditionalContent("not-enough-storage"));
            return;
        }

        if(amount <= 0){
            notificationService.showError(Localization.getAdditionalContent("invalid-value"));
            return;
        }

        if(batchSize <= 0){
            notificationService.showError(Localization.getAdditionalContent("invalid-value"));
            return;
        }

        const request = new Request(HttpMethod.POST, Mapping.concat(Mapping.UPDATE_STORAGE_SETTING, storageSettingId), {targetAmount: amount, priority: priority, batchSize: batchSize})
            request.processValidResponse = function(){
                notificationService.showSuccess(Localization.getAdditionalContent("storage-setting-updated"));
                eventProcessor.processEvent(new Event(events.REFRESH_WINDOWS, [WindowType.MAP, WindowType.STORAGE_SETTINGS, WindowType.STAR]));
            }
        dao.sendRequestAsync(request);
    }
})();