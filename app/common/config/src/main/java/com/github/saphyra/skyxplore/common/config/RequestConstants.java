package com.github.saphyra.skyxplore.common.config;

import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.List;

@UtilityClass
public class RequestConstants {
    public static final String WEB_PREFIX = "/web";
    public static final String API_PREFIX = "/api";

    public static final String INDEX_MAPPING = WEB_PREFIX;
    public static final String REGISTRATION_MAPPING = RequestConstants.API_PREFIX + "/user";

    public static final List<String> PROPERTY_PATHS = Arrays.asList(
        "/**/favicon.ico",
        "/css/**",
        "/images/**",
        "/js/**",
        "/i18n/**",
        API_PREFIX + "/data/**"
    );
}
