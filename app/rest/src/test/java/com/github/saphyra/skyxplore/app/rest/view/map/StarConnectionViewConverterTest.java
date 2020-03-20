package com.github.saphyra.skyxplore.app.rest.view.map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.app.domain.coordinate.Coordinate;
import com.github.saphyra.skyxplore.app.domain.star.StarQueryService;
import com.github.saphyra.skyxplore.app.domain.star_connection.StarConnection;

@RunWith(MockitoJUnitRunner.class)
public class StarConnectionViewConverterTest {
    private static final UUID STAR_ID_1 = UUID.randomUUID();
    private static final UUID STAR_ID_2 = UUID.randomUUID();

    @Mock
    private StarQueryService starQueryService;

    @InjectMocks
    private StarConnectionViewConverter underTest;

    @Test
    public void convertDomain() {
        StarConnection starConnection = StarConnection.builder()
            .connectionId(UUID.randomUUID())
            .gameId(UUID.randomUUID())
            .star1(STAR_ID_1)
            .star2(STAR_ID_2)
            .build();

        Coordinate coordinate1 = new Coordinate(1, 1);
        Coordinate coordinate2 = new Coordinate(2, 2);

        given(starQueryService.getCoordinateOfStar(STAR_ID_1)).willReturn(coordinate1);
        given(starQueryService.getCoordinateOfStar(STAR_ID_2)).willReturn(coordinate2);

        StarConnectionView result = underTest.convertDomain(starConnection);

        assertThat(result.getCoordinate1()).isEqualTo(coordinate1);
        assertThat(result.getCoordinate2()).isEqualTo(coordinate2);
    }
}