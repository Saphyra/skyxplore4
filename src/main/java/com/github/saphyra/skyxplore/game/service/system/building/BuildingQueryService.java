package com.github.saphyra.skyxplore.game.service.system.building;

import com.github.saphyra.skyxplore.common.ExceptionFactory;
import com.github.saphyra.skyxplore.common.UuidConverter;
import com.github.saphyra.skyxplore.game.dao.system.building.Building;
import com.github.saphyra.skyxplore.game.dao.system.building.BuildingDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BuildingQueryService {
    private final BuildingDao buildingDao;
    private final UuidConverter uuidConverter;

    public Optional<Building> findBySurfaceId(UUID surfaceId) {
        return buildingDao.findBySurfaceId(surfaceId);
    }

    public Building findOneValidated(UUID buildingId) {
        return buildingDao.findById(uuidConverter.convertDomain(buildingId))
                .orElseThrow(() -> ExceptionFactory.buildingNotFound(buildingId));
    }
}
