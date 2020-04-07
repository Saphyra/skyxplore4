package com.github.saphyra.skyxplore.app.game_data.domain.building.production;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import static org.mockito.Mockito.verify;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.app.common.utils.OptionalHashMap;
import com.github.saphyra.skyxplore.app.game_data.domain.building.BuildingDataValidator;

@RunWith(MockitoJUnitRunner.class)
public class ProductionBuildingValidatorTest {
    private static final String ID = "id";

    @Mock
    private BuildingDataValidator buildingDataValidator;

    @Mock
    private ProductionValidator productionValidator;

    @InjectMocks
    private ProductionBuildingValidator underTest;

    private Map<String, ProductionBuilding> map = new HashMap<>();

    private ProductionBuilding productionBuilding = new ProductionBuilding();

    @Mock
    private Production gives;

    private OptionalHashMap<String, Production> givesMap = new OptionalHashMap<>();

    @Before
    public void setUp() {
        map.put(ID, productionBuilding);

        productionBuilding.setWorkers(1);


        givesMap.put(ID, gives);
        productionBuilding.setGives(givesMap);
    }

    @Test
    public void nullWorkers() {
        productionBuilding.setWorkers(null);

        Throwable ex = catchThrowable(() -> underTest.validate(map));

        assertThat(ex).isInstanceOf(IllegalStateException.class);
        verify(buildingDataValidator).validate(productionBuilding);
    }

    @Test
    public void zeroWorkers() {
        productionBuilding.setWorkers(0);

        Throwable ex = catchThrowable(() -> underTest.validate(map));

        assertThat(ex).isInstanceOf(IllegalStateException.class);
        verify(buildingDataValidator).validate(productionBuilding);
    }

    @Test
    public void valid() {
        underTest.validate(map);

        verify(buildingDataValidator).validate(productionBuilding);
        verify(productionValidator).validate(givesMap);
    }
}