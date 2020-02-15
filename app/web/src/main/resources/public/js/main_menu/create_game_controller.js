(function CreateGameController(){
    scriptLoader.loadScript("/js/common/validation_util.js");
    scriptLoader.loadScript("/js/common/animation/spinner.js");

    const INVALID_GAME_NAME = "#invalid-new-game-name";

    events.CREATE_GAME_ATTEMPT = "create_game_attempt";
    events.VALIDATION_ATTEMPT = "validation_attempt";

    let validationTimeout = null;
    let gameCreationAllowed = false;

    $(document).ready(function(){
        $("#new-game-name").on("keyup", function(e){
            if(e.which == 13){
                eventProcessor.processEvent(new Event(events.CREATE_GAME_ATTEMPT));
            }else{
                eventProcessor.processEvent(new Event(events.VALIDATION_ATTEMPT));
            }
        });
        $("#new-game-name").on("focusin", function(){
            eventProcessor.processEvent(new Event(events.VALIDATION_ATTEMPT));
        });
    });

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.VALIDATION_ATTEMPT},
        function(){
            blockGameCreation();

            if(validationTimeout){
                clearTimeout(validationTimeout);
            }
            validationTimeout = setTimeout(validateGameName, getValidationTimeout());
        }
    ));

    function validateGameName(){
        const gameName = getGameName();

        if(gameName.length < 3){
            blockGameCreation();
            createErrorProcess(INVALID_GAME_NAME, "game-name-too-short")();
        }else if(gameName.length > 30){
            blockGameCreation();
            createErrorProcess(INVALID_GAME_NAME, "game-name-too-short")();
        }else{
            createSuccessProcess(INVALID_GAME_NAME)();
            allowGameCreation();
        }
    }

    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.CREATE_GAME_ATTEMPT},
        function(){
            if(!gameCreationAllowed){
                return;
            }

            spinner.open();
            const gameName = getGameName();
                document.getElementById("new-game-name").value = "";
            const request = new Request(HttpMethod.PUT, Mapping.CREATE_GAME, {value: gameName});
                request.processValidResponse = function(response){
                    sessionStorage.successMessage = "game-created";
                    selectGame(response.body);
                }
            dao.sendRequestAsync(request);
        }
    ));

    function getGameName(){
        return document.getElementById("new-game-name").value;
    }

    function blockGameCreation(){
        gameCreationAllowed = false;
        setCreateGameButtonState();
    }

    function allowGameCreation(){
        gameCreationAllowed = true;
        setCreateGameButtonState();
    }

    function setCreateGameButtonState(){
        document.getElementById("new-game-button").disabled = !gameCreationAllowed;
    }

})();