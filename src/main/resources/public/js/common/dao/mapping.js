window.Mapping = {

    LOGIN: "/api/login",
    LOGOUT: "/api/logout",
    REGISTER: "/api/user",

    INDEX_PAGE: "/web",
    MAIN_MENU_PAGE: "/web/main-menu",

    concat: function(path, id){
        return path.replace("*", id);
    }
}