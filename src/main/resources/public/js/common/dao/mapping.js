window.Mapping = {
    CREATE_GAME: "/api/game",
    DELETE_GAME: "/api/game/*",
    GET_GAMES: "/api/game",
    GET_MAP: "/api/game/map",
    GET_PLAYER_ID: "/api/game/player/id",
    GET_STAR: "/api/game/star/*",
    GET_SYSTEM_DETAILS: "/api/game/star/*/system/details",
    GET_SURFACE_DETAILS: "/api/game/surface/*",
    GET_SURFACES_OF_STAR: "/api/game/star/*/surface",
    GET_TERRAFORMING_POSSIBILITIES: "/api/game/surface/{surfaceId}/terraform",
    LOGIN: "/api/login",
    LOGOUT: "/api/logout",
    REGISTER: "/api/user",

    ACCOUNT_PAGE: "/web/account",
    GAME_PAGE: "/web/game/",
    INDEX_PAGE: "/web",
    MAIN_MENU_PAGE: "/web/main-menu",

    concat: function(path, id){
        return path.replace("*", id);
    },

    replace: function(path, pathVariables){
        let result = path;
        for(let index in pathVariables){
            const key = createKey(index);
            result = path.replace(key, pathVariables[index]);
        }

        return result;
        function createKey(index){
            return "{"+index+"}";
        }
    }
}