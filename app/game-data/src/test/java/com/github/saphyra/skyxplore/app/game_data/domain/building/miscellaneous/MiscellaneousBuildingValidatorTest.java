package com.github.saphyra.skyxplore.app.game_data.domain.building.miscellaneous;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.app.domain.common.SurfaceType;
import com.github.saphyra.skyxplore.app.game_data.domain.building.BuildingDataValidator;

@RunWith(MockitoJUnitRunner.class)
public class MiscellaneousBuildingValidatorTest {
    @Mock
    private BuildingDataValidator buildingDataValidator;

    @InjectMocks
    private MiscellaneousBuildingValidator underTest;

    @Mock
    private MiscellaneousBuilding miscellaneousBuilding;

    @Test
    public void nullPlaceableSurfaceTypes() {
        given(miscellaneousBuilding.getPlaceableSurfaceTypes()).willReturn(null);
        Map<String, MiscellaneousBuilding> input = new HashMap<>();
        input.put("asd", miscellaneousBuilding);

        Throwable ex = catchThrowable(() -> underTest.validate(input));

        verify(buildingDataValidator).validate(miscellaneousBuilding);
        assertThat(ex).isInstanceOf(IllegalStateException.class);
        assertThat(ex.getCause()).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void nullInPlaceableSurfaceTypes() {
        List<SurfaceType> surfaceTypes = new ArrayList<>();
        surfaceTypes.add(null);
        given(miscellaneousBuilding.getPlaceableSurfaceTypes()).willReturn(surfaceTypes);
        Map<String, MiscellaneousBuilding> input = new HashMap<>();
        input.put("asd", miscellaneousBuilding);

        Throwable ex = catchThrowable(() -> underTest.validate(input));

        verify(buildingDataValidator).validate(miscellaneousBuilding);
        assertThat(ex).isInstanceOf(IllegalStateException.class);
        assertThat(ex.getCause()).isInstanceOf(NullPointerException.class);
    }

    @Test
    public void notPlaceableBuilding() {
        given(miscellaneousBuilding.getPlaceableSurfaceTypes()).willReturn(Collections.emptyList());
        Map<String, MiscellaneousBuilding> input = new HashMap<>();
        input.put("asd", miscellaneousBuilding);

        Throwable ex = catchThrowable(() -> underTest.validate(input));

        verify(buildingDataValidator).validate(miscellaneousBuilding);
        assertThat(ex).isInstanceOf(IllegalStateException.class);
        assertThat(ex.getCause()).isInstanceOf(IllegalStateException.class);
    }

    @Test
    public void valid() {
        given(miscellaneousBuilding.getPlaceableSurfaceTypes()).willReturn(Arrays.asList(SurfaceType.COAL_MINE));
        Map<String, MiscellaneousBuilding> input = new HashMap<>();
        input.put("asd", miscellaneousBuilding);

        underTest.validate(input);

        verify(buildingDataValidator).validate(miscellaneousBuilding);
    }
}