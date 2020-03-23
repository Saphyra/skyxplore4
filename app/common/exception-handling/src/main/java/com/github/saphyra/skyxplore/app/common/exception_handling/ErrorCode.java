package com.github.saphyra.skyxplore.app.common.exception_handling;

import java.util.Arrays;
import java.util.Optional;

public enum ErrorCode {
    BAD_CREDENTIALS,
    GAME_NOT_FOUND,
    GENERAL_ERROR,
    INVALID_REQUEST,
    INVALID_LOCALE,
    INVALID_PASSWORD,
    PLAYER_NOT_FOUND,
    SESSION_EXPIRED,
    STAR_NOT_FOUND,
    SURFACE_NOT_FOUND,
    USER_NAME_ALREADY_EXISTS,
    USER_NOT_FOUND;

    public static Optional<ErrorCode> fromValue(String value) {
        return Arrays.stream(values())
            .filter(errorCode -> errorCode.name().equalsIgnoreCase(value))
            .findAny();
    }
}
