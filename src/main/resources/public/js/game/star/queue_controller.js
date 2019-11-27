(function QueueController(){
    const queueTypeLocalization = localizations.queueTypeLocalization;
    const buildingLocalization = localizations.buildingLocalization;
    const surfaceTypeLocalization = localizations.surfaceTypeLocalization;

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
                    displayQueue(starId, queue, document.getElementById(containerId));
                }
            dao.sendRequestAsync(request);
        }
    }

    function displayQueue(starId, queue, container){
        container.innerHTML = "";

        new Stream(queue)
            .map(createQueueElement)
            .forEach(function(queueElement){container.appendChild(queueElement)});

        function createQueueElement(queueItem){
            const queueElement = document.createElement("div");
                queueElement.classList.add("bar-list-item");
                queueElement.classList.add("queue-item");

                queueElement.appendChild(createHeader(queueItem));
                queueElement.appendChild(createPrioritySlider(starId, queueItem));
                queueElement.appendChild(createCancelButton(starId, queueItem));
            return queueElement;

            function createHeader(queueItem){
                const header = document.createElement("h3");
                    header.innerHTML = queueTypeLocalization.get(queueItem.queueType) + " - " + getName(queueItem.queueType, queueItem.additionalData);
                return header;

                function getName(queueType, additionalData){
                    switch(queueType){
                        case "CONSTRUCTION":
                            return buildingLocalization.get(additionalData);
                        break;
                        case "TERRAFORMING":
                            return surfaceTypeLocalization.get(additionalData);
                        break;
                        default:
                            throwException("IllegalArgument", "Name could not be resolved from queueType " + queueType);
                        break;
                    }
                }
            }

            function createPrioritySlider(starId, queueItem){
                const sliderContainer = document.createElement("div");
                //TODO implement
                return sliderContainer;
            }

            function createCancelButton(starId, queueElement){
                const button = document.createElement("div");
                    button.classList.add("button");
                    button.innerHTML = Localization.getAdditionalContent("cancel");

                    button.onclick = function(){
                        if(confirm(Localization.getAdditionalContent("confirm-cancel-queue-item"))){
                            //TODO implement
                        }
                    }
                return button;
            }
        }
    }
})();