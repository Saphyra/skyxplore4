package com.github.saphyra.skyxplore_deprecated.platform.metrics;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class RestMetricsFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        StopWatch stopwatch = new StopWatch(String.format("%s: %s", request.getMethod(), request.getRequestURI()));
        stopwatch.start();
        filterChain.doFilter(request, response);
        stopwatch.stop();
        log.info("{}: {}ms", stopwatch.getId(), stopwatch.getTotalTimeMillis());
    }
}
