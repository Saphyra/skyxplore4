package com.github.saphyra.skyxplore.app.auth.error_handler;

import com.github.saphyra.authservice.auth.domain.AccessStatus;
import com.github.saphyra.authservice.auth.domain.AuthContext;
import com.github.saphyra.authservice.auth.domain.RestErrorResponse;
import com.github.saphyra.exceptionhandling.domain.ErrorResponse;
import com.github.saphyra.skyxplore.common.config.RequestConstants;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class ErrorResponseResolverImplTest {
    @Mock
    private ErrorResponseFactory errorResponseFactory;

    @InjectMocks
    private ErrorResponseResolverImpl underTest;

    @Mock
    private AuthContext authContext;

    @Mock
    private HttpServletRequest request;

    @Mock
    private ErrorResponse errorResponse;

    @Test
    public void getRestErrorResponse() {
        //GIVEN
        given(authContext.getAccessStatus()).willReturn(AccessStatus.LOGIN_FAILED);
        given(authContext.getRequest()).willReturn(request);
        given(errorResponseFactory.createErrorResponse(request, AccessStatus.LOGIN_FAILED)).willReturn(errorResponse);
        given(errorResponse.getHttpStatus()).willReturn(HttpStatus.UNAUTHORIZED.value());
        //WHEN
        RestErrorResponse result = underTest.getRestErrorResponse(authContext);
        //THEN
        assertThat(result.getHttpStatus()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(result.getResponseBody()).isEqualTo(errorResponse);
    }

    @Test
    public void getRedirectionPath() {
        //WHEN
        String result = underTest.getRedirectionPath(authContext);
        //THEN
        assertThat(result).isEqualTo(RequestConstants.INDEX_MAPPING);
    }
}