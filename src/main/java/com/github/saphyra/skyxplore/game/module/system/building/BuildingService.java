package com.github.saphyra.skyxplore.game.module.system.building;

import com.github.saphyra.skyxplore.common.event.GameDeletedEvent;
import com.github.saphyra.skyxplore.data.gamedata.domain.building.storage.StorageBuilding;
import com.github.saphyra.skyxplore.data.gamedata.domain.building.storage.StorageBuildingService;
import com.github.saphyra.skyxplore.game.dao.system.building.Building;
import com.github.saphyra.skyxplore.game.dao.system.building.BuildingDao;
import com.github.saphyra.skyxplore.game.dao.system.storage.resource.StorageType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BuildingService {
    private final BuildingDao buildingDao;
    private final StorageBuildingService storageBuildingService;

    @EventListener
    void gameDeletedEventProcessor(GameDeletedEvent gameDeletedEvent) {
        buildingDao.deleteByGameIdAndUserId(gameDeletedEvent.getGameId(), gameDeletedEvent.getUserId());
    }

    public Integer getDwellingSpaceAmount(UUID starId) {
        StorageBuilding storageBuilding = storageBuildingService.findByStorageType(StorageType.CITIZEN);
        List<Building> buildings = buildingDao.getByStarIdAndDataId(starId, storageBuilding.getId());

        return buildings.stream()
                .mapToInt(Building::getLevel)
                .map(level -> level * storageBuilding.getCapacity())
                .sum();
    }
}