window.Mapping = {
    CREATE_GAME: "/api/game",
    DELETE_GAME: "/api/game/*",
    GET_GAMES: "/api/game",
    GET_MAP: "/api/game/map",
    GET_PLAYER_ID: "/api/game/player/id",
    LOGIN: "/api/login",
    LOGOUT: "/api/logout",
    REGISTER: "/api/user",

    ACCOUNT_PAGE: "/web/account",
    GAME_PAGE: "/web/game/",
    INDEX_PAGE: "/web",
    MAIN_MENU_PAGE: "/web/main-menu",

    concat: function(path, id){
        return path.replace("*", id);
    }
}