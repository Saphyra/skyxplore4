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

import com.github.saphyra.skyxplore.app.domain.star_connection.StarConnection;
import com.github.saphyra.skyxplore.app.domain.star_connection.StarConnectionQueryService;

@RunWith(MockitoJUnitRunner.class)
public class VisibleStarConnectionQueryServiceTest {
    private static final UUID STAR_ID = UUID.randomUUID();

    @Mock
    private StarConnectionQueryService starConnectionQueryService;

    @InjectMocks
    private VisibleStarConnectionQueryService underTest;

    @Mock
    private StarConnection connection1;

    @Mock
    private StarConnection connection2;

    @Test
    public void getVisibleByStars() {
        given(starConnectionQueryService.getByGameId()).willReturn(Arrays.asList(connection1, connection2));
        given(connection1.getStar1()).willReturn(STAR_ID);

        List<StarConnection> result = underTest.getVisibleByStars(Arrays.asList(STAR_ID));

        assertThat(result).containsExactly(connection1);
    }
}