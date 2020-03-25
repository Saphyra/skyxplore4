package com.github.saphyra.skyxplore.app.service.game_creation.surface;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verifyZeroInteractions;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.app.domain.common.SurfaceType;
import com.github.saphyra.util.Random;

@RunWith(MockitoJUnitRunner.class)
public class SurfaceTypeListFactoryTest {
    @Mock
    private Random random;

    @Mock
    private SurfaceCreationProperties properties;

    @InjectMocks
    private SurfaceTypeListFactory underTest;

    @Test
    public void createSurfaceTypeList_initial_noOptional(){
        SurfaceCreationProperties.SurfaceTypeSpawnDetails spawnDetails = new SurfaceCreationProperties.SurfaceTypeSpawnDetails(SurfaceType.DESERT.name(), 2, false);
        SurfaceCreationProperties.SurfaceTypeSpawnDetails optionalSpawnDetails = new SurfaceCreationProperties.SurfaceTypeSpawnDetails(SurfaceType.COAL_MINE.name(), 1, true);
        given(properties.getSurfaceTypeSpawnDetails()).willReturn(Arrays.asList(spawnDetails, optionalSpawnDetails));
        given(random.randBoolean()).willReturn(true);

        List<SurfaceType> result = underTest.createSurfaceTypeList(true);

        assertThat(result).containsExactly(SurfaceType.DESERT, SurfaceType.DESERT);
    }

    @Test
    public void createSurfaceTypeList_initial(){
        SurfaceCreationProperties.SurfaceTypeSpawnDetails spawnDetails = new SurfaceCreationProperties.SurfaceTypeSpawnDetails(SurfaceType.DESERT.name(), 2, false);
        SurfaceCreationProperties.SurfaceTypeSpawnDetails optionalSpawnDetails = new SurfaceCreationProperties.SurfaceTypeSpawnDetails(SurfaceType.COAL_MINE.name(), 1, true);
        given(properties.getSurfaceTypeSpawnDetails()).willReturn(Arrays.asList(spawnDetails, optionalSpawnDetails));
        given(random.randBoolean()).willReturn(false);

        List<SurfaceType> result = underTest.createSurfaceTypeList(true);

        assertThat(result).containsExactlyInAnyOrder(SurfaceType.DESERT, SurfaceType.DESERT, SurfaceType.COAL_MINE);
    }

    @Test
    public void createSurfaceTypeList(){
        SurfaceCreationProperties.SurfaceTypeSpawnDetails spawnDetails = new SurfaceCreationProperties.SurfaceTypeSpawnDetails(SurfaceType.DESERT.name(), 2, false);
        SurfaceCreationProperties.SurfaceTypeSpawnDetails optionalSpawnDetails = new SurfaceCreationProperties.SurfaceTypeSpawnDetails(SurfaceType.COAL_MINE.name(), 1, true);
        given(properties.getSurfaceTypeSpawnDetails()).willReturn(Arrays.asList(spawnDetails, optionalSpawnDetails));

        List<SurfaceType> result = underTest.createSurfaceTypeList(false);

        verifyZeroInteractions(random);
        assertThat(result).containsExactlyInAnyOrder(SurfaceType.DESERT, SurfaceType.DESERT, SurfaceType.COAL_MINE);
    }
}