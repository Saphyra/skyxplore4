function CustomLocalization(fileName){
    const localization = loadLocalization(fileName, function(localization){return localization});

    this.get = function(key){
        return localization[key] || throwException("IllegalArgument", "Localization not found with key " + key + " in file " + fileName);
    }
}