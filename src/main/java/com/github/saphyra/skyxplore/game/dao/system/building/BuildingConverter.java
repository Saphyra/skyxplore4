package com.github.saphyra.skyxplore.game.dao.system.building;

import com.github.saphyra.converter.ConverterBase;
import com.github.saphyra.skyxplore.common.UuidConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BuildingConverter extends ConverterBase<BuildingEntity, Building> {
    private final UuidConverter uuidConverter;

    @Override
    protected Building processEntityConversion(BuildingEntity entity) {
        return Building.builder()
            .buildingId(uuidConverter.convertEntity(entity.getBuildingId()))
            .buildingDataId(entity.getBuildingDataId())
            .gameId(uuidConverter.convertEntity(entity.getGameId()))
            .starId(uuidConverter.convertEntity(entity.getStarId()))
            .playerId(uuidConverter.convertEntity(entity.getPlayerId()))
            .surfaceId(uuidConverter.convertEntity(entity.getSurfaceId()))
            .level(entity.getLevel())
            .constructionId(uuidConverter.convertEntity(entity.getConstructionId()))
            .isNew(entity.isNew())
            .build();
    }

    @Override
    protected BuildingEntity processDomainConversion(Building domain) {
        return BuildingEntity.builder()
            .buildingId(uuidConverter.convertDomain(domain.getBuildingId()))
            .buildingDataId(domain.getBuildingDataId())
            .gameId(uuidConverter.convertDomain(domain.getGameId()))
            .starId(uuidConverter.convertDomain(domain.getStarId()))
            .playerId(uuidConverter.convertDomain(domain.getPlayerId()))
            .surfaceId(uuidConverter.convertDomain(domain.getSurfaceId()))
            .level(domain.getLevel())
            .constructionId(uuidConverter.convertDomain(domain.getConstructionId()))
            .isNew(domain.isNew())
            .build();
    }
}
