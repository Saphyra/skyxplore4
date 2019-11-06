package com.github.saphyra.skyxplore.common;

import java.util.UUID;

import com.github.saphyra.exceptionhandling.domain.ErrorMessage;
import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import com.github.saphyra.exceptionhandling.exception.ConflictException;
import com.github.saphyra.exceptionhandling.exception.ForbiddenException;
import com.github.saphyra.exceptionhandling.exception.NotFoundException;
import com.github.saphyra.exceptionhandling.exception.RestException;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ExceptionFactory {
    private static final String BUILDING_NOT_FOUND_PREFIX = "Building not found with id %s";
    private static final String DATA_NOT_FOUND_PREFIX = "Data not found with dataId %s";
    private static final String GAME_NOT_FOUND_PREFIX = "Game not found with gameId %s";
    private static final String INVALID_GAME_ACCESS_PREFIX = "%s has no access to game %s";
    private static final String INVALID_LOCALE_PREFIX = "Locale %s is not supported";
    private static final String INVALID_STAR_ACCESS_PREFIX = "Player %s has no access to access to star %s";
    private static final String PLAYER_NOT_FOUND_PREFIX = "Player not found with gameId %s and userId %s";
    private static final String STAR_NOT_FOUND_PREFIX = "Star not found with starId %s";
    private static final String SURFACE_NOT_FOUND_PREFIX = "Surface not found with id %s";
    private static final String USER_NAME_ALREADY_EXISTS_PREFIX = "UserName %s already exists";
    private static final String USER_NOT_FOUND_PREFIX = "User not found with userId %s";

    public static RestException buildingNotFound(UUID buildingId) {
        return new NotFoundException(createErrorMessage(ErrorCode.BUILDING_NOT_FOUND), String.format(BUILDING_NOT_FOUND_PREFIX, buildingId));
    }

    public static RestException dataNotFount(String dataId) {
        return new NotFoundException(createErrorMessage(ErrorCode.DATA_NOT_FOUND), String.format(DATA_NOT_FOUND_PREFIX, dataId));
    }

    public static RestException gameNotFound(UUID gameId) {
        return new NotFoundException(createErrorMessage(ErrorCode.GAME_NOT_FOUND), String.format(GAME_NOT_FOUND_PREFIX, gameId));
    }

    public static RestException invalidGameAccess(UUID userId, UUID gameId) {
        return new ForbiddenException(createErrorMessage(ErrorCode.INVALID_GAME_ACCESS), String.format(INVALID_GAME_ACCESS_PREFIX, userId.toString(), gameId.toString()));
    }

    public static RestException invalidLocale(String locale) {
        return new BadRequestException(createErrorMessage(ErrorCode.INVALID_LOCALE), String.format(INVALID_LOCALE_PREFIX, locale));
    }

    public static RestException invalidStarAccess(UUID playerId, UUID starId) {
        return new ForbiddenException(createErrorMessage(ErrorCode.INVALID_STAR_ACCESS), String.format(INVALID_STAR_ACCESS_PREFIX, playerId, starId));
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

    public static RestException userNameAlreadyExists(String userName) {
        return new ConflictException(createErrorMessage(ErrorCode.USER_NAME_ALREADY_EXISTS), String.format(USER_NAME_ALREADY_EXISTS_PREFIX, userName));
    }

    public static RestException userNotFound(UUID userId) {
        return new NotFoundException(createErrorMessage(ErrorCode.USER_NOT_FOUND), String.format(USER_NOT_FOUND_PREFIX, userId.toString()));
    }

    private static ErrorMessage createErrorMessage(ErrorCode errorCode) {
        return new ErrorMessage(errorCode.name());
    }
}
