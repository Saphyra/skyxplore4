package com.github.saphyra.skyxplore.app.common.exception_handling.localization;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.app.common.exception_handling.localization.code_resolver.ErrorCodeLocalizationResolver;
import com.github.saphyra.skyxplore.app.common.exception_handling.localization.properties.ErrorCodeLocalization;

@RunWith(MockitoJUnitRunner.class)
public class ErrorMessageResolverTest {
    private static final String ERROR_CODE = "error-code";
    private static final String ERROR_MESSAGE = "error-message";

    @Mock
    private ErrorCodeLocalizationResolver errorCodeLocalizationResolver;

    @InjectMocks
    private ErrorMessageResolver underTest;

    @Mock
    private HttpServletRequest request;

    @Mock
    private ErrorCodeLocalization errorCodeLocalization;

    @Test
    public void localizationNotFound() {
        //GIVEN
        given(errorCodeLocalizationResolver.getErrorCodeLocalization(request)).willReturn(Optional.empty());
        //WHEN
        String errorMessage = underTest.getErrorMessage(request, ERROR_CODE);
        //THEN
        assertThat(errorMessage).startsWith("Could not translate errorCode");
        assertThat(errorMessage).endsWith(ERROR_CODE);
    }

    @Test
    public void errorCodeNotTranslated() {
        //GIVEN
        given(errorCodeLocalizationResolver.getErrorCodeLocalization(request)).willReturn(Optional.of(errorCodeLocalization));
        given(errorCodeLocalization.getOptional(ERROR_CODE)).willReturn(Optional.empty());
        //WHEN
        String errorMessage = underTest.getErrorMessage(request, ERROR_CODE);
        //THEN
        assertThat(errorMessage).startsWith("Could not translate errorCode");
        assertThat(errorMessage).endsWith(ERROR_CODE);
    }

    @Test
    public void getErrorMessage() {
        //GIVEN
        given(errorCodeLocalizationResolver.getErrorCodeLocalization(request)).willReturn(Optional.of(errorCodeLocalization));
        given(errorCodeLocalization.getOptional(ERROR_CODE)).willReturn(Optional.of(ERROR_MESSAGE));
        //WHEN
        String errorMessage = underTest.getErrorMessage(request, ERROR_CODE);
        //THEN
        assertThat(errorMessage).isEqualTo(ERROR_MESSAGE);
    }
}