package com.github.saphyra.skyxplore.app.common.exception_handling.localization;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ErrorTranslationAdapterImplTest {
    private static final String ERROR_CODE = "error-code";
    private static final String ERROR_MESSAGE = "error-message";
    private static final Map<String, String> PARAMS = new HashMap<>();
    private static final String PROCESSED_ERROR_MESSAGE = "processed-error-message";

    @Mock
    private ErrorMessageResolver errorMessageResolver;

    @Mock
    private ParameterInserter parameterInserter;

    @InjectMocks
    private ErrorTranslationAdapterImpl underTest;

    @Mock
    private HttpServletRequest request;

    @Test
    public void translateMessage() {
        given(errorMessageResolver.getErrorMessage(request, ERROR_CODE)).willReturn(ERROR_MESSAGE);
        given(parameterInserter.insertParams(ERROR_MESSAGE, PARAMS)).willReturn(PROCESSED_ERROR_MESSAGE);

        String result = underTest.translateMessage(request, ERROR_CODE, PARAMS);

        assertThat(result).isEqualTo(PROCESSED_ERROR_MESSAGE);
    }
}