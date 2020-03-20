package com.github.saphyra.skyxplore.app.rest.view.map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.app.domain.coordinate.Coordinate;
import com.github.saphyra.skyxplore.app.domain.star.Star;

@RunWith(MockitoJUnitRunner.class)
public class StarMapViewConverterTest {
    private static final UUID STAR_ID = UUID.randomUUID();
    private static final UUID PLAYER_ID = UUID.randomUUID();
    private static final String STAR_NAME = "star-name";
    @InjectMocks
    private StarMapViewConverter underTest;

    @Test
    public void convertDomain() {
        Coordinate coordinate = new Coordinate(1, 1);

        Star star = Star.builder()
            .gameId(UUID.randomUUID())
            .starId(STAR_ID)
            .starName(STAR_NAME)
            .coordinate(coordinate)
            .ownerId(PLAYER_ID)
            .build();

        StarMapView result = underTest.convertDomain(star);

        assertThat(result.getStarId()).isEqualTo(STAR_ID);
        assertThat(result.getStarName()).isEqualTo(STAR_NAME);
        assertThat(result.getCoordinate()).isEqualTo(coordinate);
        assertThat(result.getOwnerId()).isEqualTo(PLAYER_ID);
    }
}