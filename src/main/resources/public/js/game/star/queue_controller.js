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
                    displayQueue(starId, queue, containerId);
                }
            dao.sendRequestAsync(request);
        }
    }

    function displayQueue(starId, queue, containerId){
        const container = document.getElementById(containerId);
            container.innerHTML = "";

        new Stream(queue)
            .map(function(queueItem){return createQueueElement(queueItem, containerId)})
            .forEach(function(queueElement){container.appendChild(queueElement)});

        function createQueueElement(queueItem){
            const queueElement = document.createElement("div");
                queueElement.classList.add("bar-list-item");
                queueElement.classList.add("queue-item");

                queueElement.appendChild(createHeader(queueItem));
                queueElement.appendChild(createPrioritySlider(starId, queueItem, containerId));
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

            function createPrioritySlider(starId, queueItem, containerId){
                const sliderContainer = document.createElement("div");
                    sliderContainer.classList.add("star-view-queue-slider-container");
                    const prioritySpan = document.createElement("SPAN");
                        prioritySpan.innerHTML = Localization.getAdditionalContent("priority") + ": ";
                sliderContainer.appendChild(prioritySpan);

                    const slider = document.createElement("input");
                        slider.style.verticalAlign = "middle";
                        slider.min = 1;
                        slider.max = 10;
                        slider.step = 1;
                        slider.value = queueItem.priority;
                        slider.type = "range";
                sliderContainer.appendChild(slider);

                    const value = document.createElement("SPAN");
                        value.innerHTML = queueItem.priority;
                sliderContainer.appendChild(value);

                    slider.onchange = function(){
                        value.innerHTML = slider.value;

                        updatePriority(starId, queueItem, slider.value, containerId);
                    }
                return sliderContainer;
            }

            function createCancelButton(starId, queueItem){
                const button = document.createElement("div");
                    button.classList.add("button");
                    button.innerHTML = Localization.getAdditionalContent("cancel");

                    button.onclick = function(){
                        if(confirm(Localization.getAdditionalContent("confirm-cancel-queue-item"))){
                            cancelQueueItem(starId, queueItem);
                        }
                    }
                return button;
            }
        }
    }

    function updatePriority(starId, queueItem, priority, containerId){
        const request = new Request(
            HttpMethod.POST,
            Mapping.replace(
                Mapping.UPDATE_PRIORITY,
                {
                    starId: starId,
                    queueItemId: queueItem.queueItemId
                }
            ),
            {
                queueType: queueItem.queueType,
                priority: priority
            }
        );
            request.processValidResponse = function(){
                queueController.showQueue(starId, containerId);
            }
        dao.sendRequestAsync(request);
    }

    function cancelQueueItem(starId, queueItem){
        const request = new Request(
            HttpMethod.DELETE,
            Mapping.replace(
                Mapping.CANCEL_QUEUE_ITEM,
                {
                    starId: starId,
                    queueItemId: queueItem.queueItemId
                }
            ),
            {value: queueItem.queueType}
        );
            request.processValidResponse = function(){
                notificationService.showSuccess(Localization.getAdditionalContent("queue-item-cancelled"));
                eventProcessor.processEvent(new Event(events.REFRESH_WINDOWS, WindowType.STAR))
            }
        dao.sendRequestAsync(request);
    }
})();