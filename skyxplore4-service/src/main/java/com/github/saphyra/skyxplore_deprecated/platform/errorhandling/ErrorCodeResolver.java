package com.github.saphyra.skyxplore_deprecated.platform.errorhandling;

import com.github.saphyra.authservice.auth.domain.AccessStatus;
import com.github.saphyra.skyxplore_deprecated.common.ErrorCode;
import org.springframework.stereotype.Component;

@Component
class ErrorCodeResolver {
    ErrorCode getErrorCode(AccessStatus accessStatus) {
        switch (accessStatus) {
            case LOGIN_FAILED:
                return ErrorCode.BAD_CREDENTIALS;
            case ACCESS_TOKEN_EXPIRED:
            case ACCESS_TOKEN_NOT_FOUND:
            case COOKIE_NOT_FOUND:
            case INVALID_USER_ID:
            case USER_NOT_FOUND:
                return ErrorCode.SESSION_EXPIRED;
            default:
                return ErrorCode.GENERAL_ERROR;
        }
    }
}
