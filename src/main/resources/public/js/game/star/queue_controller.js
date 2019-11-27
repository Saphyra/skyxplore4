(function QueueController(){
    window.queueController = new function(){
        this.showQueue = function(starId, containerId){
            const request = new Request(HttpMethod.GET, Mapping.concat(Mapping.GET_QUEUE, starId));
                request.convertResponse = function(response){
                    return new Stream(JSON.parse(response.body))
                        .sorted(function(a, b){
                            return b.priority - a.priority || a.addedAt.localeCompare(b.addedAt);
                        })
                        .toList();
                }
                request.processValidResponse = function(queue){
                    logService.log(queue);
                }
            dao.sendRequestAsync(request);
        }
    }
})();