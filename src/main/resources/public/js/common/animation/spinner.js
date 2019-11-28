(function Spinner(){
    const SPINNER_ID = "spinner";

    window.spinner = new function(){
        this.open = function(){
           $(getOrCreateSpinner()).show();
       };
        this.close = function(){
            getSpinner().ifPresent(function(spinner){$(spinner).hide()});
        };
    }

    function getOrCreateSpinner(){
        return getSpinner()
            .orElseGet(createSpinner);

        function createSpinner(){
            const spinner = document.createElement("div");
                spinner.id = SPINNER_ID;
                spinner.classList.add("spinner");
            document.body.appendChild(spinner);
            return spinner;
        }
    }

    function getSpinner(){
        return new Optional(document.getElementById(SPINNER_ID));
    }
})();