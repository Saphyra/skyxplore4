package com.github.saphyra.skyxplore.game.service.system.building.creation;

import com.github.saphyra.skyxplore.game.dao.system.building.Building;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
class BuildingFactory {
    private final IdGenerator idGenerator;

    Building create(String buildingId, UUID gameId, UUID starId, UUID surfaceId, UUID playerId) {
        return Building.builder()
            .buildingId(idGenerator.randomUUID())
            .buildingDataId(buildingId)
            .gameId(gameId)
            .starId(starId)
            .playerId(playerId)
            .surfaceId(surfaceId)
            .level(1)
            .isNew(true)
            .build();
    }
}
