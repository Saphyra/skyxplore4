package com.github.saphyra.skyxplore.game.dao.system.storage.allocation;

import org.springframework.stereotype.Component;

import com.github.saphyra.converter.ConverterBase;
import com.github.saphyra.skyxplore.common.UuidConverter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
class AllocationConverter extends ConverterBase<AllocationEntity, Allocation> {
    private final UuidConverter uuidConverter;

    @Override
    protected Allocation processEntityConversion(AllocationEntity entity) {
        return Allocation.builder()
            .allocationId(uuidConverter.convertEntity(entity.getAllocationId()))
            .gameId(uuidConverter.convertEntity(entity.getGameId()))
            .userId(uuidConverter.convertEntity(entity.getUserId()))
            .starId(uuidConverter.convertEntity(entity.getStarId()))
            .externalReference(uuidConverter.convertEntity(entity.getExternalReference()))
            .dataId(entity.getDataId())
            .storageType(entity.getStorageType())
            .amount(entity.getAmount())
            .allocationType(entity.getAllocationType())
            .build();
    }

    @Override
    protected AllocationEntity processDomainConversion(Allocation domain) {
        return AllocationEntity.builder()
            .allocationId(uuidConverter.convertDomain(domain.getAllocationId()))
            .gameId(uuidConverter.convertDomain(domain.getGameId()))
            .userId(uuidConverter.convertDomain(domain.getUserId()))
            .starId(uuidConverter.convertDomain(domain.getStarId()))
            .externalReference(uuidConverter.convertDomain(domain.getExternalReference()))
            .dataId(domain.getDataId())
            .storageType(domain.getStorageType())
            .amount(domain.getAmount())
            .allocationType(domain.getAllocationType())
            .build();
    }
}
