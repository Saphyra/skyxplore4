package com.github.saphyra.skyxplore.app.service.query;

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
import com.github.saphyra.skyxplore.app.domain.star.Star;
import com.github.saphyra.skyxplore.app.domain.star.StarQueryService;

@RunWith(MockitoJUnitRunner.class)
public class VisibleStarQueryServiceTest {
    private static final UUID PLAYER_ID = UUID.randomUUID();

    @Mock
    private RequestContextHolder requestContextHolder;

    @Mock
    private StarQueryService starQueryService;

    @InjectMocks
    private VisibleStarQueryService underTest;

    @Mock
    private RequestContext requestContext;

    @Mock
    private Star star1;

    @Mock
    private Star star2;

    @Test
    public void getVisibleStars() {
        given(requestContextHolder.get()).willReturn(requestContext);
        given(requestContext.getPlayerId()).willReturn(PLAYER_ID);
        given(star1.getOwnerId()).willReturn(PLAYER_ID);

        given(starQueryService.getByOwnerId()).willReturn(Arrays.asList(star1, star2));

        List<Star> result = underTest.getVisibleStars();

        assertThat(result).containsExactly(star1);
    }
}