package com.github.saphyra.skyxplore.game.newround.production;

import com.github.saphyra.skyxplore.data.gamedata.domain.building.production.Production;
import com.github.saphyra.skyxplore.data.gamedata.domain.building.production.ProductionBuilding;
import com.github.saphyra.skyxplore.data.gamedata.domain.building.production.ProductionBuildingService;
import com.github.saphyra.skyxplore.game.dao.map.surface.SurfaceQueryService;
import com.github.saphyra.skyxplore.game.dao.system.building.Building;
import com.github.saphyra.skyxplore.game.dao.system.building.BuildingQueryService;
import com.github.saphyra.skyxplore.game.dao.system.construction.ConstructionQueryService;
import com.github.saphyra.skyxplore.game.dao.system.construction.ConstructionStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProducerQueryService {
    private final BuildingQueryService buildingQueryService;
    private final ConstructionQueryService constructionQueryService;
    private final ProducerFactory producerFactory;
    private final ProductionBuildingService productionBuildingService;
    private final SurfaceQueryService surfaceQueryService;

    public List<Producer> getByStarIdAndDataId(UUID starId, String dataId) {
        Map<String, ProductionBuilding> buildings = productionBuildingService.values()
            .stream()
            //Filter productionBuildings can produce the given resource
            .filter(productionBuilding -> productionBuilding.getGives().containsKey(dataId))
            .collect(Collectors.toMap(ProductionBuilding::getId, Function.identity()));
        return buildings.values()
            .stream()
            //Query buildings for the producer buildingDataIds
            .flatMap(productionBuilding -> buildingQueryService.getByStarIdAndDataIdAndPlayerId(starId, productionBuilding.getId()).stream())
            //Filter buildings under construction / update, since they cannot produce
            .filter(building -> isNull(building.getConstructionId()) || constructionNotInProgress(building.getConstructionId()))
            //Filter buildings placed on proper surfaceType for the production
            .filter(building -> isOnCorrectSurfaceType(building, buildings.get(building.getBuildingDataId()).getGives().get(dataId)))
            .map(producerFactory::convertToProducer)
            .collect(Collectors.toList());
    }

    private boolean isOnCorrectSurfaceType(Building building, Production productionBuilding) {
        return productionBuilding.getPlaced().contains(surfaceQueryService.findBySurfaceIdAndPlayerId(building.getSurfaceId()).getSurfaceType());
    }

    private boolean constructionNotInProgress(UUID constructionId) {
        return !constructionQueryService.findByConstructionIdAndPlayerId(constructionId).getConstructionStatus().equals(ConstructionStatus.IN_PROGRESS);
    }
}
