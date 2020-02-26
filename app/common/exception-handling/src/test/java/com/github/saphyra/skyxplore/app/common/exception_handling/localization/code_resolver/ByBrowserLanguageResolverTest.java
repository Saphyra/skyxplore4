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
public class ByBrowserLanguageResolverTest {
    private static final String BROWSER_LANGUAGE = "browser-language";

    @Mock
    private ErrorCodeService errorCodeService;

    @Mock
    private RequestDataProvider requestDataProvider;

    @Mock
    private ErrorCodeLocalization errorCodeLocalization;

    @InjectMocks
    private ByBrowserLanguageResolver underTest;

    @Mock
    private HttpServletRequest request;

    @Test
    public void browserLanguageNotPresent() {
        //GIVEN
        given(requestDataProvider.getBrowserLanguage(request)).willReturn(Optional.empty());
        //WHEN
        Optional<ErrorCodeLocalization> result = underTest.getByBrowserLanguage(request);
        //THEN
        assertThat(result).isEmpty();
    }

    @Test
    public void browserLanguageNotSupported() {
        //GIVEN
        given(requestDataProvider.getBrowserLanguage(request)).willReturn(Optional.of(BROWSER_LANGUAGE));
        given(errorCodeService.getOptional(BROWSER_LANGUAGE)).willReturn(Optional.empty());
        //WHEN
        Optional<ErrorCodeLocalization> result = underTest.getByBrowserLanguage(request);
        //THEN
        assertThat(result).isEmpty();
    }

    @Test
    public void getByBrowserLanguage() {
        //GIVEN
        given(requestDataProvider.getBrowserLanguage(request)).willReturn(Optional.of(BROWSER_LANGUAGE));
        given(errorCodeService.getOptional(BROWSER_LANGUAGE)).willReturn(Optional.of(errorCodeLocalization));
        //WHEN
        Optional<ErrorCodeLocalization> result = underTest.getByBrowserLanguage(request);
        //THEN
        assertThat(result).contains(errorCodeLocalization);
    }
}