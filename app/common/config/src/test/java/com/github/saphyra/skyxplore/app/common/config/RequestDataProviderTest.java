package com.github.saphyra.skyxplore.app.common.config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.util.CookieUtil;

@RunWith(MockitoJUnitRunner.class)
public class RequestDataProviderTest {
    private static final String USER_ID = "user-id";
    private static final String LOCALE = "locale";
    private static final String BROWSER_LANGUAGE = "browser-language";

    @Mock
    private CookieUtil cookieUtil;

    @InjectMocks
    private RequestDataProvider underTest;

    @Mock
    private HttpServletRequest request;

    @Test
    public void getUserId_blank() {
        //GIVEN
        given(cookieUtil.getCookie(request, RequestConstants.COOKIE_USER_ID)).willReturn(Optional.of(" "));
        //WHEN
        Optional<String> result = underTest.getUserId(request);
        //THEN
        assertThat(result).isEmpty();
    }

    @Test
    public void getUserId() {
        //GIVEN
        given(cookieUtil.getCookie(request, RequestConstants.COOKIE_USER_ID)).willReturn(Optional.of(USER_ID));
        //WHEN
        Optional<String> result = underTest.getUserId(request);
        //THEN
        assertThat(result).contains(USER_ID);
    }

    @Test
    public void getLocale_blank() {
        //GIVEN
        given(cookieUtil.getCookie(request, RequestConstants.COOKIE_LOCALE)).willReturn(Optional.of(" "));
        //WHEN
        Optional<String> result = underTest.getLocale(request);
        //THEN
        assertThat(result).isEmpty();
    }

    @Test
    public void getLocale() {
        //GIVEN
        given(cookieUtil.getCookie(request, RequestConstants.COOKIE_LOCALE)).willReturn(Optional.of(LOCALE));
        //WHEN
        Optional<String> result = underTest.getLocale(request);
        //THEN
        assertThat(result).contains(LOCALE);
    }

    @Test
    public void getBrowserLanguage_notFound() {
        //GIVEN
        given(request.getHeader(RequestConstants.HEADER_BROWSER_LANGUAGE)).willReturn(null);
        //WHEN
        Optional<String> result = underTest.getBrowserLanguage(request);
        //THEN
        assertThat(result).isEmpty();
    }

    @Test
    public void getBrowserLanguage_blank() {
        //GIVEN
        given(request.getHeader(RequestConstants.HEADER_BROWSER_LANGUAGE)).willReturn(" ");
        //WHEN
        Optional<String> result = underTest.getBrowserLanguage(request);
        //THEN
        assertThat(result).isEmpty();
    }

    @Test
    public void getBrowserLanguage() {
        //GIVEN
        given(request.getHeader(RequestConstants.HEADER_BROWSER_LANGUAGE)).willReturn(BROWSER_LANGUAGE);
        //WHEN
        Optional<String> result = underTest.getBrowserLanguage(request);
        //THEN
        assertThat(result).contains(BROWSER_LANGUAGE);
    }
}