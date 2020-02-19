package com.github.saphyra.skyxplore.common.exception_handling.localization;

import com.github.saphyra.skyxplore.common.config.RequestConstants;
import com.github.saphyra.skyxplore.common.exception_handling.localization.properties.ErrorCodeLocalization;
import com.github.saphyra.skyxplore.common.exception_handling.localization.properties.ErrorCodeService;
import com.github.saphyra.skyxplore.common.utils.UuidConverter;
import com.github.saphyra.util.CookieUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Component
@RequiredArgsConstructor
@Slf4j
//TODO unit test
class ErrorCodeLocalizationResolver {
    private final CookieUtil cookieUtil;
    private final ErrorCodeService errorCodeService;
    private final LocaleProvider localeProvider;
    private final UuidConverter uuidConverter;

    Optional<ErrorCodeLocalization> getErrorCodeLocalization(HttpServletRequest request) {
        Optional<String> userIdCookie = cookieUtil.getCookie(request, RequestConstants.COOKIE_USER_ID)
            .filter(s -> !isBlank(s));
        log.debug("userId in cookie: {}", userIdCookie);

        Optional<String> localeCookie = cookieUtil.getCookie(request, RequestConstants.COOKIE_LOCALE)
            .filter(s -> !isBlank(s));
        log.debug("locale in cookie: {}", localeCookie);

        Optional<String> browserLanguageCookie = Optional.ofNullable(request.getHeader(RequestConstants.HEADER_BROWSER_LANGUAGE))
            .filter(bl -> !isBlank(bl));
        log.debug("browserLanguage in header: {}", browserLanguageCookie);

        Optional<ErrorCodeLocalization> errorCodeLocalization = Optional.empty();
        if (userIdCookie.isPresent()) {
            UUID userId = userIdCookie
                .map(uuidConverter::convertEntity)
                .get();
            errorCodeLocalization = getErrorCodeLocalizationByUserId(userId);
        }

        if (!errorCodeLocalization.isPresent() && localeCookie.isPresent()) {
            log.debug("errorCodeLocalization not found with userSetting. Using localeCookie...");
            errorCodeLocalization = errorCodeService.getOptional(localeCookie.get());
        }

        if (!errorCodeLocalization.isPresent() && browserLanguageCookie.isPresent()) {
            log.debug("errorCodeLocalization not found with userSetting and localeCookie. Using browserLanguage...");
            errorCodeLocalization = errorCodeService.getOptional(browserLanguageCookie.get());
        }

        if (!errorCodeLocalization.isPresent()) {
            log.debug("errorCodeLocalization not found with userSetting, localeCookie and browserLanguage.. Using defaultLocale...");
            errorCodeLocalization = errorCodeService.getOptional(RequestConstants.DEFAULT_LOCALE);
        }
        return errorCodeLocalization;
    }

    private Optional<ErrorCodeLocalization> getErrorCodeLocalizationByUserId(UUID userId) {
        Optional<String> locale = localeProvider.getByUserId(userId);
        log.debug("Saved locale for userId {}: {}", userId, locale);
        return locale.flatMap(errorCodeService::getOptional);
    }
}
