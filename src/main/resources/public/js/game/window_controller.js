function WindowController(windowType){
    const type = checkWindowType(windowType);
    const id = generateRandomId();

    function checkWindowType(windowType){
        if(!windowType){
            throwException("IllegalArgument", "WindowType must be set.");
        }

        if(!WindowType[windowType]){
            throwException("IllegalArgument", "Unknown WindowType: " + windowType);
        }

        return WindowType[windowType];
    }

    this.getId = function(){
        return id;
    }

    this.getType = function(){
        return type;
    }

    this.create = function(){
        logService.warn("WindowController.create is not overridden.");
        return this;
    }

    this.refresh = function(){
        logService.warn("WindowController.refresh is not overridden.");
        return this;
    }

    this.close = function(){
        logService.warn("WindowController.close is not overridden.");
        return this;
    }

    this.toString = function(){
        return "WindowController " + id + " - " + type;
    }
}

const WindowType = {
    ALL: "ALL",
    EDIT_SURFACE: "EDIT_SURFACE",
    MAP: "MAP",
    POPULATION_OVERVIEW: "POPULATION_OVERVIEW",
    STORAGE_SETTINGS: "STORAGE_SETTINGS",
    STAR: "STAR"
}