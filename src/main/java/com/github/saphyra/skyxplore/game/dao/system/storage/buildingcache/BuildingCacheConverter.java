package com.github.saphyra.skyxplore.game.dao.system.storage.buildingcache;

import com.github.saphyra.converter.ConverterBase;
import com.github.saphyra.skyxplore.common.UuidConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BuildingCacheConverter extends ConverterBase<BuildingCacheEntity, BuildingCache> {
    private final UuidConverter uuidConverter;

    @Override
    protected BuildingCache processEntityConversion(BuildingCacheEntity entity) {
        return BuildingCache.builder()
            .buildingCacheId(uuidConverter.convertEntity(entity.getBuildingCacheId()))
            .gameId(uuidConverter.convertEntity(entity.getGameId()))
            .playerId(uuidConverter.convertEntity(entity.getPlayerId()))
            .buildingId(uuidConverter.convertEntity(entity.getBuildingId()))
            .dataId(entity.getDataId())
            .amount(entity.getAmount())
            .isNew(entity.isNew())
            .build();
    }

    @Override
    protected BuildingCacheEntity processDomainConversion(BuildingCache domain) {
        return BuildingCacheEntity.builder()
            .buildingCacheId(uuidConverter.convertDomain(domain.getBuildingCacheId()))
            .gameId(uuidConverter.convertDomain(domain.getGameId()))
            .playerId(uuidConverter.convertDomain(domain.getPlayerId()))
            .buildingId(uuidConverter.convertDomain(domain.getBuildingId()))
            .dataId(domain.getDataId())
            .amount(domain.getAmount())
            .isNew(domain.isNew())
            .build();
    }
}
