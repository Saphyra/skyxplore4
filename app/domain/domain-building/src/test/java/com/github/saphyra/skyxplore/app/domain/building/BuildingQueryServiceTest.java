package com.github.saphyra.skyxplore.app.domain.building;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.exceptionhandling.exception.NotFoundException;
import com.github.saphyra.skyxplore.app.common.exception_handling.ErrorCode;
import com.github.saphyra.skyxplore.app.common.request_context.RequestContext;
import com.github.saphyra.skyxplore.app.common.request_context.RequestContextHolder;
import com.github.saphyra.skyxplore.app.common.utils.OptionalHashMap;
import com.github.saphyra.skyxplore.app.domain.common.SurfaceType;
import com.github.saphyra.skyxplore.app.domain.surface.Surface;
import com.github.saphyra.skyxplore.app.domain.surface.SurfaceQueryService;
import com.github.saphyra.skyxplore.app.game_data.domain.building.production.Production;
import com.github.saphyra.skyxplore.app.game_data.domain.building.production.ProductionBuilding;
import com.github.saphyra.skyxplore.app.game_data.domain.building.production.ProductionBuildingService;

@RunWith(MockitoJUnitRunner.class)
public class BuildingQueryServiceTest {
    private static final UUID PLAYER_ID = UUID.randomUUID();
    private static final UUID SURFACE_ID_1 = UUID.randomUUID();
    private static final UUID SURFACE_ID_2 = UUID.randomUUID();
    private static final UUID BUILDING_ID = UUID.randomUUID();
    private static final UUID STAR_ID = UUID.randomUUID();
    private static final String BUILDING_DATA_ID = "building-data-id";
    private static final String RESOURCE_DATA_ID = "resource-data-id";

    @Mock
    private BuildingDao buildingDao;

    @Mock
    private ProductionBuildingService productionBuildingService;

    @Mock
    private RequestContextHolder requestContextHolder;

    @Mock
    private SurfaceQueryService surfaceQueryService;

    @InjectMocks
    private BuildingQueryService underTest;

    @Mock
    private RequestContext requestContext;

    @Mock
    private Building building1;

    @Mock
    private Building building2;

    @Mock
    private ProductionBuilding productionBuilding;

    @Mock
    private Surface surface1;

    @Mock
    private Surface surface2;

    @Mock
    private OptionalHashMap<String, Production> gives;

    @Mock
    private Production production;

    @Before
    public void setUp() {
        given(requestContextHolder.get()).willReturn(requestContext);
        given(requestContext.getPlayerId()).willReturn(PLAYER_ID);
    }

    @Test
    public void findBySurfaceIdAndPlayerId() {
        given(buildingDao.findBySurfaceIdAndPlayerId(SURFACE_ID_1, PLAYER_ID)).willReturn(Optional.of(building1));

        Optional<Building> result = underTest.findBySurfaceIdAndPlayerId(SURFACE_ID_1);

        assertThat(result).contains(building1);
    }

    @Test
    public void findByBuildingIdAndPlayerId() {
        given(buildingDao.findByBuildingIdAndPlayerId(BUILDING_ID, PLAYER_ID)).willReturn(Optional.of(building1));

        Building result = underTest.findByBuildingIdAndPlayerId(BUILDING_ID);

        assertThat(result).isEqualTo(building1);
    }

    @Test
    public void findByBuildingIdAndPlayerId_notFound() {
        given(buildingDao.findByBuildingIdAndPlayerId(BUILDING_ID, PLAYER_ID)).willReturn(Optional.empty());

        Throwable ex = catchThrowable(() -> underTest.findByBuildingIdAndPlayerId(BUILDING_ID));

        assertThat(ex).isInstanceOf(NotFoundException.class);
        NotFoundException exception = (NotFoundException) ex;
        assertThat(exception.getErrorMessage().getErrorCode()).isEqualTo(ErrorCode.BUILDING_NOT_FOUND.name());
    }

    @Test
    public void getProducerByStarOdAndResourceDataId() {
        given(buildingDao.getByStarIdAndPlayerId(STAR_ID, PLAYER_ID)).willReturn(Arrays.asList(building1, building2));
        given(building1.getSurfaceId()).willReturn(SURFACE_ID_1);
        given(building2.getSurfaceId()).willReturn(SURFACE_ID_2);
        given(building1.getBuildingDataId()).willReturn(BUILDING_DATA_ID);
        given(building2.getBuildingDataId()).willReturn(BUILDING_DATA_ID);

        given(productionBuildingService.get(BUILDING_DATA_ID)).willReturn(productionBuilding);
        given(surfaceQueryService.findBySurfaceIdAndPlayerId(SURFACE_ID_1)).willReturn(surface1);
        given(surfaceQueryService.findBySurfaceIdAndPlayerId(SURFACE_ID_2)).willReturn(surface2);
        given(productionBuilding.getGives()).willReturn(gives);
        given(gives.getOptional(RESOURCE_DATA_ID)).willReturn(Optional.of(production));
        given(production.getPlaced()).willReturn(Arrays.asList(SurfaceType.DESERT));
        given(surface1.getSurfaceType()).willReturn(SurfaceType.DESERT);
        given(surface2.getSurfaceType()).willReturn(SurfaceType.COAL_MINE);

        List<Building> result = underTest.getProducersByStarIdAndResourceDataId(STAR_ID, RESOURCE_DATA_ID);

        assertThat(result).containsExactly(building1);
    }

    @Test
    public void getByStarIdAndDataIdAndPlayerId() {
        given(buildingDao.getByStarIdAndDataIdAndPlayerId(STAR_ID, BUILDING_DATA_ID, PLAYER_ID)).willReturn(Arrays.asList(building1));

        List<Building> result = underTest.getByStarIdAndDataIdAndPlayerId(STAR_ID, BUILDING_DATA_ID);

        assertThat(result).containsExactly(building1);
    }

    @Test
    public void getByStarIdAndPlayerId() {
        given(buildingDao.getByStarIdAndPlayerId(STAR_ID, PLAYER_ID)).willReturn(Arrays.asList(building1));

        List<Building> result = underTest.getByStarIdAndPlayerId(STAR_ID);

        assertThat(result).containsExactly(building1);
    }
}