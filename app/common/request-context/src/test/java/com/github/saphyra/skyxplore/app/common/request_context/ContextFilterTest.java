package com.github.saphyra.skyxplore.app.common.request_context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ContextFilterTest {
    @Mock
    private RequestContextFactory requestContextFactory;

    @Mock
    private RequestContextHolder requestContextHolder;

    @InjectMocks
    private ContextFilter underTest;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private RequestContext requestContext;

    @Test
    public void filter_setContext_clearContextWhenException() throws ServletException, IOException {
        //GIVEN
        given(requestContextFactory.createContext(request)).willReturn(requestContext);
        doThrow(new RuntimeException()).when(filterChain).doFilter(request, response);
        //WHEN
        Throwable ex = catchThrowable(() -> underTest.doFilterInternal(request, response, filterChain));
        //THEN
        verify(requestContextHolder).setContext(requestContext);
        verify(requestContextHolder).clear();
    }
}