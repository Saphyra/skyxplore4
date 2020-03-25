package com.github.saphyra.skyxplore.app.service.game_creation.surface;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.app.domain.common.SurfaceType;

@RunWith(MockitoJUnitRunner.class)
public class SurfaceMapFactoryTest {
    @Mock
    private EmptySurfaceMapFactory emptySurfaceMapFactory;

    @Mock
    private SurfaceMapFiller surfaceMapFiller;

    @InjectMocks
    private SurfaceMapFactory underTest;

    @Test
    public void createSurfaceMap() {
        SurfaceType[][] surfaceTypes1 = new SurfaceType[0][0];
        SurfaceType[][] surfaceTypes2 = new SurfaceType[1][1];
        given(emptySurfaceMapFactory.createEmptySurfaceMap()).willReturn(surfaceTypes1);
        given(surfaceMapFiller.fillSurfaceMap(surfaceTypes1)).willReturn(surfaceTypes2);

        SurfaceType[][] result = underTest.createSurfaceMap();

        assertThat(result).isEqualTo(surfaceTypes2);
    }
}