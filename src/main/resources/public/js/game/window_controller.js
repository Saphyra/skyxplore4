function WindowController(){
    this.create = function(){
        logService.warn("WindowController.create is not overridden.");
        return this;
    }

    this.refresh = function(){
        logService.warn("WindowController.refresh is not overridden.");
        return this;
    }

    this.hide = function(){
        logService.warn("WindowController.hide is not overridden.");
        return this;
    }
}