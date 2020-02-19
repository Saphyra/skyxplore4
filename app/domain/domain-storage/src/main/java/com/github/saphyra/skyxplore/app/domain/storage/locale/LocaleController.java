package com.github.saphyra.skyxplore.app.domain.storage.locale;

import com.github.saphyra.skyxplore.app.common.request_context.RequestContext;
import com.github.saphyra.skyxplore.app.common.request_context.RequestContextHolder;
import com.github.saphyra.skyxplore.common.config.RequestConstants;
import com.github.saphyra.skyxplore.common.exception_handling.localization.properties.ErrorCodeService;
import com.github.saphyra.util.CookieUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

import static com.github.saphyra.skyxplore.common.config.RequestConstants.COOKIE_LOCALE;

@RestController
@Slf4j
@RequiredArgsConstructor
class LocaleController {
    private static final String SET_LOCALE_COOKIE_MAPPING = RequestConstants.API_PREFIX + "/locale/{locale}";
    private static final String SET_LOCALE_MAPPING = RequestConstants.API_PREFIX + "/user/locale/{locale}";

    private final CookieUtil cookieUtil;
    private final ErrorCodeService errorCodeService;
    private final LocaleService localeService;
    private final RequestContextHolder requestContextHolder;

    @PostMapping(SET_LOCALE_MAPPING)
    void setLocale(
        @PathVariable("locale") String locale,
        HttpServletResponse response) {
        setLocaleCookie(locale, response);
        RequestContext requestContext = requestContextHolder.get();
        localeService.setLocale(requestContext.getUserId(), locale);
    }

    @PostMapping(SET_LOCALE_COOKIE_MAPPING)
    void setLocaleCookie(
        @PathVariable("locale") String locale,
        HttpServletResponse response
    ) {
        errorCodeService.validateLocale(locale);
        cookieUtil.setCookie(response, COOKIE_LOCALE, locale);
    }
}
