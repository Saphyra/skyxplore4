package com.github.saphyra.skyxplore.app.common.exception_handling.localization.code_resolver;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.app.common.config.RequestDataProvider;
import com.github.saphyra.skyxplore.app.common.exception_handling.localization.properties.ErrorCodeLocalization;
import com.github.saphyra.skyxplore.app.common.exception_handling.localization.properties.ErrorCodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
class ByLocaleCookieResolver {
    private final ErrorCodeService errorCodeService;
    private final RequestDataProvider requestDataProvider;

    Optional<ErrorCodeLocalization> getByLocaleCookie(HttpServletRequest request) {
        return requestDataProvider.getLocale(request)
            .flatMap(errorCodeService::getOptional);
    }
}
