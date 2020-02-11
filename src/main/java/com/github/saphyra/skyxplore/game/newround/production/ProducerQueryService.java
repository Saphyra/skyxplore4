package com.github.saphyra.skyxplore.game.newround.production;

import com.github.saphyra.skyxplore.common.context.RequestContext;
import com.github.saphyra.skyxplore.common.context.RequestContextHolder;
import com.github.saphyra.skyxplore.data.gamedata.domain.building.production.Production;
import com.github.saphyra.skyxplore.data.gamedata.domain.building.production.ProductionBuilding;
import com.github.saphyra.skyxplore.data.gamedata.domain.building.production.ProductionBuildingService;
import com.github.saphyra.skyxplore.game.dao.map.surface.SurfaceQueryService;
import com.github.saphyra.skyxplore.game.dao.system.building.Building;
import com.github.saphyra.skyxplore.game.dao.system.building.BuildingQueryService;
import com.github.saphyra.skyxplore.game.dao.system.construction.ConstructionQueryService;
import com.github.saphyra.skyxplore.game.dao.system.construction.ConstructionStatus;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProducerQueryService {
    private final Map<UUID, Map<ProducerKey, List<Producer>>> producerStorage = new ConcurrentHashMap<>();

    private final BuildingQueryService buildingQueryService;
    private final ConstructionQueryService constructionQueryService;
    private final ProducerFactory producerFactory;
    private final ProductionBuildingService productionBuildingService;
    private final RequestContextHolder requestContextHolder;
    private final SurfaceQueryService surfaceQueryService;

    public void clear(UUID gameId) {
        producerStorage.remove(gameId);
    }

    public List<Producer> getByStarIdAndDataId(UUID starId, String dataId) {
        RequestContext requestContext = requestContextHolder.get();
        UUID gameId = requestContext.getGameId();
        Map<ProducerKey, List<Producer>> producerMap = getMapByGameId(gameId);
        ProducerKey key = ProducerKey.builder()
            .starId(starId)
            .dataId(dataId)
            .build();
        if (!producerMap.containsKey(key)) {
            producerMap.put(key, getProducers(starId, dataId));
        }

        List<Producer> result = producerMap.get(key)
            //Order by load
            .stream()
            .sorted(Comparator.comparing(Producer::getLoad))
            .collect(Collectors.toList());
        log.info("Number of producers found for starId {} and dataId {}: {}", starId, dataId, result.size());
        return result;
    }

    private Map<ProducerKey, List<Producer>> getMapByGameId(UUID gameId) {
        if (!producerStorage.containsKey(gameId)) {
            producerStorage.put(gameId, new ConcurrentHashMap<>());
        }
        return producerStorage.get(gameId);
    }

    private List<Producer> getProducers(UUID starId, String dataId) {
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

    @Builder
    @Data
    private static class ProducerKey {
        @NonNull
        private final UUID starId;

        @NonNull
        private final String dataId;
    }
}
