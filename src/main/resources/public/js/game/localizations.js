(function Localizations(){
    scriptLoader.loadScript("/js/common/localization/custom_localization.js");

    window.localizations = new function(){
        this.buildingDescriptionLocalization = new CustomLocalization("building_description");
        this.buildingLocalization = new CustomLocalization("building");
        this.priorityTypeLocalization = new CustomLocalization("priority_type");
        this.researchLocalization = new CustomLocalization("research");
        this.resourceLocalization = new CustomLocalization("resource");
        this.skillTypeLocalization = new CustomLocalization("skill_type");
        this.storageTypeLocalization = new CustomLocalization("storage_type");
        this.surfaceTypeLocalization = new CustomLocalization("surface_type");
        this.queueTypeLocalization = new CustomLocalization("queue_type");
    }
})();