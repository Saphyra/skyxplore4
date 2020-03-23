package com.github.saphyra.skyxplore.app.service.game_creation.star_connection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.app.domain.coordinate.Coordinate;
import com.github.saphyra.skyxplore.app.domain.star.Star;
import com.github.saphyra.skyxplore.app.domain.star_connection.StarConnection;
import com.github.saphyra.skyxplore.app.service.common.DistanceCalculator;

@RunWith(MockitoJUnitRunner.class)
public class CloseStarsConnectionCreationServiceTest {
    private static final UUID STAR_ID_1 = UUID.randomUUID();
    private static final UUID STAR_ID_2 = UUID.randomUUID();

    @Mock
    private StarConnectionCreationConfiguration configuration;

    @Mock
    private DistanceCalculator distanceCalculator;

    @Mock
    private StarConnectionFactory starConnectionFactory;

    @InjectMocks
    private CloseStarsConnectionCreationService underTest;

    @Mock
    private Star closeStar1;

    @Mock
    private Star closeStar2;

    @Mock
    private Star distantStar;

    @Test
    public void connectCloseStars() {
        StarConnection starConnection = StarConnection.builder()
            .connectionId(UUID.randomUUID())
            .gameId(UUID.randomUUID())
            .star1(STAR_ID_1)
            .star2(STAR_ID_2)
            .build();

        given(closeStar1.getStarId()).willReturn(STAR_ID_1);
        given(closeStar2.getStarId()).willReturn(STAR_ID_2);

        Coordinate coordinateCloseStar1 = new Coordinate(1, 1);
        Coordinate coordinateCloseStar2 = new Coordinate(2, 2);
        given(closeStar1.getCoordinate()).willReturn(coordinateCloseStar1);
        given(closeStar2.getCoordinate()).willReturn(coordinateCloseStar2);

        Coordinate coordinateDistantStar = new Coordinate(100, 100);
        given(distantStar.getCoordinate()).willReturn(coordinateDistantStar);

        given(configuration.getMaxDistance()).willReturn(5);
        given(distanceCalculator.getDistance(any(), any())).willCallRealMethod();

        given(starConnectionFactory.createConnection(closeStar1, closeStar2)).willReturn(starConnection);

        List<StarConnection> result = underTest.connectCloseStars(Arrays.asList(closeStar1, closeStar2, distantStar));

        assertThat(result).containsExactly(starConnection);
    }
}