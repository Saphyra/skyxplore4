package com.github.saphyra.skyxplore_deprecated.platform.storage.locale;

import com.github.saphyra.skyxplore_deprecated.common.RequestConstants;
import com.github.saphyra.skyxplore_deprecated.data.errorcode.ErrorCodeService;
import com.github.saphyra.util.CookieUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.UUID;

import static com.github.saphyra.skyxplore_deprecated.common.RequestConstants.COOKIE_LOCALE;

@RestController
@Slf4j
@RequiredArgsConstructor
class LocaleController {
    private static final String SET_LOCALE_MAPPING = RequestConstants.API_PREFIX + "/locale/{locale}";

    private final CookieUtil cookieUtil;
    private final ErrorCodeService errorCodeService;
    private final LocaleService localeService;

    @PostMapping(SET_LOCALE_MAPPING)
    void setLocale(
        @PathVariable("locale") String locale,
        @CookieValue(value = RequestConstants.COOKIE_USER_ID, required = false) Optional<UUID> userId,
        HttpServletResponse response
    ) {
        log.info("setLocale endpoint called with locale {} by userId {}", locale, userId);
        errorCodeService.validateLocale(locale);

        userId.ifPresent(uuid -> localeService.setLocale(uuid, locale));

        cookieUtil.setCookie(response, COOKIE_LOCALE, locale);
    }
}
