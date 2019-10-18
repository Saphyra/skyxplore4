function WindowController(){
    const id = generateRandomId();

    this.getId = function(){
        return id;
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
}