(function MapController(){
    scriptLoader.loadScript("/js/common/animation/move_controller.js");

    const X_OFFSET = 70;
    const Y_OFFSET = 70;
    const STAR_NAME_OFFSET = 30;
    const STAR_SIZE = 20;

    window.mapController = new function(){
        this.createMapController = createMapController;
    }

    function createMapController(){
        const controller = new WindowController(WindowType.MAP);
            controller.create = createFunction();
            controller.refresh = refreshFunction();
        return controller;
    }

    function createFunction(){
        return function(){
            const container = document.createElement("DIV");
                container.id = "map-container";
                container.classList.add("page");

                const mapElementsContainer = createSvgElement("svg");
                    mapElementsContainer.id = "map-elements-container";
            container.appendChild(mapElementsContainer);

            document.getElementById("pages").appendChild(container);
            addRightClickMove("map-elements-container", "map-container", true)
            this.refresh();
        }
    }

    function refreshFunction(){
        return function(){
            const mapElementsContainer = document.getElementById("map-elements-container");
                mapElementsContainer.innerHTML = "";

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

            function createConnectionElement(connection){
                const element = createSvgElement("line");
                    element.setAttribute("x1", connection.coordinate1.x + X_OFFSET);
                    element.setAttribute("y1", connection.coordinate1.y + Y_OFFSET);
                    element.setAttribute("x2", connection.coordinate2.x + X_OFFSET);
                    element.setAttribute("y2", connection.coordinate2.y + Y_OFFSET);
                    element.setAttribute("stroke", "white");
                    element.setAttribute("stroke-width", 1);
                return element;
            }

            function createStarElements(star){
                const starElement = createSvgElement("circle");
                    starElement.classList.add("star-element");
                    starElement.setAttribute("r", STAR_SIZE);
                    starElement.setAttribute("cx", star.coordinate.x + X_OFFSET);
                    starElement.setAttribute("cy", star.coordinate.y + Y_OFFSET);
                    starElement.setAttribute("stroke", "blue");
                    starElement.setAttribute("stroke-width", 0);
                    if(star.ownerId == PLAYER_ID){
                        starElement.setAttribute("fill", "green");
                    }
                    $(starElement).hover(
                        function(){starElement.setAttribute("stroke-width", 3)},
                        function(){starElement.setAttribute("stroke-width", 0)}
                    )
                    starElement.onclick = function(){
                        eventProcessor.processEvent(new Event(events.SHOW_STAR, star.starId));
                    }

                const starNameElement = createSvgElement("text");
                    starNameElement.classList.add("star-name-element");
                    starNameElement.setAttribute("x", star.coordinate.x + X_OFFSET);
                    starNameElement.setAttribute("y", star.coordinate.y - STAR_NAME_OFFSET + Y_OFFSET);
                    starNameElement.setAttribute("text-anchor", "middle");
                    starNameElement.setAttribute("pointer-events", "none");
                    starNameElement.innerHTML = star.starName;
                return new Stream([starElement, starNameElement]);
            }
        };
    }
})();