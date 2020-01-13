(function StorageSettingsCreateSettingController(){
    window.storageSettingsCreateSettingController = new function(){
        this.refresh = refresh;
    }

    function refresh(starId, containerId){
        const request = new Request(HttpMethod.GET, Mapping.concat(Mapping.GET_STORAGE_SETTINGS_CREATION_DETAILS, starId));
        //TODO implement
        dao.sendRequestAsync(request);
    }
})();