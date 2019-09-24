package com.github.saphyra.skyxplore.common;

import java.util.Arrays;
import java.util.List;

import lombok.experimental.UtilityClass;

@UtilityClass
public class RequestConstants {
    public static final String DEFAULT_LOCALE = "hu";
    public static final String COOKIE_LOCALE = "locale";
    public static final String COOKIE_USER_ID = "userId";
    public static final String COOKIE_GAME_ID = "game_id";
    public static final String HEADER_BROWSER_LANGUAGE = "BrowserLanguage";

    public static final String WEB_PREFIX = "/web";
    public static final String API_PREFIX = "/api";

    public static final List<String> PROPERTY_PATHS = Arrays.asList(
        "/**/favicon.ico",
        "/css/**",
        "/images/**",
        "/js/**",
        "/i18n/**"
    );
}
