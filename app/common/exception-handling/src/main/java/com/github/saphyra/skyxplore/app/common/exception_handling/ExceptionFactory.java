package com.github.saphyra.skyxplore.app.common.exception_handling;

import com.github.saphyra.exceptionhandling.domain.ErrorMessage;
import com.github.saphyra.exceptionhandling.exception.BadRequestException;
import com.github.saphyra.exceptionhandling.exception.ConflictException;
import com.github.saphyra.exceptionhandling.exception.NotFoundException;
import com.github.saphyra.exceptionhandling.exception.RestException;

import java.util.UUID;

public class ExceptionFactory {
    private static final String GAME_NOT_FOUND_PREFIX = "Game not found with gameId %s and userId %s";
    private static final String INVALID_LOCALE_PREFIX = "Locale %s is not supported";
    private static final String INVALID_PASSWORD_MESSAGE = "Invalid password.";
    private static final String USER_NAME_ALREADY_EXISTS_PREFIX = "UserName %s already exists";
    private static final String USER_NOT_FOUND_PREFIX = "User not found with userId %s";

    public static RestException gameNotFound(UUID gameId, UUID userId) {
        return new NotFoundException(createErrorMessage(ErrorCode.GAME_NOT_FOUND), String.format(GAME_NOT_FOUND_PREFIX, gameId, userId));
    }

    public static RestException invalidLocale(String locale) {
        return new BadRequestException(createErrorMessage(ErrorCode.INVALID_LOCALE), String.format(INVALID_LOCALE_PREFIX, locale));
    }

    public static RestException invalidPassword() {
        return new BadRequestException(createErrorMessage(ErrorCode.INVALID_PASSWORD), INVALID_PASSWORD_MESSAGE);
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
}
