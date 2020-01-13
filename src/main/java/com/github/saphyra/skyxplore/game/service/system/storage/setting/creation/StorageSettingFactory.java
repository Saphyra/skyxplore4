package com.github.saphyra.skyxplore.game.service.system.storage.setting.creation;

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

    public StorageSetting create(
        UUID gameId,
        UUID starId,
        UUID playerId,
        String dataId,
        Integer targetAmount,
        Integer priority
    ) {
        return StorageSetting.builder()
            .storageSettingId(idGenerator.randomUUID())
            .gameId(gameId)
            .starId(starId)
            .playerId(playerId)
            .dataId(dataId)
            .targetAmount(targetAmount)
            .priority(priority)
            .isNew(true)
            .build();
    }
}
