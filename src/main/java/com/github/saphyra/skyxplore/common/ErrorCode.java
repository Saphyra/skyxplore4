package com.github.saphyra.skyxplore.common;

import java.util.Arrays;
import java.util.Optional;

public enum ErrorCode {
    BAD_CREDENTIALS,
    BUILDING_NOT_FOUND,
    CITIZEN_NOT_FOUND,
    CONSTRUCTION_IN_PROGRESS,
    CONSTRUCTION_NOT_FOUND,
    DATA_NOT_FOUND,
    GAME_NOT_FOUND,
    GENERAL_ERROR,
    INVALID_BUILD_LOCATION,
    INVALID_CITIZEN_NAME,
    INVALID_LOCALE,
    INVALID_REQUEST_CONTEXT,
    INVALID_STAR_ACCESS,
    MAX_LEVEL_REACHED,
    PLAYER_NOT_FOUND,
    RESEARCH_NOT_PRESENT,
    RESERVATION_NOT_FOUND,
    SESSION_EXPIRED,
    STAR_NOT_FOUND,
    STORAGE_FULL,
    STORAGE_SETTING_ALREADY_EXISTS,
    STORAGE_SETTING_NOT_FOUND,
    SURFACE_NOT_FOUND,
    TERRAFORMING_ALREADY_IN_PROGRESS,
    TERRAFORMING_NOT_POSSIBLE,
    UPGRADE_ALREADY_IN_PROGRESS,
    USER_NAME_ALREADY_EXISTS,
    USER_NOT_FOUND;

    public static Optional<ErrorCode> fromValue(String value) {
        return Arrays.stream(values())
            .filter(errorCode -> errorCode.name().equalsIgnoreCase(value))
            .findAny();
    }
}
