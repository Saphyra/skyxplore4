package com.github.saphyra.skyxplore.app.common.exception_handling;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.github.saphyra.exceptionhandling.domain.ErrorMessage;
import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import com.github.saphyra.exceptionhandling.exception.ConflictException;
import com.github.saphyra.exceptionhandling.exception.NotFoundException;
import com.github.saphyra.exceptionhandling.exception.RestException;

public class ExceptionFactory {
    private static final String BUILDING_NOT_FOUND_PREFIX = "Building not found with buildingId %s and playerId %s";
    private static final String DATA_NOT_FOUND_PREFIX = "Data not found with dataId %s";
    private static final String GAME_NOT_FOUND_PREFIX = "Game not found with gameId %s and userId %s";
    private static final String INVALID_REQUEST_PREFIX = "Invalid field %s: %s";
    private static final String INVALID_LOCALE_PREFIX = "Locale %s is not supported";
    private static final String INVALID_PASSWORD_MESSAGE = "Invalid password.";
    private static final String INVALID_STAR_NAME_MESSAGE = "Invalid star name.";
    private static final String PLAYER_NOT_FOUND_PREFIX = "Player not found with gameId %s and userId %s";
    private static final String STAR_NOT_FOUND_PREFIX = "Star not found with starId %s";
    private static final String SURFACE_NOT_FOUND_PREFIX = "Surface not found with id %s";
    private static final String USER_NAME_ALREADY_EXISTS_PREFIX = "UserName %s already exists";
    private static final String USER_NOT_FOUND_PREFIX = "User not found with userId %s";

    private static final String INVALID_FIELD_PLACEHOLDER = "invalidField";

    public static RestException buildingNotFound(UUID buildingId, UUID playerId) {
        return new NotFoundException(createErrorMessage(ErrorCode.BUILDING_NOT_FOUND), String.format(BUILDING_NOT_FOUND_PREFIX, buildingId, playerId));
    }

    public static RestException dataNotFound(String dataId) {
        return new NotFoundException(createErrorMessage(ErrorCode.DATA_NOT_FOUND), String.format(DATA_NOT_FOUND_PREFIX, dataId));
    }

    public static RestException gameNotFound(UUID gameId, UUID userId) {
        return new NotFoundException(createErrorMessage(ErrorCode.GAME_NOT_FOUND), String.format(GAME_NOT_FOUND_PREFIX, gameId, userId));
    }

    public static RestException invalidLocale(String locale) {
        return new BadRequestException(createErrorMessage(ErrorCode.INVALID_LOCALE), String.format(INVALID_LOCALE_PREFIX, locale));
    }

    public static RestException invalidPassword() {
        return new BadRequestException(createErrorMessage(ErrorCode.INVALID_PASSWORD), INVALID_PASSWORD_MESSAGE);
    }

    public static RestException invalidRequest(String fieldName, String message) {
        Map<String, String> params = new HashMap<>();
        params.put(INVALID_FIELD_PLACEHOLDER, fieldName);
        return new BadRequestException(createErrorMessage(ErrorCode.INVALID_REQUEST, params), String.format(INVALID_REQUEST_PREFIX, fieldName, message));
    }

    public static RestException invalidStarName() {
        return new BadRequestException(createErrorMessage(ErrorCode.INVALID_STAR_NAME), INVALID_STAR_NAME_MESSAGE);
    }

    public static RestException playerNotFound(UUID userId, UUID gameId) {
        return new NotFoundException(createErrorMessage(ErrorCode.PLAYER_NOT_FOUND), String.format(PLAYER_NOT_FOUND_PREFIX, gameId, userId));
    }

    public static RestException starNotFound(UUID starId) {
        return new NotFoundException(createErrorMessage(ErrorCode.STAR_NOT_FOUND), String.format(STAR_NOT_FOUND_PREFIX, starId.toString()));
    }

    public static NotFoundException surfaceNotFound(UUID surfaceId) {
        return new NotFoundException(createErrorMessage(ErrorCode.SURFACE_NOT_FOUND), String.format(SURFACE_NOT_FOUND_PREFIX, surfaceId));
    }

    public static RestException usernameAlreadyExists(String userName) {
        return new ConflictException(createErrorMessage(ErrorCode.USER_NAME_ALREADY_EXISTS), String.format(USER_NAME_ALREADY_EXISTS_PREFIX, userName));
    }

    public static RestException userNotFound(UUID userId) {
        return new NotFoundException(createErrorMessage(ErrorCode.USER_NOT_FOUND), String.format(USER_NOT_FOUND_PREFIX, userId.toString()));
    }

    private static ErrorMessage createErrorMessage(ErrorCode errorCode) {
        return new ErrorMessage(errorCode.name());
    }

    private static ErrorMessage createErrorMessage(ErrorCode errorCode, Map<String, String> params) {
        return new ErrorMessage(errorCode.name(), params);
    }
}
