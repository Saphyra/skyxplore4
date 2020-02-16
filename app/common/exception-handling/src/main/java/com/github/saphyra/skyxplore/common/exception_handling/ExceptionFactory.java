package com.github.saphyra.skyxplore.common.exception_handling;

import com.github.saphyra.exceptionhandling.domain.ErrorMessage;
import com.github.saphyra.exceptionhandling.exception.ConflictException;
import com.github.saphyra.exceptionhandling.exception.NotFoundException;
import com.github.saphyra.exceptionhandling.exception.RestException;

import java.util.UUID;

public class ExceptionFactory {
    private static final String USER_NAME_ALREADY_EXISTS_PREFIX = "UserName %s already exists";
    private static final String USER_NOT_FOUND_PREFIX = "User not found with userId %s";

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
