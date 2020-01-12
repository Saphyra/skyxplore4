(function StorageSettingsCloseFunction(){
    window.storageSettingsCloseFunction = new function(){
        this.closeFunction = closeFunction;
    }

    function closeFunction(controllerId, containerId){
        return function(){
            document.getElementById("pages").removeChild(document.getElementById(containerId));
            pageController.removeFromList(controllerId);
        }
    }
})();