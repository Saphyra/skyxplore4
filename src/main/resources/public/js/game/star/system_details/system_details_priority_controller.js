(function SystemDetailsPriorityController(){
    const priorityTypeLocalization = localizations.priorityTypeLocalization;

    window.systemDetailsPriorityController = new function(){
        this.createPriorityDetails = createPriorityDetails;
    }

    function createPriorityDetails (starId, priorities){
        const container = document.createElement("div");
            container.classList.add("bar-container");

            const header = document.createElement("div");
                header.classList.add("bar-container-header");
                header.innerHTML = Localization.getAdditionalContent("priorities");
        container.appendChild(header);

            new Stream(priorities)
                .sorted(function(a, b){return priorityTypeLocalization.get(a.type).localeCompare(priorityTypeLocalization.get(b.type))})
                .map(function(priority){return createItem(starId, priority)})
                .forEach(function(item){container.appendChild(item)});
        return container;

        function createItem(starId, priority){
            const item = document.createElement("div");
                item.classList.add("bar-list-item");

                const itemName = document.createElement("span");
                    itemName.innerHTML = priorityTypeLocalization.get(priority.type) + ": ";
            item.appendChild(itemName);

                const input = document.createElement("input");
                    input.type = "range";
                    input.min = 1;
                    input.max = 10
                    input.step = 1;
                    input.value = priority.priority;
            item.appendChild(input);

                const value = document.createElement("span");
                    value.innerHTML = priority.priority;
            item.appendChild(value);

                input.onchange = function(){
                    value.innerHTML = input.value;
                    updatePriority(starId, priority.type, input.value);
                }
            return item;
        }
    }

    function updatePriority(starId, type, priority){
        const request = new Request(
            HttpMethod.POST,
            Mapping.replace(
                Mapping.UPDATE_SYSTEM_PRIORITY,
                {
                    starId: starId,
                    type: type
                }
            ),
            {value: priority}
        );
            request.processValidResponse = function(){
                notificationService.showSuccess(Localization.getAdditionalContent("priority-updated"));
            }
        dao.sendRequestAsync(request);
    }
})();