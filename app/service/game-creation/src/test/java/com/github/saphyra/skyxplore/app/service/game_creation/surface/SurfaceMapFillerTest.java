package com.github.saphyra.skyxplore.app.service.game_creation.surface;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doAnswer;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.app.domain.common.SurfaceType;

@RunWith(MockitoJUnitRunner.class)
public class SurfaceMapFillerTest {
    @Mock
    private SurfaceFiller surfaceFiller;

    @Mock
    private SurfaceTypeListFactory surfaceTypeListFactory;

    @InjectMocks
    private SurfaceMapFiller underTest;

    @Test
    public void fillSurfaceMap() {
        SurfaceType[][] surfaceTypes = new SurfaceType[][]{
            new SurfaceType[1],
            new SurfaceType[1],
        };

        given(surfaceTypeListFactory.createSurfaceTypeList(true)).willReturn(Arrays.asList(SurfaceType.DESERT));
        given(surfaceTypeListFactory.createSurfaceTypeList(false)).willReturn(Arrays.asList(SurfaceType.COAL_MINE));
        doAnswer(invocationOnMock -> surfaceTypes[0][0] = SurfaceType.DESERT).when(surfaceFiller).fillBlockWithSurfaceType(surfaceTypes, SurfaceType.DESERT, true);
        doAnswer(invocationOnMock -> surfaceTypes[1][0] = SurfaceType.COAL_MINE).when(surfaceFiller).fillBlockWithSurfaceType(surfaceTypes, SurfaceType.COAL_MINE, false);

        SurfaceType[][] result = underTest.fillSurfaceMap(surfaceTypes);

        assertThat(result[0][0]).isEqualTo(SurfaceType.DESERT);
        assertThat(result[1][0]).isEqualTo(SurfaceType.COAL_MINE);
    }
}