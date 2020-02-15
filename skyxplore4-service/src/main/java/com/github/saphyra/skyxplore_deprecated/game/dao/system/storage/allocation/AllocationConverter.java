package com.github.saphyra.skyxplore_deprecated.game.dao.system.storage.allocation;

import com.github.saphyra.converter.ConverterBase;
import com.github.saphyra.skyxplore.common.utils.UuidConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
class AllocationConverter extends ConverterBase<AllocationEntity, Allocation> {
    private final UuidConverter uuidConverter;

    @Override
    protected Allocation processEntityConversion(AllocationEntity entity) {
        return Allocation.builder()
            .allocationId(uuidConverter.convertEntity(entity.getAllocationId()))
            .gameId(uuidConverter.convertEntity(entity.getGameId()))
            .starId(uuidConverter.convertEntity(entity.getStarId()))
            .playerId(uuidConverter.convertEntity(entity.getPlayerId()))
            .externalReference(uuidConverter.convertEntity(entity.getExternalReference()))
            .dataId(entity.getDataId())
            .storageType(entity.getStorageType())
            .amount(entity.getAmount())
            .allocationType(entity.getAllocationType())
            .isNew(entity.isNew())
            .build();
    }

    @Override
    protected AllocationEntity processDomainConversion(Allocation domain) {
        return AllocationEntity.builder()
            .allocationId(uuidConverter.convertDomain(domain.getAllocationId()))
            .gameId(uuidConverter.convertDomain(domain.getGameId()))
            .starId(uuidConverter.convertDomain(domain.getStarId()))
            .playerId(uuidConverter.convertDomain(domain.getPlayerId()))
            .externalReference(uuidConverter.convertDomain(domain.getExternalReference()))
            .dataId(domain.getDataId())
            .storageType(domain.getStorageType())
            .amount(domain.getAmount())
            .allocationType(domain.getAllocationType())
            .isNew(domain.isNew())
            .build();
    }
}
