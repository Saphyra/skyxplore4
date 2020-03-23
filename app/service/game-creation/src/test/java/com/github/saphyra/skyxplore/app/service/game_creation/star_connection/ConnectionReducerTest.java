package com.github.saphyra.skyxplore.app.service.game_creation.star_connection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.app.domain.coordinate.Coordinate;
import com.github.saphyra.skyxplore.app.domain.star.Star;
import com.github.saphyra.skyxplore.app.domain.star_connection.StarConnection;
import com.github.saphyra.skyxplore.app.service.common.DistanceCalculator;

@RunWith(MockitoJUnitRunner.class)
public class ConnectionReducerTest {
    private static final UUID STAR_ID_1 = UUID.randomUUID();
    private static final UUID STAR_ID_2 = UUID.randomUUID();
    private static final UUID STAR_ID_3 = UUID.randomUUID();
    private static final UUID STAR_ID_4 = UUID.randomUUID();

    @Mock
    private DistanceCalculator distanceCalculator;

    @Mock
    private StarConnectionCreationConfiguration configuration;

    @InjectMocks
    private ConnectionReducer underTest;

    @Test
    public void removeConnections() {
        Star star1 = createStar(STAR_ID_1, 0, 0);
        Star star2 = createStar(STAR_ID_2, 0, 5);
        Star star3 = createStar(STAR_ID_3, 5, 0);
        Star star4 = createStar(STAR_ID_4, 5, 5);

        StarConnection connection12 = createConnection(star1, star2, "12");
        StarConnection connection13 = createConnection(star1, star3, "13");
        StarConnection connection14 = createConnection(star1, star4, "14");
        StarConnection connection23 = createConnection(star2, star3, "23");
        StarConnection connection24 = createConnection(star2, star4, "24");
        StarConnection connection34 = createConnection(star3, star4, "34");

        given(distanceCalculator.getDistance(any(), any())).willCallRealMethod();
        given(configuration.getMaxNumberOfConnections()).willReturn(2);

        List<StarConnection> result = underTest.removeConnections(
            new ArrayList<>(Arrays.asList(connection12, connection13, connection14, connection23, connection24, connection34)),
            Arrays.asList(star1, star2, star3, star4)
        );

        assertThat(result).containsExactlyInAnyOrder(connection12, connection13, connection24, connection34);
    }

    private StarConnection createConnection(Star star1, Star star2, String connectionName) {
        UUID starId1 = star1.getStarId();
        UUID starId2 = star2.getStarId();

        StarConnection connection = Mockito.mock(StarConnection.class);

        given(connection.getStar1()).willReturn(starId1);
        given(connection.getStar2()).willReturn(starId2);
        given(connection.toString()).willReturn("Connection" + connectionName);

        return connection;
    }

    private Star createStar(UUID starId, int x, int y) {
        Star star = Mockito.mock(Star.class);
        given(star.getStarId()).willReturn(starId);
        given(star.getCoordinate()).willReturn(new Coordinate(x, y));
        return star;
    }
}