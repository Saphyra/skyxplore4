package com.github.saphyra.skyxplore.game.service.system.storage.setting.create;

import com.github.saphyra.skyxplore.data.gamedata.domain.building.production.ProductionBuildingService;
import com.github.saphyra.skyxplore.game.dao.system.storage.setting.StorageSetting;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class StorageSettingFactory {
    private final IdGenerator idGenerator;
    private final ProductionBuildingService productionBuildingService;

    public StorageSetting create(
        UUID gameId,
        UUID starId,
        UUID playerId,
        String dataId,
        Integer targetAmount,
        Integer priority,
        Integer batchSize
    ) {
        return StorageSetting.builder()
            .storageSettingId(idGenerator.randomUUID())
            .gameId(gameId)
            .starId(starId)
            .playerId(playerId)
            .dataId(dataId)
            .buildable(isBuildable(dataId))
            .targetAmount(targetAmount)
            .batchSize(batchSize)
            .priority(priority)
            .isNew(true)
            .build();
    }

    private Boolean isBuildable(String dataId) {
        return productionBuildingService.values()
            .stream()
            .anyMatch(productionBuilding -> productionBuilding.getGives().containsKey(dataId));
    }
}
