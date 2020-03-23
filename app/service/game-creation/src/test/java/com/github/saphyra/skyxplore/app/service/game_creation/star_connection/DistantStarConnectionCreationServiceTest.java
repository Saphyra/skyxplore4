package com.github.saphyra.skyxplore.app.service.game_creation.star_connection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.app.domain.star.Star;
import com.github.saphyra.skyxplore.app.domain.star_connection.StarConnection;

@RunWith(MockitoJUnitRunner.class)
public class DistantStarConnectionCreationServiceTest {
    private static final UUID STAR_ID_1 = UUID.randomUUID();
    private static final UUID STAR_ID_2 = UUID.randomUUID();
    private static final UUID STAR_ID_3 = UUID.randomUUID();

    @Mock
    private ClosestStarFinder closestStarFinder;

    @Mock
    private StarConnectionFactory starConnectionFactory;

    @InjectMocks
    private DistantStarConnectionCreationService underTest;

    @Mock
    private Star starWithConnection;

    @Mock
    private Star starWithNoConnections;

    @Mock
    private Star closestStar;

    @Mock
    private StarConnection newConnection;

    @Test
    public void onlyOneStar() {
        List<StarConnection> result = underTest.connectDistantStars(Collections.emptyList(), Collections.emptyList());

        assertThat(result).isEmpty();
        verifyZeroInteractions(starConnectionFactory);
    }

    @Test
    public void connectDistantStars() {
        given(starWithConnection.getStarId()).willReturn(STAR_ID_1);
        given(closestStar.getStarId()).willReturn(STAR_ID_2);
        given(starWithNoConnections.getStarId()).willReturn(STAR_ID_3);

        StarConnection connection = StarConnection.builder()
            .connectionId(UUID.randomUUID())
            .gameId(UUID.randomUUID())
            .star1(STAR_ID_1)
            .star2(STAR_ID_2)
            .build();
        given(closestStarFinder.getClosestStar(eq(starWithNoConnections), anyList())).willReturn(closestStar);
        given(starConnectionFactory.createConnection(starWithNoConnections, closestStar)).willReturn(newConnection);

        List<StarConnection> result = underTest.connectDistantStars(Arrays.asList(starWithConnection, starWithNoConnections, closestStar), Arrays.asList(connection));

        verify(closestStarFinder).getClosestStar(eq(starWithNoConnections), anyList());
        verify(starConnectionFactory).createConnection(starWithNoConnections, closestStar);
        assertThat(result).containsExactlyInAnyOrder(connection, newConnection);
    }
}