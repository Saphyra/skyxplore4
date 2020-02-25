package com.github.saphyra.skyxplore.app.common.exception_handling.localization.code_resolver;

import com.github.saphyra.skyxplore.app.common.config.RequestConstants;
import com.github.saphyra.skyxplore.app.common.exception_handling.localization.properties.ErrorCodeLocalization;
import com.github.saphyra.skyxplore.app.common.exception_handling.localization.properties.ErrorCodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
//TODO unit test
public class ErrorCodeLocalizationResolver {
    private final ByBrowserLanguageResolver byBrowserLanguageResolver;
    private final ByLocaleCookieResolver byLocaleCookieResolver;
    private final ByUidCookieResolver byUidCookieResolver;

    private final ErrorCodeService errorCodeService;

    public Optional<ErrorCodeLocalization> getErrorCodeLocalization(HttpServletRequest request) {
        Optional<ErrorCodeLocalization> errorCodeLocalization = byUidCookieResolver.getByUid(request);

        if (!errorCodeLocalization.isPresent()) {
            errorCodeLocalization = byLocaleCookieResolver.getByLocaleCookie(request);
        }

        if (!errorCodeLocalization.isPresent()) {
            errorCodeLocalization = byBrowserLanguageResolver.getByBrowserLanguage(request);
        }

        if (!errorCodeLocalization.isPresent()) {
            errorCodeLocalization = errorCodeService.getOptional(RequestConstants.DEFAULT_LOCALE);
        }

        return errorCodeLocalization;
    }
}
