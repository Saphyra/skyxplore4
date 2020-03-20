package com.github.saphyra.skyxplore.app.domain.star_connection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.app.common.request_context.RequestContext;
import com.github.saphyra.skyxplore.app.common.request_context.RequestContextHolder;

@RunWith(MockitoJUnitRunner.class)
public class StarConnectionQueryServiceTest {
    private static final UUID GAME_ID = UUID.randomUUID();

    @Mock
    private RequestContextHolder requestContextHolder;

    @Mock
    private StarConnectionDao starConnectionDao;

    @InjectMocks
    private StarConnectionQueryService underTest;

    @Mock
    private RequestContext requestContext;

    @Mock
    private StarConnection starConnection;

    @Test
    public void getByGameId() {
        given(requestContextHolder.get()).willReturn(requestContext);
        given(requestContext.getGameId()).willReturn(GAME_ID);
        given(starConnectionDao.getByGameId(GAME_ID)).willReturn(Arrays.asList(starConnection));

        List<StarConnection> result = underTest.getByGameId();

        assertThat(result).containsExactly(starConnection);
    }
}