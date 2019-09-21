(function MapController(){
    window.mapController = new function(){
        this.createMapController = createMapController;
    }

    function createMapController(){
        const controller = new WindowController();
            controller.create = createFunction();
        return controller;
    }

    function createFunction(){
        return function(){
            const container = document.createElement("DIV");
                container.id = "map-container";

                const mapElementsContainer = createSvgElement("svg");
                    mapElementsContainer.id = "map-elements-container";

                    const request = new Request(HttpMethod.GET, Mapping.GET_STARS)
                        request.convertResponse = function(response){
                            return JSON.parse(response.body);
                        }
                        request.processValidResponse = function(stars){
                            new Stream(stars)
                                .flatMap(createStarElements)
                                .forEach(function(starElement){mapElementsContainer.appendChild(starElement)});
                        }
                    dao.sendRequestAsync(request);

            container.appendChild(mapElementsContainer);

            document.getElementById("pages").appendChild(container);
        }

        function createStarElements(star){
            const starElement = createSvgElement("circle");
                starElement.setAttribute("r", 20);
                starElement.setAttribute("cx", star.coordinate.x + 70);
                starElement.setAttribute("cy", star.coordinate.y + 70);
            const starNameElement = createSvgElement("text");
                starNameElement.setAttribute("x", star.coordinate.x + 70);
                starNameElement.setAttribute("y", star.coordinate.y - 30 + 70);
                starNameElement.setAttribute("text-anchor", "middle");
                starNameElement.setAttribute("pointer-events", "none");
                starNameElement.innerHTML = star.starName;
            return new Stream([starElement, starNameElement]);
        }
    }
})();