(function GameDisplayService(){
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.LOCALIZATION_LOADED},
        loadGames
    ));

    function loadGames(){
        const request = new Request(HttpMethod.GET, Mapping.GET_GAMES);
            request.convertResponse = function(response){
                const games = JSON.parse(response.body);
                    games.sort(function(a, b){return a.gameName.localeCompare(b.gameName)});
                return games;
            }
            request.processValidResponse = function(games){
                displayGames(games);
            }
        dao.sendRequestAsync(request);
    }

    function displayGames(games){
        if(!games.length){
            $("#no-game").show();
            return;
        }
        $("#no-game").hide();

        const container = document.getElementById("games");
            container.innerHTML = "";

        for(let gIndex in games){
            container.appendChild(createGame(games[gIndex]));
        }

        function createGame(game){
            const row = document.createElement("TR");

                const nameCell = document.createElement("TD");
                    nameCell.classList.add("game-name-cell");
                    nameCell.title = Localization.getAdditionalContent("select-game");
                    nameCell.innerHTML = game.gameName;
                    nameCell.onclick = function(){
                        selectGame(game.gameId);
                    }
            row.appendChild(nameCell)

                const operationsCell = document.createElement("TD");
                    operationsCell.classList.add("game-operations");

                    const deleteButton = document.createElement("BUTTON");
                        deleteButton.innerHTML = Localization.getAdditionalContent("delete-game-button");
                        deleteButton.onclick = function(){
                            deleteGame(game.gameId, row);
                        }
                operationsCell.appendChild(deleteButton);
            row.appendChild(operationsCell);
            return row;
        }
    }

    function deleteGame(gameId, row){
        if(!confirm(Localization.getAdditionalContent("confirm-game-deletion"))){
            return;
        }

        const request = new Request(HttpMethod.DELETE, Mapping.concat(Mapping.DELETE_GAME, gameId));
            request.processValidResponse = function(){
                notificationService.showSuccess(Localization.getAdditionalContent("game-deleted"));
                loadGames();
            }
        dao.sendRequestAsync(request);
    }
})();