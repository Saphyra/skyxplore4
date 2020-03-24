package com.github.saphyra.skyxplore.app.service.game_creation.surface;

import com.github.saphyra.skyxplore.app.domain.coordinate.Coordinate;
import com.github.saphyra.skyxplore.app.domain.surface.SurfaceType;
import com.github.saphyra.util.Random;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class RandomEmptySlotNextToSurfaceTypeProviderTest {
    @Mock
    private EmptySlotNextToSurfaceTypeProvider emptySlotNextToSurfaceTypeProvider;

    @Mock
    private Random random;

    @InjectMocks
    private RandomEmptySlotNextToSurfaceTypeProvider underTest;

    @Test
    public void getRandomEmptySlotNextToSurfaceType_notFound() {
        SurfaceType[][] surfaceTypes = new SurfaceType[0][0];
        given(emptySlotNextToSurfaceTypeProvider.getEmptySlotsNextToSurfaceType(surfaceTypes, SurfaceType.COAL_MINE)).willReturn(Collections.emptyList());

        Optional<Coordinate> result = underTest.getRandomEmptySlotNextToSurfaceType(surfaceTypes, SurfaceType.COAL_MINE);

        assertThat(result).isEmpty();
    }

    @Test
    public void getRandomEmptySlotNextToSurfaceType() {
        SurfaceType[][] surfaceTypes = new SurfaceType[0][0];
        Coordinate coordinate1 = new Coordinate(1, 1);
        Coordinate coordinate2 = new Coordinate(1, 2);
        given(emptySlotNextToSurfaceTypeProvider.getEmptySlotsNextToSurfaceType(surfaceTypes, SurfaceType.COAL_MINE)).willReturn(Arrays.asList(coordinate1, coordinate2));

        given(random.randInt(0, 1)).willReturn(1);

        Optional<Coordinate> result = underTest.getRandomEmptySlotNextToSurfaceType(surfaceTypes, SurfaceType.COAL_MINE);

        assertThat(result).contains(coordinate2);
    }
}