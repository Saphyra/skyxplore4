package com.github.saphyra.skyxplore.platform.errorhandling;

import com.github.saphyra.authservice.auth.ErrorResponseResolver;
import com.github.saphyra.authservice.auth.domain.AuthContext;
import com.github.saphyra.authservice.auth.domain.RestErrorResponse;
import com.github.saphyra.exceptionhandling.domain.ErrorResponse;
import com.github.saphyra.skyxplore.common.PageController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ErrorResponseResolverImpl implements ErrorResponseResolver {
    private final ErrorResponseFactory errorResponseFactory;

    @Override
    public RestErrorResponse getRestErrorResponse(AuthContext authContext) {
        ErrorResponse errorResponse = errorResponseFactory.createErrorResponse(authContext.getRequest(), authContext.getAccessStatus());
        return new RestErrorResponse(HttpStatus.UNAUTHORIZED, errorResponse);
    }

    @Override
    public String getRedirectionPath(AuthContext authContext) {
        return PageController.INDEX_MAPPING;
    }
}
