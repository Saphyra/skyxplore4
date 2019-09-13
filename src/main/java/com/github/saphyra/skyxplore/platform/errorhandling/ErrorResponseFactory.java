package com.github.saphyra.skyxplore.platform.errorhandling;

import com.github.saphyra.authservice.auth.domain.AccessStatus;
import com.github.saphyra.skyxplore.common.ErrorCode;
import com.github.saphyra.exceptionhandling.ErrorTranslationAdapter;
import com.github.saphyra.exceptionhandling.domain.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Component
@RequiredArgsConstructor
class ErrorResponseFactory {
    private static final String GENERAL_ERROR_KEY = "generalErrorKey";
    private static final String MESSAGE_UNKNOWN_ACCESS_STATUS = "Unknown accessStatus: %s";

    private final ErrorCodeResolver errorCodeResolver;
    private final ErrorTranslationAdapter errorTranslationAdapter;

    ErrorResponse createErrorResponse(HttpServletRequest request, AccessStatus accessStatus) {
        ErrorCode errorCode = errorCodeResolver.getErrorCode(accessStatus);
        HashMap<String, String> params = new HashMap<>();
        params.put(GENERAL_ERROR_KEY, String.format(MESSAGE_UNKNOWN_ACCESS_STATUS, accessStatus.name()));
        return ErrorResponse.builder()
            .httpStatus(HttpStatus.UNAUTHORIZED.value())
            .errorCode(errorCode.name())
            .localizedMessage(errorTranslationAdapter.translateMessage(request, errorCode.name(), params))
            .params(params)
            .build();
    }
}
