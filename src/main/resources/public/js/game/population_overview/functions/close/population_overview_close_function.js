(function PopulationOverviewCloseFunction(){
    window.populationOverviewCloseFunction = new function(){
        this.closeFunction = function(starId, controllerId, containerId){
            return function(){
                document.getElementById("pages").removeChild(document.getElementById(containerId));
                pageController.removeFromList(controllerId);
                delete window.populationOverviewController.filters[starId];
                delete window.populationOverviewController.citizenMap[starId];
                delete window.populationOverviewController.orderRules[starId];
            }
        }
    }
})();