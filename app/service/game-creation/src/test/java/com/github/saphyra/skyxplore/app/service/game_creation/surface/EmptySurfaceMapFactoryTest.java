package com.github.saphyra.skyxplore.app.service.game_creation.surface;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.app.domain.common.SurfaceType;
import com.github.saphyra.util.Random;


@RunWith(MockitoJUnitRunner.class)
public class EmptySurfaceMapFactoryTest {
    private static final Integer MIN_SIZE = 1;
    private static final Integer MAX_SIZE = 3;
    private static final int MAP_SIZE = 2;

    @Mock
    private Random random;

    @Mock
    private SurfaceCreationProperties properties;

    @InjectMocks
    private EmptySurfaceMapFactory underTest;

    @Test
    public void createEmptySurfaceMap() {
        given(properties.getMinSize()).willReturn(MIN_SIZE);
        given(properties.getMaxSize()).willReturn(MAX_SIZE);
        given(random.randInt(MIN_SIZE, MAX_SIZE)).willReturn(MAP_SIZE);

        SurfaceType[][] result = underTest.createEmptySurfaceMap();

        assertThat(result.length).isEqualTo(MAP_SIZE);
        assertThat(result[0].length).isEqualTo(MAP_SIZE);
        assertThat(result[1].length).isEqualTo(MAP_SIZE);
        result[0][0] = SurfaceType.COAL_MINE;
        assertThat(result[1][0]).isNull();
    }
}