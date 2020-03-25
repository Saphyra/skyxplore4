package com.github.saphyra.skyxplore.app.service.game_creation.surface;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.app.domain.common.SurfaceType;
import com.github.saphyra.skyxplore.app.domain.coordinate.Coordinate;
import com.github.saphyra.skyxplore.app.domain.star.Star;
import com.github.saphyra.skyxplore.app.domain.surface.Surface;

@RunWith(MockitoJUnitRunner.class)
public class SurfaceMapperTest {
    private static final UUID STAR_ID = UUID.randomUUID();
    private static final UUID GAME_ID = UUID.randomUUID();
    private static final UUID PLAYER_ID = UUID.randomUUID();
    @Mock
    private SurfaceFactory surfaceFactory;

    @InjectMocks
    private SurfaceMapper underTest;

    @Mock
    private Star star;

    @Mock
    private Surface surface;

    @Test
    public void mapSurfaces() {
        SurfaceType[][] surfaceTypes = new SurfaceType[][]{
            new SurfaceType[]{SurfaceType.COAL_MINE}
        };
        given(star.getStarId()).willReturn(STAR_ID);
        given(star.getGameId()).willReturn(GAME_ID);
        given(star.getOwnerId()).willReturn(PLAYER_ID);
        given(surfaceFactory.create(STAR_ID, GAME_ID, PLAYER_ID, new Coordinate(0, 0), SurfaceType.COAL_MINE)).willReturn(surface);


        List<Surface> result = underTest.mapSurfaces(surfaceTypes, star);

        assertThat(result).containsExactly(surface);
    }

}