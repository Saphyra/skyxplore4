package com.github.saphyra.skyxplore.app.common.exception_handling.localization.code_resolver;

import com.github.saphyra.skyxplore.app.common.config.RequestDataProvider;
import com.github.saphyra.skyxplore.app.common.exception_handling.localization.LocaleProvider;
import com.github.saphyra.skyxplore.app.common.exception_handling.localization.properties.ErrorCodeLocalization;
import com.github.saphyra.skyxplore.app.common.exception_handling.localization.properties.ErrorCodeService;
import com.github.saphyra.skyxplore.app.common.utils.UuidConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
//TODO unit test
class ByUidCookieResolver {
    private final ErrorCodeService errorCodeService;
    private final LocaleProvider localeProvider;
    private final RequestDataProvider requestDataProvider;
    private final UuidConverter uuidConverter;

    Optional<ErrorCodeLocalization> getByUid(HttpServletRequest request) {
        return requestDataProvider.getUserId(request)
            .map(uuidConverter::convertEntity)
            .flatMap(this::getErrorCodeLocalizationByUserId);
    }

    private Optional<ErrorCodeLocalization> getErrorCodeLocalizationByUserId(UUID userId) {
        Optional<String> locale = localeProvider.getByUserId(userId);
        log.debug("Saved locale for userId {}: {}", userId, locale);
        return locale.flatMap(errorCodeService::getOptional);
    }
}
