package com.github.saphyra.skyxplore.platform;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StopWatch;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class MetricsFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        StopWatch stopwatch = new StopWatch(String.format("%s: %s", request.getMethod(), request.getRequestURI()));
        stopwatch.start();
        filterChain.doFilter(request, response);
        stopwatch.stop();
        log.warn("{}: {}ms", stopwatch.getId(), stopwatch.getLastTaskTimeMillis());
    }
}
