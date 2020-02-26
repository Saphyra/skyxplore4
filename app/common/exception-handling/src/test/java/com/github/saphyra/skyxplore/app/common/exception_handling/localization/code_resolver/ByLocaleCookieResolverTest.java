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

import com.github.saphyra.skyxplore.app.common.config.RequestDataProvider;
import com.github.saphyra.skyxplore.app.common.exception_handling.localization.properties.ErrorCodeLocalization;
import com.github.saphyra.skyxplore.app.common.exception_handling.localization.properties.ErrorCodeService;

@RunWith(MockitoJUnitRunner.class)
public class ByLocaleCookieResolverTest {
    private static final String LOCALE_COOKIE = "locale-cookie";

    @Mock
    private  ErrorCodeService errorCodeService;

    @Mock
    private  RequestDataProvider requestDataProvider;

    @InjectMocks
    private ByLocaleCookieResolver underTest;

    @Mock
    private HttpServletRequest request;

    @Mock
    private ErrorCodeLocalization errorCodeLocalization;

    @Test
    public void localeCookieNotPresent(){
        //GIVEN
        given(requestDataProvider.getLocale(request)).willReturn(Optional.empty());
        //WHEN
        Optional<ErrorCodeLocalization> result = underTest.getByLocaleCookie(request);
        //THEN
        assertThat(result).isEmpty();
    }

    @Test
    public void localeCookieNotSupported(){
        //GIVEN
        given(requestDataProvider.getLocale(request)).willReturn(Optional.of(LOCALE_COOKIE));
        given(errorCodeService.getOptional(LOCALE_COOKIE)).willReturn(Optional.empty());
        //WHEN
        Optional<ErrorCodeLocalization> result = underTest.getByLocaleCookie(request);
        //THEN
        assertThat(result).isEmpty();
    }

    @Test
    public void getByLocaleCookie(){
        //GIVEN
        given(requestDataProvider.getLocale(request)).willReturn(Optional.of(LOCALE_COOKIE));
        given(errorCodeService.getOptional(LOCALE_COOKIE)).willReturn(Optional.of(errorCodeLocalization));
        //WHEN
        Optional<ErrorCodeLocalization> result = underTest.getByLocaleCookie(request);
        //THEN
        assertThat(result).contains(errorCodeLocalization);
    }
}