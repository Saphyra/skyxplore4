package com.github.saphyra.skyxplore.game.dao.system.storage.setting;

import com.github.saphyra.converter.ConverterBase;
import com.github.saphyra.skyxplore.common.UuidConverter;
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
            .playerId(uuidConverter.convertEntity(entity.getPlayerId()))
            .dataId(entity.getDataId())
            .targetAmount(entity.getTargetAmount())
            .priority(entity.getPriority())
            .build();
    }

    @Override
    protected StorageSettingEntity processDomainConversion(StorageSetting domain) {
        return StorageSettingEntity.builder()
            .storageSettingId(uuidConverter.convertDomain(domain.getStorageSettingId()))
            .gameId(uuidConverter.convertDomain(domain.getGameId()))
            .playerId(uuidConverter.convertDomain(domain.getPlayerId()))
            .dataId(domain.getDataId())
            .targetAmount(domain.getTargetAmount())
            .priority(domain.getPriority())
            .build();
    }
}
