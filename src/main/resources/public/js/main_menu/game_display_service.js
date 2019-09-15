(function GameDisplayService(){
    eventProcessor.registerProcessor(new EventProcessor(
        function(eventType){return eventType == events.LOCALIZATION_LOADED},
        displayGames
    ));

    function displayGames(){
        const request = new Request(HttpMethod.GET, Mapping.GET_GAMES);
            request.convertResponse = function(response){
                const games = JSON.parse(response.body);
                    games.sort(function(a, b){return a.gameName.localeCompare(b.gameName)});
                return games;
            }
            request.processValidResponse = function(games){
                if(!games.length){
                    return;
                }
                $("#no-game").hide();

                const container = document.getElementById("games");
                for(let gIndex in games){
                    container.appendChild(createGame(games[gIndex]));
                }
            }
        dao.sendRequestAsync(request);
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
                        deleteGame(gameId);
                    }
            operationsCell.appendChild(deleteButton);
        row.appendChild(operationsCell);
        return row;
    }
})();