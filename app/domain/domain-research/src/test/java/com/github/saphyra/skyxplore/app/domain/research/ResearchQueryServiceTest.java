package com.github.saphyra.skyxplore.app.domain.research;

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
public class ResearchQueryServiceTest {
    private static final UUID PLAYER_ID = UUID.randomUUID();
    private static final UUID STAR_ID = UUID.randomUUID();

    @Mock
    private RequestContextHolder requestContextHolder;

    @Mock
    private ResearchDao researchDao;

    @InjectMocks
    private ResearchQueryService underTest;

    @Mock
    private RequestContext requestContext;

    @Mock
    private Research research;

    @Test
    public void getByStarIdAndPlayerId() {
        given(requestContextHolder.get()).willReturn(requestContext);
        given(requestContext.getPlayerId()).willReturn(PLAYER_ID);
        given(researchDao.getByStarIdAndPlayerId(STAR_ID, PLAYER_ID)).willReturn(Arrays.asList(research));

        List<Research> result = underTest.getByStarIdAndPlayerId(STAR_ID);

        assertThat(result).containsExactly(research);
    }
}