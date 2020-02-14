package com.github.saphyra.skyxplore.game.service.system.building;

import com.github.saphyra.skyxplore.data.gamedata.domain.building.storage.StorageBuilding;
import com.github.saphyra.skyxplore.data.gamedata.domain.building.storage.StorageBuildingService;
import com.github.saphyra.skyxplore.game.dao.system.building.Building;
import com.github.saphyra.skyxplore.game.dao.system.building.BuildingQueryService;
import com.github.saphyra.skyxplore.game.dao.system.storage.resource.StorageType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BuildingService {
    private final BuildingQueryService buildingQueryService;
    private final StorageBuildingService storageBuildingService;

    public Integer getDwellingSpaceAmount(UUID starId) {
        StorageBuilding storageBuilding = storageBuildingService.findByStorageType(StorageType.CITIZEN);
        List<Building> buildings = buildingQueryService.getByStarIdAndDataIdAndPlayerId(starId, storageBuilding.getId());

        return buildings.stream()
            .mapToInt(Building::getLevel)
            .map(level -> level * storageBuilding.getCapacity())
            .sum();
    }
}