window.Mapping = {
    BUILD_BUILDING: "/api/game/building/*",
    CANCEL_QUEUE_ITEM: "/api/game/star/{starId}/queue/{queueItemId}",
    CREATE_GAME: "/api/game",
    CREATE_STORAGE_SETTING: "/api/game/star/*/system/storage-settings",
    DELETE_GAME: "/api/game/*",
    DELETE_STORAGE_SETTING: "/api/game/star/system/storage-settings/*",
    GET_BUILDABLE_BUILDINGS: "/api/data/building/*",
    GET_GAMES: "/api/game",
    GET_MAP: "/api/game/map",
    GET_PLAYER_ID: "/api/game/player/id",
    GET_POPULATION_OVERVIEW: "/api/game/star/*/population/overview",
    GET_QUEUE: "/api/game/star/*/queue",
    GET_STAR: "/api/game/star/*",
    GET_STORAGE_SETTINGS: "/api/game/star/*/system/storage-settings",
    GET_STORAGE_SETTINGS_CREATION_DETAILS: "/api/game/star/*/system/storage-settings/creation-details",
    GET_SURFACE_DETAILS: "/api/game/surface/*",
    GET_SURFACES_OF_STAR: "/api/game/star/*/surface",
    GET_SYSTEM_DETAILS: "/api/game/star/*/system/details",
    GET_TERRAFORMING_POSSIBILITIES: "/api/game/surface/{surfaceId}/terraform",
    LOGIN: "/api/login",
    LOGOUT: "/api/logout",
    NEW_ROUND: "/api/game",
    REGISTER: "/api/user",
    RENAME_CITIZEN: "/api/game/star/citizen/*",
    RENAME_STAR: "/api/game/star/*",
    TERRAFORM_SURFACE: "/api/game/surface/*/terraform",
    UPDATE_QUEUE_PRIORITY: "/api/game/star/{starId}/queue/{queueItemId}",
    UPDATE_STORAGE_SETTING: "/api/game/star/system/storage-settings/*",
    UPDATE_SYSTEM_PRIORITY: "/api/game/star/{starId}/priority/{type}",
    UPGRADE_BUILDING: "/api/game/building/*/upgrade",

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
            result = result.replace(key, pathVariables[index]);
        }

        return result;
        function createKey(index){
            return "{" + index + "}";
        }
    }
}