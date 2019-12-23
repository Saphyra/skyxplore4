package com.github.saphyra.skyxplore.game.dao.system.building;

import com.github.saphyra.skyxplore.common.ExceptionFactory;
import com.github.saphyra.skyxplore.common.context.RequestContext;
import com.github.saphyra.skyxplore.common.context.RequestContextHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BuildingQueryService {
    private final BuildingDao buildingDao;
    private final RequestContextHolder requestContextHolder;

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

    public List<Building> getByStarIdAndDataIdAndPlayerId(UUID starId, String dataId) {
        RequestContext context = requestContextHolder.get();
        UUID playerId = context.getPlayerId();
        return buildingDao.getByStarIdAndDataIdAndPlayerId(starId, dataId, playerId);
    }
}
