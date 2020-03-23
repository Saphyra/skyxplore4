package com.github.saphyra.skyxplore.app.service.game_creation.star_connection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.app.domain.coordinate.Coordinate;
import com.github.saphyra.skyxplore.app.domain.star.Star;
import com.github.saphyra.skyxplore.app.service.common.DistanceCalculator;

@RunWith(MockitoJUnitRunner.class)
public class ClosestStarFinderTest {
    @Mock
    private DistanceCalculator distanceCalculator;

    @InjectMocks
    private ClosestStarFinder underTest;

    @Mock
    private Star star;

    @Mock
    private Star closestStar;

    @Mock
    private Star anotherStar;

    @Test
    public void getClosestStar() {
        given(star.getCoordinate()).willReturn(new Coordinate(1, 1));
        given(closestStar.getCoordinate()).willReturn(new Coordinate(2, 2));
        given(anotherStar.getCoordinate()).willReturn(new Coordinate(100, 100));

        given(distanceCalculator.getDistance(any(), any())).willCallRealMethod();

        Star result = underTest.getClosestStar(star, Arrays.asList(star, closestStar, anotherStar));

        assertThat(result).isEqualTo(closestStar);
    }
}