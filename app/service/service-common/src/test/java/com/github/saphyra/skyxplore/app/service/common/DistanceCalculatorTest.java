package com.github.saphyra.skyxplore.app.service.common;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.app.domain.coordinate.Coordinate;

@RunWith(MockitoJUnitRunner.class)
public class DistanceCalculatorTest {
    @InjectMocks
    private DistanceCalculator underTest;

    @Test
    public void getDistance() {
        Coordinate coordinate1 = new Coordinate(0, 0);
        Coordinate coordinate2 = new Coordinate(1, 1);

        double result = underTest.getDistance(coordinate1, coordinate2);

        assertThat(result).isEqualTo(Math.sqrt(2));
    }
}