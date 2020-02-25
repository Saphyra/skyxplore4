package com.github.saphyra.skyxplore.app.domain.storage.locale;

import com.github.saphyra.skyxplore.app.common.request_context.RequestContext;
import com.github.saphyra.skyxplore.app.common.request_context.RequestContextHolder;
import com.github.saphyra.skyxplore.app.common.config.RequestConstants;
import com.github.saphyra.skyxplore.app.common.exception_handling.localization.properties.ErrorCodeService;
import com.github.saphyra.util.CookieUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class LocaleControllerTest {
    private static final String LOCALE = "locale";
    private static final UUID USER_ID = UUID.randomUUID();

    @Mock
    private CookieUtil cookieUtil;

    @Mock
    private ErrorCodeService errorCodeService;

    @Mock
    private LocaleService localeService;

    @Mock
    private RequestContextHolder requestContextHolder;

    @InjectMocks
    private LocaleController underTest;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestContext requestContext;

    @Before
    public void setUp() {
        given(requestContextHolder.get()).willReturn(requestContext);
    }

    @Test
    public void setLocale() {
        //GIVEN
        given(requestContext.getUserId()).willReturn(USER_ID);
        //WHEN
        underTest.setLocale(LOCALE, response);
        //THEN
        verify(errorCodeService).validateLocale(LOCALE);
        verify(localeService).setLocale(USER_ID, LOCALE);
        cookieUtil.setCookie(response, RequestConstants.COOKIE_LOCALE, LOCALE);
    }

    @Test
    public void setLocaleCookie() {
        //WHEN
        underTest.setLocaleCookie(LOCALE, response);
        //THEN
        verify(errorCodeService).validateLocale(LOCALE);
        cookieUtil.setCookie(response, RequestConstants.COOKIE_LOCALE, LOCALE);
    }
}