package com.github.saphyra.skyxplore.app.service.game_creation.surface;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.app.common.service.DomainSaverService;
import com.github.saphyra.skyxplore.app.domain.common.SurfaceType;
import com.github.saphyra.skyxplore.app.domain.star.Star;
import com.github.saphyra.skyxplore.app.domain.surface.Surface;


@RunWith(MockitoJUnitRunner.class)
public class SurfaceCreationServiceTest {
    @Mock
    private DomainSaverService domainSaverService;

    @Mock
    private SurfaceMapFactory surfaceMapFactory;

    @Mock
    private SurfaceMapper surfaceMapper;

    @InjectMocks
    private SurfaceCreationService underTest;

    @Mock
    private Star star;

    @Mock
    private Surface surface;

    @Test
    public void createSurfaces() {
        SurfaceType[][] surfaceTypes = new SurfaceType[0][0];
        given(surfaceMapFactory.createSurfaceMap()).willReturn(surfaceTypes);
        given(surfaceMapper.mapSurfaces(surfaceTypes, star)).willReturn(Arrays.asList(surface));

        Map<Star, List<Surface>> result = underTest.createSurfaces(Arrays.asList(star));

        assertThat(result.get(star)).isEqualTo(Arrays.asList(surface));
    }
}