package com.github.saphyra.skyxplore.app.common.exception_handling.localization.code_resolver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.app.common.config.RequestConstants;
import com.github.saphyra.skyxplore.app.common.exception_handling.localization.properties.ErrorCodeLocalization;
import com.github.saphyra.skyxplore.app.common.exception_handling.localization.properties.ErrorCodeService;

@RunWith(MockitoJUnitRunner.class)
public class ErrorCodeLocalizationResolverTest {
    @Mock
    private ByBrowserLanguageResolver byBrowserLanguageResolver;

    @Mock
    private ByLocaleCookieResolver byLocaleCookieResolver;

    @Mock
    private ByUidCookieResolver byUidCookieResolver;

    @Mock
    private ErrorCodeService errorCodeService;

    @InjectMocks
    private ErrorCodeLocalizationResolver underTest;

    @Mock
    private HttpServletRequest request;

    @Mock
    private ErrorCodeLocalization errorCodeLocalization;

    @Test
    public void getByUid() {
        //GIVEN
        given(byUidCookieResolver.getByUid(request)).willReturn(Optional.of(errorCodeLocalization));
        //WHEN
        Optional<ErrorCodeLocalization> result = underTest.getErrorCodeLocalization(request);
        //THEN
        assertThat(result).contains(errorCodeLocalization);
    }

    @Test
    public void getByLocaleCookie() {
        //GIVEN
        given(byUidCookieResolver.getByUid(request)).willReturn(Optional.empty());
        given(byLocaleCookieResolver.getByLocaleCookie(request)).willReturn(Optional.of(errorCodeLocalization));
        //WHEN
        Optional<ErrorCodeLocalization> result = underTest.getErrorCodeLocalization(request);
        //THEN
        assertThat(result).contains(errorCodeLocalization);
    }

    @Test
    public void getByBrowserLanguage() {
        //GIVEN
        given(byUidCookieResolver.getByUid(request)).willReturn(Optional.empty());
        given(byLocaleCookieResolver.getByLocaleCookie(request)).willReturn(Optional.empty());
        given(byBrowserLanguageResolver.getByBrowserLanguage(request)).willReturn(Optional.of(errorCodeLocalization));
        //WHEN
        Optional<ErrorCodeLocalization> result = underTest.getErrorCodeLocalization(request);
        //THEN
        assertThat(result).contains(errorCodeLocalization);
    }

    @Test
    public void getDefault() {
        //GIVEN
        given(byUidCookieResolver.getByUid(request)).willReturn(Optional.empty());
        given(byLocaleCookieResolver.getByLocaleCookie(request)).willReturn(Optional.empty());
        given(byBrowserLanguageResolver.getByBrowserLanguage(request)).willReturn(Optional.empty());
        given(errorCodeService.getOptional(RequestConstants.DEFAULT_LOCALE)).willReturn(Optional.of(errorCodeLocalization));
        //WHEN
        Optional<ErrorCodeLocalization> result = underTest.getErrorCodeLocalization(request);
        //THEN
        assertThat(result).contains(errorCodeLocalization);
    }

    @Test
    public void notFound() {
        //GIVEN
        given(byUidCookieResolver.getByUid(request)).willReturn(Optional.empty());
        given(byLocaleCookieResolver.getByLocaleCookie(request)).willReturn(Optional.empty());
        given(byBrowserLanguageResolver.getByBrowserLanguage(request)).willReturn(Optional.empty());
        given(errorCodeService.getOptional(RequestConstants.DEFAULT_LOCALE)).willReturn(Optional.empty());
        //WHEN
        Optional<ErrorCodeLocalization> result = underTest.getErrorCodeLocalization(request);
        //THEN
        assertThat(result).isEmpty();
    }
}