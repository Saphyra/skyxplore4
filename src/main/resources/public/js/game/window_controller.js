function WindowController(windowType){
    const type = windowType;
    const id = generateRandomId();

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
    EDIT_SURFACE: "EdIT_SURFACE",
    MAP: "MAP",
    STAR: "STAR"
}