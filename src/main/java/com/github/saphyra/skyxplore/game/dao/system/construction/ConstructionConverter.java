package com.github.saphyra.skyxplore.game.dao.system.construction;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.github.saphyra.converter.ConverterBase;
import com.github.saphyra.skyxplore.common.UuidConverter;
import com.github.saphyra.skyxplore.game.dao.common.ConstructionRequirements;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class ConstructionConverter extends ConverterBase<ConstructionEntity, Construction> {
    private final UuidConverter uuidConverter;

    @Override
    protected Construction processEntityConversion(ConstructionEntity constructionEntity) {
        return Construction.builder()
            .constructionId(uuidConverter.convertEntity(constructionEntity.getConstructionId()))
            .gameId(uuidConverter.convertEntity(constructionEntity.getGameId()))
            .userId(uuidConverter.convertEntity(constructionEntity.getUserId()))
            .starId(uuidConverter.convertEntity(constructionEntity.getStarId()))
            .constructionRequirements(convertRequirements(
                constructionEntity.getResourceRequirements(),
                constructionEntity.getCurrentWorkPoints(),
                constructionEntity.getRequiredWorkPoints()
            ))
            .constructionType(constructionEntity.getConstructionType())
            .constructionStatus(constructionEntity.getConstructionStatus())
            .currentWorkPoints(constructionEntity.getCurrentWorkPoints())
            .priority(constructionEntity.getPriority())
            .externalId(uuidConverter.convertEntity(constructionEntity.getExternalId()))
            .dataId(constructionEntity.getDataId())
            .surfaceId(uuidConverter.convertEntity(constructionEntity.getSurfaceId()))
            .build();
    }

    private ConstructionRequirements convertRequirements(
        Map<String, Integer> resourceRequirements,
        Integer currentWorkPoints,
        Integer requiredWorkPoints
    ) {
        return ConstructionRequirements.builder()
            .currentWorkPoints(currentWorkPoints)
            .requiredWorkPoints(requiredWorkPoints)
            .requiredResources(new HashMap<>(resourceRequirements))
            .build();
    }

    @Override
    protected ConstructionEntity processDomainConversion(Construction domain) {
        return ConstructionEntity.builder()
            .constructionId(uuidConverter.convertDomain(domain.getConstructionId()))
            .gameId(uuidConverter.convertDomain(domain.getGameId()))
            .userId(uuidConverter.convertDomain(domain.getUserId()))
            .starId(uuidConverter.convertDomain(domain.getStarId()))
            .resourceRequirements(domain.getConstructionRequirements().getRequiredResources())
            .currentWorkPoints(domain.getConstructionRequirements().getCurrentWorkPoints())
            .requiredWorkPoints(domain.getConstructionRequirements().getRequiredWorkPoints())
            .constructionStatus(domain.getConstructionStatus())
            .constructionType(domain.getConstructionType())
            .currentWorkPoints(domain.getCurrentWorkPoints())
            .priority(domain.getPriority())
            .externalId(uuidConverter.convertDomain(domain.getExternalId()))
            .dataId(domain.getDataId())
            .surfaceId(uuidConverter.convertDomain(domain.getSurfaceId()))
            .build();
    }
}