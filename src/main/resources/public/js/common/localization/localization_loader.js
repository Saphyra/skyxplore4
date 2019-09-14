function loadLocalization(fileName, successCallback){
    const DEFAULT_LOCALE = "hu";

    createQuery(
        fileName,
        getLocale(),
        successCallback,
        createQuery(
            fileName,
            getBrowserLanguage(),
            successCallback,
            createQuery(
                fileName,
                DEFAULT_LOCALE,
                successCallback
            )
        )
    )();

    function createQuery(fileName, locale, successCallback, errorCallback){
        return function(){
            const response = dao.sendRequest(HttpMethod.GET, getPath(locale, fileName));
            if(response.status === ResponseStatus.OK){
                successCallback(JSON.parse(response.body));
            }else if(errorCallback){
                errorCallback();
            }else{
                logService.log(response.toString(), "error", "Error loading localization: ");
            }
        }
    }

    function getPath(locale, fileName){
        return "/i18n/page/" + locale + "/" + fileName + ".json";
    }
}