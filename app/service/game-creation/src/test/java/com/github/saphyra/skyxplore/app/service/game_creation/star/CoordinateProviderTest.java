package com.github.saphyra.skyxplore.app.service.game_creation.star;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.app.domain.coordinate.Coordinate;
import com.github.saphyra.skyxplore.app.service.common.DistanceCalculator;
import com.github.saphyra.util.Random;

@RunWith(MockitoJUnitRunner.class)
public class CoordinateProviderTest {
    private static final int X = 100;
    private static final int Y = 50;
    private static final int MIN_DISTANCE = 20;

    @Mock
    private DistanceCalculator distanceCalculator;

    @Mock
    private Random random;

    @Mock
    private StarCreatorConfiguration configuration;

    @InjectMocks
    private CoordinateProvider underTest;

    @Test
    public void getRandomCoordinates() {
        given(configuration.getCreationAttempts()).willReturn(3);
        given(configuration.getX()).willReturn(X);
        given(configuration.getY()).willReturn(Y);
        given(configuration.getMinStarDistance()).willReturn(MIN_DISTANCE);


        given(random.randInt(0, X))
            .willReturn(0)
            .willReturn(10)
            .willReturn(100);
        given(random.randInt(0, Y))
            .willReturn(0)
            .willReturn(10)
            .willReturn(50);
        given(distanceCalculator.getDistance(any(), any())).willCallRealMethod();

        List<Coordinate> result = underTest.getRandomCoordinates();

        assertThat(result).containsOnly(new Coordinate(0, 0), new Coordinate(100, 50));

    }
}