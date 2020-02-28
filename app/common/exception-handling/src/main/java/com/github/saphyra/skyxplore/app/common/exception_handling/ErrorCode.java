package com.github.saphyra.skyxplore.app.common.exception_handling;

import java.util.Arrays;
import java.util.Optional;

public enum ErrorCode {
    BAD_CREDENTIALS,
    GAME_NOT_FOUND,
    GENERAL_ERROR,
    INVALID_LOCALE,
    INVALID_PASSWORD,
    SESSION_EXPIRED,
    USER_NAME_ALREADY_EXISTS,
    USER_NOT_FOUND;

    public static Optional<ErrorCode> fromValue(String value) {
        return Arrays.stream(values())
            .filter(errorCode -> errorCode.name().equalsIgnoreCase(value))
            .findAny();
    }
}
