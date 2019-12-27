package com.github.saphyra.skyxplore.game.dao.system.storage.resource;

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
            .playerId(uuidConverter.convertEntity(entity.getPlayerId()))
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
            .starId(uuidConverter.convertDomain(domain.getStarId()))
            .playerId(uuidConverter.convertDomain(domain.getPlayerId()))
            .storageType(domain.getStorageType())
            .dataId(domain.getDataId())
            .amount(domain.getAmount())
            .round(domain.getRound())
            .build();
    }
}
