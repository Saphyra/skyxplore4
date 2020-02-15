package com.github.saphyra.skyxplore_deprecated.game.dao.system.storage.setting;

import com.github.saphyra.converter.ConverterBase;
import com.github.saphyra.skyxplore.common.utils.UuidConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StorageSettingConverter extends ConverterBase<StorageSettingEntity, StorageSetting> {
    private final UuidConverter uuidConverter;

    @Override
    protected StorageSetting processEntityConversion(StorageSettingEntity entity) {
        return StorageSetting.builder()
            .storageSettingId(uuidConverter.convertEntity(entity.getStorageSettingId()))
            .gameId(uuidConverter.convertEntity(entity.getGameId()))
            .starId(uuidConverter.convertEntity(entity.getStarId()))
            .playerId(uuidConverter.convertEntity(entity.getPlayerId()))
            .dataId(entity.getDataId())
            .targetAmount(entity.getTargetAmount())
            .batchSize(entity.getBatchSize())
            .priority(entity.getPriority())
            .isNew(entity.isNew())
            .buildable(entity.getBuildable())
            .build();
    }

    @Override
    protected StorageSettingEntity processDomainConversion(StorageSetting domain) {
        return StorageSettingEntity.builder()
            .storageSettingId(uuidConverter.convertDomain(domain.getStorageSettingId()))
            .gameId(uuidConverter.convertDomain(domain.getGameId()))
            .starId(uuidConverter.convertDomain(domain.getStarId()))
            .playerId(uuidConverter.convertDomain(domain.getPlayerId()))
            .dataId(domain.getDataId())
            .targetAmount(domain.getTargetAmount())
            .batchSize(domain.getBatchSize())
            .priority(domain.getPriority())
            .isNew(domain.isNew())
            .buildable(domain.getBuildable())
            .build();
    }
}
