package com.github.saphyra.skyxplore.platform.errorhandling.translation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@Slf4j
@RequiredArgsConstructor
class ErrorMessageResolver {
    private static final String DEFAULT_ERROR_TRANSLATION_PREFIX = "Could not translate errorCode %s";

    private final ErrorCodeLocalizationResolver errorCodeLocalizationResolver;

    String getErrorMessage(HttpServletRequest request, String errorCode) {
        String errorMessage = errorCodeLocalizationResolver.getErrorCodeLocalization(request)
            .flatMap(errorCodes -> errorCodes.getOptional(errorCode))
            .orElseGet(() -> String.format(DEFAULT_ERROR_TRANSLATION_PREFIX, errorCode));
        log.debug("ErrorMessage found: {}", errorMessage);
        return errorMessage;
    }
}
