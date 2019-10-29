(function StarDetailsController(){
    window.starDetailsController = new function(){
        this.showStarDetails = function(starId, container){
            const request = new Request(HttpMethod.GET, Mapping.concat(Mapping.GET_STAR_DETAILS, starId));
                request.convertResponse = function(response){
                    return JSON.parse(response.body);
                }
                request.processValidResponse = function(starData){
                    logService.log(starData);
                }
            dao.sendRequestAsync(request);
        }
    }
})();