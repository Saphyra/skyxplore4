package com.github.saphyra.skyxplore.platform.errorhandling.translation;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.github.saphyra.exceptionhandling.ErrorTranslationAdapter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class ErrorTranslationAdapterImpl implements ErrorTranslationAdapter {
    private final ErrorMessageResolver errorMessageResolver;
    private final ParameterInserter parameterInserter;

    public String translateMessage(HttpServletRequest request, String errorCode, @NonNull Map<String, String> params) {
        log.debug("Translating errorCode {} with params {}", errorCode, params);

        String errorMessage = errorMessageResolver.getErrorMessage(request, errorCode);
        String result = parameterInserter.insertParams(errorMessage, params);
        log.debug("Translated errorMessage: {}", result);
        return result;
    }
}
