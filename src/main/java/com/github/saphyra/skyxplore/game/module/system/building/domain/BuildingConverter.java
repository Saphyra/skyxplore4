package com.github.saphyra.skyxplore.game.module.system.building.domain;

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
                .userId(uuidConverter.convertEntity(entity.getUserId()))
                .starId(uuidConverter.convertEntity(entity.getStarId()))
                .level(entity.getLevel())
                .constructionId(uuidConverter.convertEntity(entity.getConstructionId()))
                .build();
    }

    @Override
    protected BuildingEntity processDomainConversion(Building domain) {
        return BuildingEntity.builder()
                .buildingId(uuidConverter.convertDomain(domain.getBuildingId()))
                .buildingDataId(domain.getBuildingDataId())
                .gameId(uuidConverter.convertDomain(domain.getGameId()))
                .userId(uuidConverter.convertDomain(domain.getUserId()))
                .starId(uuidConverter.convertDomain(domain.getStarId()))
                .level(domain.getLevel())
                .constructionId(uuidConverter.convertDomain(domain.getConstructionId()))
                .build();
    }
}