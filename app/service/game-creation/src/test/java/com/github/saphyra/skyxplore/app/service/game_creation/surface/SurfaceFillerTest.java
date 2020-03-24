package com.github.saphyra.skyxplore.app.service.game_creation.surface;

import com.github.saphyra.skyxplore.app.domain.coordinate.Coordinate;
import com.github.saphyra.skyxplore.app.domain.surface.SurfaceType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class SurfaceFillerTest {
    @Mock
    private RandomEmptySlotNextToSurfaceTypeProvider randomEmptySlotNextToSurfaceTypeProvider;

    @Mock
    private RandomEmptySlotProvider randomEmptySlotProvider;

    @InjectMocks
    private SurfaceFiller underTest;

    @Test
    public void fillBlockWithSurfaceType_initialPlacement() {
        SurfaceType[][] surfaceTypes = new SurfaceType[1][1];
        given(randomEmptySlotProvider.getRandomEmptySlot(surfaceTypes)).willReturn(Optional.of(new Coordinate(0, 0)));

        underTest.fillBlockWithSurfaceType(surfaceTypes, SurfaceType.COAL_MINE, true);

        assertThat(surfaceTypes[0][0]).isEqualTo(SurfaceType.COAL_MINE);
    }

    @Test
    public void fillBlockWithSurfaceType() {
        SurfaceType[][] surfaceTypes = new SurfaceType[1][1];
        given(randomEmptySlotNextToSurfaceTypeProvider.getRandomEmptySlotNextToSurfaceType(surfaceTypes, SurfaceType.COAL_MINE)).willReturn(Optional.of(new Coordinate(0, 0)));

        underTest.fillBlockWithSurfaceType(surfaceTypes, SurfaceType.COAL_MINE, false);

        assertThat(surfaceTypes[0][0]).isEqualTo(SurfaceType.COAL_MINE);
    }
}