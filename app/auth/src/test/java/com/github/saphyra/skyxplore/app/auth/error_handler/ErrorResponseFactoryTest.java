package com.github.saphyra.skyxplore.app.auth.error_handler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

import com.github.saphyra.authservice.auth.domain.AccessStatus;
import com.github.saphyra.exceptionhandling.ErrorTranslationAdapter;
import com.github.saphyra.exceptionhandling.domain.ErrorResponse;
import com.github.saphyra.skyxplore.app.common.exception_handling.ErrorCode;

@RunWith(MockitoJUnitRunner.class)
public class ErrorResponseFactoryTest {
    private static final String LOCALIZED_MESSAGE = "localized-message";

    @Mock
    private ErrorCodeResolver errorCodeResolver;

    @Mock
    private ErrorTranslationAdapter errorTranslationAdapter;

    @InjectMocks
    private ErrorResponseFactory underTest;

    @Mock
    private HttpServletRequest request;

    @Test
    public void createErrorResponse() {
        //GIVEN
        given(errorCodeResolver.getErrorCode(AccessStatus.LOGIN_FAILED)).willReturn(ErrorCode.BAD_CREDENTIALS);
        given(errorTranslationAdapter.translateMessage(eq(request), eq(ErrorCode.BAD_CREDENTIALS.name()), anyMap())).willReturn(LOCALIZED_MESSAGE);
        //WHEN
        ErrorResponse result = underTest.createErrorResponse(request, AccessStatus.LOGIN_FAILED);
        //THEN
        assertThat(result.getHttpStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        assertThat(result.getErrorCode()).isEqualTo(ErrorCode.BAD_CREDENTIALS.name());
        assertThat(result.getLocalizedMessage()).isEqualTo(LOCALIZED_MESSAGE);
        assertThat(result.getParams().get("generalErrorKey")).isEqualTo("Unknown accessStatus: LOGIN_FAILED");
    }
}