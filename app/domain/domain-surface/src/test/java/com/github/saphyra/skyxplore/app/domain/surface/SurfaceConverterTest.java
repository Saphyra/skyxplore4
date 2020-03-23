package com.github.saphyra.skyxplore.app.domain.surface;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.app.common.utils.UuidConverter;
import com.github.saphyra.skyxplore.app.domain.coordinate.Coordinate;
import com.github.saphyra.skyxplore.app.domain.coordinate.CoordinateConverter;
import com.github.saphyra.skyxplore.app.domain.coordinate.CoordinateEntity;

@RunWith(MockitoJUnitRunner.class)
public class SurfaceConverterTest {
    private static final String SURFACE_ID_STRING = "surface-id";
    private static final String STAR_ID_STRING = "star-id";
    private static final String GAME_ID_STRING = "game-id";
    private static final String PLAYER_ID_STRING = "player-id";
    private static final UUID SURFACE_ID = UUID.randomUUID();
    private static final UUID STAR_ID = UUID.randomUUID();
    private static final UUID GAME_ID = UUID.randomUUID();
    private static final UUID PLAYER_ID = UUID.randomUUID();

    @Mock
    private CoordinateConverter coordinateConverter;

    @Mock
    private UuidConverter uuidConverter;

    @InjectMocks
    private SurfaceConverter underTest;

    @Mock
    private Coordinate coordinate;

    @Mock
    private CoordinateEntity coordinateEntity;

    @Test
    public void convertEntity() {
        given(coordinateConverter.convertEntity(coordinateEntity)).willReturn(coordinate);
        given(uuidConverter.convertEntity(SURFACE_ID_STRING)).willReturn(SURFACE_ID);
        given(uuidConverter.convertEntity(STAR_ID_STRING)).willReturn(STAR_ID);
        given(uuidConverter.convertEntity(GAME_ID_STRING)).willReturn(GAME_ID);
        given(uuidConverter.convertEntity(PLAYER_ID_STRING)).willReturn(PLAYER_ID);

        SurfaceEntity entity = SurfaceEntity.builder()
            .surfaceId(SURFACE_ID_STRING)
            .starId(STAR_ID_STRING)
            .gameId(GAME_ID_STRING)
            .playerId(PLAYER_ID_STRING)
            .coordinate(coordinateEntity)
            .surfaceType(SurfaceType.COAL_MINE)
            .isNew(true)
            .build();

        Surface result = underTest.convertEntity(entity);

        assertThat(result.getSurfaceId()).isEqualTo(SURFACE_ID);
        assertThat(result.getStarId()).isEqualTo(STAR_ID);
        assertThat(result.getGameId()).isEqualTo(GAME_ID);
        assertThat(result.getPlayerId()).isEqualTo(PLAYER_ID);
        assertThat(result.getCoordinate()).isEqualTo(coordinate);
        assertThat(result.getSurfaceType()).isEqualTo(SurfaceType.COAL_MINE);
        assertThat(result.isNew()).isFalse();
    }

    @Test
    public void convertDomain() {
        given(coordinateConverter.convertDomain(coordinate)).willReturn(coordinateEntity);
        given(uuidConverter.convertDomain(SURFACE_ID)).willReturn(SURFACE_ID_STRING);
        given(uuidConverter.convertDomain(STAR_ID)).willReturn(STAR_ID_STRING);
        given(uuidConverter.convertDomain(GAME_ID)).willReturn(GAME_ID_STRING);
        given(uuidConverter.convertDomain(PLAYER_ID)).willReturn(PLAYER_ID_STRING);

        Surface domain = Surface.builder()
            .surfaceId(SURFACE_ID)
            .starId(STAR_ID)
            .gameId(GAME_ID)
            .playerId(PLAYER_ID)
            .coordinate(coordinate)
            .surfaceType(SurfaceType.COAL_MINE)
            .isNew(true)
            .build();

        SurfaceEntity result = underTest.convertDomain(domain);

        assertThat(result.getSurfaceId()).isEqualTo(SURFACE_ID_STRING);
        assertThat(result.getStarId()).isEqualTo(STAR_ID_STRING);
        assertThat(result.getGameId()).isEqualTo(GAME_ID_STRING);
        assertThat(result.getPlayerId()).isEqualTo(PLAYER_ID_STRING);
        assertThat(result.getCoordinate()).isEqualTo(coordinateEntity);
        assertThat(result.getSurfaceType()).isEqualTo(SurfaceType.COAL_MINE);
        assertThat(result.isNew()).isTrue();
    }
}