package com.github.saphyra.skyxplore.app.service.game_creation.surface;

import com.github.saphyra.skyxplore.app.domain.coordinate.Coordinate;
import com.github.saphyra.skyxplore.app.domain.surface.SurfaceType;
import com.github.saphyra.util.Random;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class RandomEmptySlotProviderTest {
    @Mock
    private Random random;

    @InjectMocks
    private RandomEmptySlotProvider underTest;

    @Test
    public void getRandomEmptySlot() {
        SurfaceType[][] surfaceTypes = new SurfaceType[][]{
            new SurfaceType[]{null, null},
            new SurfaceType[]{SurfaceType.COAL_MINE, null}
        };

        given(random.randInt(0, 1)).willReturn(1).willReturn(0);

        Optional<Coordinate> result = underTest.getRandomEmptySlot(surfaceTypes);

        verify(random, times(4)).randInt(0, 1);
        assertThat(result).contains(new Coordinate(0, 0));
    }
}