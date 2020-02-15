package com.github.saphyra.skyxplore_deprecated.game.dao.system.building;

import com.github.saphyra.skyxplore_deprecated.common.ExceptionFactory;
import com.github.saphyra.skyxplore_deprecated.common.context.RequestContext;
import com.github.saphyra.skyxplore_deprecated.common.context.RequestContextHolder;
import com.github.saphyra.skyxplore_deprecated.data.gamedata.domain.building.production.ProductionBuilding;
import com.github.saphyra.skyxplore_deprecated.data.gamedata.domain.building.production.ProductionBuildingService;
import com.github.saphyra.skyxplore_deprecated.game.dao.map.surface.Surface;
import com.github.saphyra.skyxplore_deprecated.game.dao.map.surface.SurfaceQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BuildingQueryService {
    private final BuildingDao buildingDao;
    private final ProductionBuildingService productionBuildingService;
    private final RequestContextHolder requestContextHolder;
    private final SurfaceQueryService surfaceQueryService;

    public Optional<Building> findBySurfaceIdAndPlayerId(UUID surfaceId) {
        RequestContext context = requestContextHolder.get();
        UUID playerId = context.getPlayerId();
        return buildingDao.findBySurfaceIdAndPlayerId(surfaceId, playerId);
    }

    public Building findByBuildingIdAndPlayerId(UUID buildingId) {
        RequestContext context = requestContextHolder.get();
        UUID playerId = context.getPlayerId();
        return buildingDao.findByBuildingIdAndPlayerId(buildingId, playerId)
            .orElseThrow(() -> ExceptionFactory.buildingNotFound(buildingId, playerId));
    }

    public List<Building> getProducersByStarIdAndResourceDataId(UUID starId, String dataId) {
        return getByStarIdAndPlayerId(starId)
            .stream()
            .filter(building -> canProduce(building, dataId))
            .collect(Collectors.toList());
    }

    private boolean canProduce(Building building, String dataId) {
        ProductionBuilding productionBuilding = productionBuildingService.get(building.getBuildingDataId());
        Surface surface = surfaceQueryService.findBySurfaceIdAndPlayerId(building.getSurfaceId());
        return productionBuilding.getGives().getOptional(dataId)
            .filter(production -> production.getPlaced().contains(surface.getSurfaceType()))
            .isPresent();
    }

    public List<Building> getByStarIdAndDataIdAndPlayerId(UUID starId, String dataId) {
        RequestContext context = requestContextHolder.get();
        UUID playerId = context.getPlayerId();
        return buildingDao.getByStarIdAndDataIdAndPlayerId(starId, dataId, playerId);
    }

    public List<Building> getByStarIdAndPlayerId(UUID starId) {
        RequestContext context = requestContextHolder.get();
        UUID playerId = context.getPlayerId();
        return buildingDao.getByStarIdAndPlayerId(starId, playerId);
    }
}
