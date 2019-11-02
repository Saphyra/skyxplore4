(function Localizations(){
    scriptLoader.loadScript("/js/common/localization/custom_localization.js");

    window.localizations = new function(){
        this.buildingLocalization = new CustomLocalization("building");
        this.resourceLocalization = new CustomLocalization("resource");
        this.storageTypeLocalization = new CustomLocalization("storage_type");
        this.surfaceTypeLocalization = new CustomLocalization("surface_type");
    }
})();