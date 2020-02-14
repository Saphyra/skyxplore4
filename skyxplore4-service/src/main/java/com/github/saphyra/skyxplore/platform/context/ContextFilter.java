package com.github.saphyra.skyxplore.platform.context;

import com.github.saphyra.skyxplore.common.context.RequestContext;
import com.github.saphyra.skyxplore.common.context.RequestContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RequiredArgsConstructor
@Component
@Slf4j
public class ContextFilter extends OncePerRequestFilter {
    private final RequestContextFactory requestContextFactory;
    private final RequestContextHolder requestContextHolder;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            try {
                RequestContext context = requestContextFactory.createContext(request);
                requestContextHolder.setContext(context);
                log.info("{} - {}, {}", request.getMethod(), request.getRequestURI(), context.toString());
            } catch (Throwable ex) {
                log.warn("Error occurred during RequestContext creation", ex);
            }
            filterChain.doFilter(request, response);
        } finally {
            requestContextHolder.clear();
        }
    }


}
