package com.github.saphyra.skyxplore.app.common.request_context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@RunWith(MockitoJUnitRunner.class)
public class RequestContextHolderTest {
    @InjectMocks
    private RequestContextHolder underTest;

    @Mock
    private RequestContext requestContext;

    @Test
    public void setGetClearContext() {
        //GIVEN
        underTest.setContext(requestContext);
        //WHEN
        RequestContext result = underTest.get();
        //THEN
        assertThat(result).isEqualTo(requestContext);

        //WHEN
        underTest.clear();
        //THEN
        Throwable ex = catchThrowable(() -> underTest.get());
        assertThat(ex).isInstanceOf(IllegalStateException.class);
    }
}