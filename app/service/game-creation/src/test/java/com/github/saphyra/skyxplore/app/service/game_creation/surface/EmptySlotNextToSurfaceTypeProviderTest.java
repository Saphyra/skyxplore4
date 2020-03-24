package com.github.saphyra.skyxplore.app.service.game_creation.surface;

import com.github.saphyra.skyxplore.app.domain.coordinate.Coordinate;
import com.github.saphyra.skyxplore.app.domain.surface.SurfaceType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class EmptySlotNextToSurfaceTypeProviderTest {
    @InjectMocks
    private EmptySlotNextToSurfaceTypeProvider underTest;

    @Test
    public void getEmptySlotsNextToSurfaceType() {
        SurfaceType[][] surfaceTypes = new SurfaceType[][]{
            new SurfaceType[]{SurfaceType.COAL_MINE, null, SurfaceType.COAL_MINE},
            new SurfaceType[]{SurfaceType.COAL_MINE, SurfaceType.CONCRETE, null},
            new SurfaceType[]{null, null, null}
        };

        List<Coordinate> result = underTest.getEmptySlotsNextToSurfaceType(surfaceTypes, SurfaceType.COAL_MINE);

        assertThat(result).containsExactlyInAnyOrder(
            new Coordinate(0, 1),
            new Coordinate(2, 0),
            new Coordinate(1, 2)
        );
    }
}