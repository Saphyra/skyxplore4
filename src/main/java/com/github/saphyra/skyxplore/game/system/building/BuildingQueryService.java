package com.github.saphyra.skyxplore.game.system.building;

import com.github.saphyra.skyxplore.common.ExceptionFactory;
import com.github.saphyra.skyxplore.common.UuidConverter;
import com.github.saphyra.skyxplore.game.system.building.domain.Building;
import com.github.saphyra.skyxplore.game.system.building.domain.BuildingDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BuildingQueryService {
    private final BuildingDao buildingDao;
    private final UuidConverter uuidConverter;

    public Building findOneValidated(UUID buildingId) {
        return buildingDao.findById(uuidConverter.convertDomain(buildingId))
                .orElseThrow(() -> ExceptionFactory.buildingNotFound(buildingId));
    }
}
