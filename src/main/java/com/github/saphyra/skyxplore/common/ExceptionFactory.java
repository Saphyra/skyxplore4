package com.github.saphyra.skyxplore.common;

import com.github.saphyra.exceptionhandling.domain.ErrorMessage;
import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import com.github.saphyra.exceptionhandling.exception.ConflictException;
import com.github.saphyra.exceptionhandling.exception.ForbiddenException;
import com.github.saphyra.exceptionhandling.exception.NotFoundException;
import com.github.saphyra.exceptionhandling.exception.RestException;
import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class ExceptionFactory {
    private static final String GAME_NOT_FOUND_PREFIX = "Game not found with gameId %s";
    private static final String INVALID_GAME_ACCESS_PREFIX = "%s has no access to game %s";
    private static final String INVALID_LOCALE_PREFIX = "Locale %s is not supported";
    private static final String USER_NAME_ALREADY_EXISTS_PREFIX = "UserName %s already exists";

    public static RestException gameNotFound(UUID gameId) {
        return new NotFoundException(createErrorMessage(ErrorCode.GAME_NOT_FOUND), String.format(GAME_NOT_FOUND_PREFIX, gameId));
    }

    public static RestException invalidGameAccess(UUID userId, UUID gameId) {
        return new ForbiddenException(createErrorMessage(ErrorCode.INVALID_GAME_ACCESS), String.format(INVALID_GAME_ACCESS_PREFIX, userId.toString(), gameId.toString()));
    }

    public static RestException invalidLocale(String locale) {
        return new BadRequestException(createErrorMessage(ErrorCode.INVALID_LOCALE), String.format(INVALID_LOCALE_PREFIX, locale));
    }

    public static RestException userNameAlreadyExists(String userName) {
        return new ConflictException(createErrorMessage(ErrorCode.USER_NAME_ALREADY_EXISTS), String.format(USER_NAME_ALREADY_EXISTS_PREFIX, userName));
    }

    private static ErrorMessage createErrorMessage(ErrorCode errorCode) {
        return new ErrorMessage(errorCode.name());
    }
}
