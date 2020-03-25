package com.github.saphyra.skyxplore.app.service.game_creation.surface;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.app.domain.common.SurfaceType;
import com.github.saphyra.skyxplore.app.domain.coordinate.Coordinate;
import com.github.saphyra.skyxplore.app.domain.surface.Surface;
import com.github.saphyra.util.IdGenerator;

@RunWith(MockitoJUnitRunner.class)
public class SurfaceFactoryTest {
    private static final UUID SURFACE_ID = UUID.randomUUID();
    private static final UUID STAR_ID = UUID.randomUUID();
    private static final UUID GAME_ID = UUID.randomUUID();
    private static final UUID PLAYER_ID = UUID.randomUUID();
    @Mock
    private IdGenerator idGenerator;

    @InjectMocks
    private SurfaceFactory underTest;

    @Test
    public void create() {
        given(idGenerator.randomUUID()).willReturn(SURFACE_ID);
        Coordinate coordinate = new Coordinate(1, 1);

        Surface result = underTest.create(STAR_ID, GAME_ID, PLAYER_ID, coordinate, SurfaceType.COAL_MINE);

        assertThat(result.getSurfaceId()).isEqualTo(SURFACE_ID);
        assertThat(result.getGameId()).isEqualTo(GAME_ID);
        assertThat(result.getPlayerId()).isEqualTo(PLAYER_ID);
        assertThat(result.getSurfaceType()).isEqualTo(SurfaceType.COAL_MINE);
        assertThat(result.getCoordinate()).isEqualTo(coordinate);
        assertThat(result.isNew()).isTrue();
    }
}