package com.github.saphyra.skyxplore.common.exception_handling.localization.code_resolver;

import com.github.saphyra.skyxplore.common.config.RequestDataProvider;
import com.github.saphyra.skyxplore.common.exception_handling.localization.properties.ErrorCodeLocalization;
import com.github.saphyra.skyxplore.common.exception_handling.localization.properties.ErrorCodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
//TODO unit test
class ByBrowserLanguageResolver {
    private final ErrorCodeService errorCodeService;
    private final RequestDataProvider requestDataProvider;

    Optional<ErrorCodeLocalization> getByBrowserLanguage(HttpServletRequest request) {
        return requestDataProvider.getBrowserLanguage(request)
            .flatMap(errorCodeService::getOptional);
    }
}
