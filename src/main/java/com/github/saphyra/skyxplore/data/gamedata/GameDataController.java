package com.github.saphyra.skyxplore.data.gamedata;

import static com.github.saphyra.skyxplore.common.RequestConstants.API_PREFIX;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.github.saphyra.skyxplore.common.ExceptionFactory;
import com.github.saphyra.skyxplore.data.base.AbstractDataService;
import com.github.saphyra.skyxplore.data.gamedata.domain.GameDataItem;
import com.github.saphyra.skyxplore.data.gamedata.domain.building.BuildingData;
import com.github.saphyra.skyxplore.game.module.map.surface.domain.SurfaceType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class GameDataController {
    private static final String GET_BUILDABLE_BUILDINGS_MAPPING = API_PREFIX + "/data/building/{surfaceType}";
    private static final String GET_DATA_MAPPING = API_PREFIX + "/data/{dataId}";

    private final List<AbstractDataService<?, ? extends GameDataItem>> dataServices;
    private final List<? extends AbstractDataService<String, ? extends BuildingData>> buildingDataServices;

    @GetMapping(GET_BUILDABLE_BUILDINGS_MAPPING)
    private List<BuildingData> getBuildableBuildings(
        @PathVariable("surfaceType") SurfaceType surfaceType //TODO handle MethodArgumentTypeMismatchException (pathVar String cannot be parsed to SurfaceType)
    ) {
        log.info("Querying buildable buildings for surfaceType {}", surfaceType);
        return buildingDataServices.stream()
            .flatMap(buildingDataService -> buildingDataService.values().stream())
            .filter(buildingData -> buildingData.getPlaceableSurfaceTypes().contains(surfaceType))
            .collect(Collectors.toList());
    }

    @GetMapping(GET_DATA_MAPPING)
    GameDataItem getData(@PathVariable("dataId") String dataId) {
        return dataServices.stream()
                .flatMap(abstractDataService -> abstractDataService.values().stream())
                .filter(gameDataItem -> gameDataItem.getId().equals(dataId))
                .findFirst()
                .orElseThrow(() -> ExceptionFactory.dataNotFount(dataId));
    }
}
