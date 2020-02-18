package com.github.saphyra.skyxplore.common.exception_handling;

import java.util.Arrays;
import java.util.Optional;

public enum ErrorCode {
    INVALID_PASSWORD,
    USER_NAME_ALREADY_EXISTS,
    USER_NOT_FOUND;

    public static Optional<ErrorCode> fromValue(String value) {
        return Arrays.stream(values())
            .filter(errorCode -> errorCode.name().equalsIgnoreCase(value))
            .findAny();
    }
}
