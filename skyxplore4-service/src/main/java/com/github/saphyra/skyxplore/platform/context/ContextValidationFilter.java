package com.github.saphyra.skyxplore.platform.context;

import com.github.saphyra.exceptionhandling.ErrorTranslationAdapter;
import com.github.saphyra.exceptionhandling.domain.ErrorResponse;
import com.github.saphyra.skyxplore.common.ErrorCode;
import com.github.saphyra.skyxplore.common.context.RequestContextHolder;
import com.github.saphyra.util.ObjectMapperWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class ContextValidationFilter extends OncePerRequestFilter {
    private final Optional<ErrorTranslationAdapter> errorTranslationAdapter;
    private final ObjectMapperWrapper objectMapperWrapper;
    private final RequestContextHolder requestContextHolder;
    private final RequestContextValidationService requestContextValidationService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            requestContextValidationService.validateContext(requestContextHolder.get());
            filterChain.doFilter(request, response);
        } catch (InvalidContextException e) {
            log.warn("Invalid RequestContext", e);
            sendErrorMessage(request, response, e);
        }
    }

    private void sendErrorMessage(HttpServletRequest request, HttpServletResponse response, InvalidContextException e) throws IOException {
        int responseStatus = HttpStatus.UNAUTHORIZED.value();
        String errorCode = ErrorCode.INVALID_REQUEST_CONTEXT.name();
        ErrorResponse errorResponse = getErrorResponse(request, responseStatus, errorCode, e);
        response.setStatus(responseStatus);
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write(objectMapperWrapper.writeValueAsString(errorResponse));
        writer.flush();
        writer.close();
    }

    private ErrorResponse getErrorResponse(HttpServletRequest request, int responseStatus, String errorCode, InvalidContextException e) {
        return ErrorResponse.builder()
            .httpStatus(responseStatus)
            .errorCode(errorCode)
            .localizedMessage(
                errorTranslationAdapter.map(adapter -> adapter.translateMessage(request, errorCode, new HashMap<>()))
                    .orElse(e.getMessage())
            )
            .params(new HashMap<>())
            .build();
    }
}
