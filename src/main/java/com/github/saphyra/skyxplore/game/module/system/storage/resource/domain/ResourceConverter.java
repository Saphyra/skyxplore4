package com.github.saphyra.skyxplore.game.module.system.storage.resource.domain;

import com.github.saphyra.converter.ConverterBase;
import com.github.saphyra.skyxplore.common.UuidConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class ResourceConverter extends ConverterBase<ResourceEntity, Resource> {
    private final UuidConverter uuidConverter;

    @Override
    protected Resource processEntityConversion(ResourceEntity entity) {
        return Resource.builder()
                .resourceId(uuidConverter.convertEntity(entity.getResourceId()))
                .gameId(uuidConverter.convertEntity(entity.getGameId()))
                .userId(uuidConverter.convertEntity(entity.getUserId()))
                .starId(uuidConverter.convertEntity(entity.getStarId()))
                .storageType(entity.getStorageType())
                .dataId(entity.getDataId())
                .amount(entity.getAmount())
                .round(entity.getRound())
                .build();
    }

    @Override
    protected ResourceEntity processDomainConversion(Resource domain) {
        return ResourceEntity.builder()
                .resourceId(uuidConverter.convertDomain(domain.getResourceId()))
                .gameId(uuidConverter.convertDomain(domain.getGameId()))
                .userId(uuidConverter.convertDomain(domain.getUserId()))
                .starId(uuidConverter.convertDomain(domain.getStarId()))
                .storageType(domain.getStorageType())
                .dataId(domain.getDataId())
                .amount(domain.getAmount())
                .round(domain.getRound())
                .build();
    }
}
