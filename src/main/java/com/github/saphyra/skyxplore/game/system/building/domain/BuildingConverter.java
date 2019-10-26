package com.github.saphyra.skyxplore.game.system.building.domain;

import com.github.saphyra.converter.ConverterBase;
import com.github.saphyra.encryption.impl.IntegerEncryptor;
import com.github.saphyra.encryption.impl.StringEncryptor;
import com.github.saphyra.skyxplore.common.UuidConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BuildingConverter extends ConverterBase<BuildingEntity, Building> {
    private final IntegerEncryptor integerEncryptor;
    private final StringEncryptor stringEncryptor;
    private final UuidConverter uuidConverter;

    @Override
    protected Building processEntityConversion(BuildingEntity entity) {
        return Building.builder()
                .buildingId(uuidConverter.convertEntity(entity.getBuildingId()))
                .buildingDataId(stringEncryptor.decryptEntity(entity.getBuildingDataId(), entity.getUserId()))
                .gameId(uuidConverter.convertEntity(entity.getGameId()))
                .userId(uuidConverter.convertEntity(entity.getUserId()))
                .level(integerEncryptor.decryptEntity(entity.getLevel(), entity.getUserId()))
                .constructionId(uuidConverter.convertEntity(entity.getConstructionId()))
                .build();
    }

    @Override
    protected BuildingEntity processDomainConversion(Building domain) {
        return BuildingEntity.builder()
                .buildingId(uuidConverter.convertDomain(domain.getBuildingId()))
                .buildingDataId(stringEncryptor.encryptEntity(domain.getBuildingDataId(), uuidConverter.convertDomain(domain.getUserId())))
                .gameId(uuidConverter.convertDomain(domain.getGameId()))
                .userId(uuidConverter.convertDomain(domain.getUserId()))
                .level(integerEncryptor.encryptEntity(domain.getLevel(), uuidConverter.convertDomain(domain.getUserId())))
                .constructionId(uuidConverter.convertDomain(domain.getConstructionId()))
                .build();
    }
}
