package com.github.saphyra.skyxplore.common.config;

import com.github.saphyra.util.CookieUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Component
@Slf4j
@RequiredArgsConstructor
//TODO unit test
public class RequestDataProvider {
    private final CookieUtil cookieUtil;

    public Optional<String> getUserId(HttpServletRequest request) {
        Optional<String> userIdCookie = cookieUtil.getCookie(request, RequestConstants.COOKIE_USER_ID)
            .filter(s -> !isBlank(s));
        log.debug("userId in cookie: {}", userIdCookie);
        return userIdCookie;
    }

    public Optional<String> getLocale(HttpServletRequest request) {
        Optional<String> localeCookie = cookieUtil.getCookie(request, RequestConstants.COOKIE_LOCALE)
            .filter(s -> !isBlank(s));
        log.debug("locale in cookie: {}", localeCookie);
        return localeCookie;
    }

    public Optional<String> getBrowserLanguage(HttpServletRequest request) {
        Optional<String> browserLanguageCookie = Optional.ofNullable(request.getHeader(RequestConstants.HEADER_BROWSER_LANGUAGE))
            .filter(bl -> !isBlank(bl));
        log.debug("browserLanguage in header: {}", browserLanguageCookie);
        return browserLanguageCookie;
    }
}
