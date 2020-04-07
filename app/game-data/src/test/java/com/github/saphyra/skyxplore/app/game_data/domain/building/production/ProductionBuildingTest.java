package com.github.saphyra.skyxplore.app.game_data.domain.building.production;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.app.common.utils.OptionalHashMap;
import com.github.saphyra.skyxplore.app.domain.common.SurfaceType;

@RunWith(MockitoJUnitRunner.class)
public class ProductionBuildingTest {
    @InjectMocks
    private ProductionBuilding underTest;

    @Mock
    private Production production;

    @Test
    public void getPlaceableSurfaceTypes() {
        OptionalHashMap<String, Production> gives = new OptionalHashMap<>();
        gives.put("asd", production);
        underTest.setGives(gives);
        given(production.getPlaced()).willReturn(Arrays.asList(SurfaceType.COAL_MINE));

        List<SurfaceType> result = underTest.getPlaceableSurfaceTypes();

        assertThat(result).containsExactly(SurfaceType.COAL_MINE);
    }

    @Test
    public void getPrimarySurfaceType_fromList() {
        OptionalHashMap<String, Production> gives = new OptionalHashMap<>();
        gives.put("asd", production);
        underTest.setGives(gives);
        given(production.getPlaced()).willReturn(Arrays.asList(SurfaceType.COAL_MINE));

        SurfaceType result = underTest.getPrimarySurfaceType();

        assertThat(result).isEqualTo(SurfaceType.COAL_MINE);
    }

    @Test
    public void getPrimarySurfaceType_preset() {
        underTest.setPrimarySurfaceType(SurfaceType.COAL_MINE);

        SurfaceType result = underTest.getPrimarySurfaceType();

        assertThat(result).isEqualTo(SurfaceType.COAL_MINE);
    }
}