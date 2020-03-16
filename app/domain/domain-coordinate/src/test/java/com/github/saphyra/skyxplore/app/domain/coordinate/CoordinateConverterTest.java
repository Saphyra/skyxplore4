package com.github.saphyra.skyxplore.app.domain.coordinate;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CoordinateConverterTest {
    private static final Integer X = 213421;
    private static final Integer Y = 251423;
    @InjectMocks
    private CoordinateConverter underTest;

    @Test
    public void convertEntity() {
        CoordinateEntity coordinateEntity = new CoordinateEntity(X, Y);

        Coordinate result = underTest.convertEntity(coordinateEntity);

        assertThat(result.getX()).isEqualTo(X);
        assertThat(result.getY()).isEqualTo(Y);
    }

    @Test
    public void convertDomain() {
        Coordinate domain = new Coordinate(X, Y);

        CoordinateEntity result = underTest.convertDomain(domain);

        assertThat(result.getX()).isEqualTo(X);
        assertThat(result.getY()).isEqualTo(Y);
    }
}