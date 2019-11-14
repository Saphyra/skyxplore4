package com.github.saphyra.skyxplore.game.module.system.building.creation;

import com.github.saphyra.skyxplore.game.dao.system.building.Building;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
class BuildingFactory {
    private final IdGenerator idGenerator;

    Building create(String buildingId, UUID gameId, UUID userId, UUID starId){
        return Building.builder()
                .buildingId(idGenerator.randomUUID())
                .buildingDataId(buildingId)
                .gameId(gameId)
                .userId(userId)
                .starId(starId)
                .level(1)
                .build();
    }
}
