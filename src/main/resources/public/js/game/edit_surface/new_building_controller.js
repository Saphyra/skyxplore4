(function NewBuildingController(){
    window.newBuildingController = new function(){
        this.loadNewBuildingPossibilities = loadNewBuildingPossibilities;
    }

    function loadNewBuildingPossibilities(surfaceId, surfaceType, container){
        const request = new Request(HttpMethod.GET, Mapping.concat(Mapping.GET_BUILDABLE_BUILDINGS, surfaceId));

        dao.sendRequestAsync(request);
    }
})();