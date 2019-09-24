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

                    const request = new Request(HttpMethod.GET, Mapping.GET_MAP)
                        request.convertResponse = function(response){
                            return JSON.parse(response.body);
                        }
                        request.processValidResponse = function(map){
                            new Stream(map.connections)
                                .forEach(function(connection){mapElementsContainer.appendChild(createConnectionElement(connection))})
                            new Stream(map.stars)
                                .flatMap(createStarElements)
                                .forEach(function(starElement){mapElementsContainer.appendChild(starElement)});
                        }
                    dao.sendRequestAsync(request);

            container.appendChild(mapElementsContainer);

            document.getElementById("pages").appendChild(container);
        }

        function createConnectionElement(connection){
            const element = createSvgElement("line");
                element.setAttribute("x1", connection.coordinate1.x + 70);
                element.setAttribute("y1", connection.coordinate1.y + 70);
                element.setAttribute("x2", connection.coordinate2.x + 70);
                element.setAttribute("y2", connection.coordinate2.y + 70);
                element.setAttribute("stroke", "white");
                element.setAttribute("stroke-width", 1);
            return element;
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