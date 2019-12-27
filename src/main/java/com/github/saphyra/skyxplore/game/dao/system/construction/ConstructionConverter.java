package com.github.saphyra.skyxplore.game.dao.system.construction;

import com.github.saphyra.converter.ConverterBase;
import com.github.saphyra.skyxplore.common.DateTimeUtil;
import com.github.saphyra.skyxplore.common.UuidConverter;
import com.github.saphyra.skyxplore.game.dao.common.ConstructionRequirements;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class ConstructionConverter extends ConverterBase<ConstructionEntity, Construction> {
    private final ConstructionResourceRequirementDao constructionResourceRequirementDao;
    private final DateTimeUtil dateTimeUtil;
    private final UuidConverter uuidConverter;

    @Override
    protected Construction processEntityConversion(ConstructionEntity constructionEntity) {
        UUID constructionId = uuidConverter.convertEntity(constructionEntity.getConstructionId());
        return Construction.builder()
            .constructionId(constructionId)
            .gameId(uuidConverter.convertEntity(constructionEntity.getGameId()))
            .starId(uuidConverter.convertEntity(constructionEntity.getStarId()))
            .playerId(uuidConverter.convertEntity(constructionEntity.getPlayerId()))
            .constructionRequirements(convertRequirements(
                constructionResourceRequirementDao.getByConstructionId(constructionId),
                constructionEntity.getRequiredWorkPoints()
            ))
            .constructionType(constructionEntity.getConstructionType())
            .constructionStatus(constructionEntity.getConstructionStatus())
            .currentWorkPoints(constructionEntity.getCurrentWorkPoints())
            .priority(constructionEntity.getPriority())
            .externalId(uuidConverter.convertEntity(constructionEntity.getExternalId()))
            .surfaceId(uuidConverter.convertEntity(constructionEntity.getSurfaceId()))
            .additionalData(constructionEntity.getAdditionalData())
            .addedAt(dateTimeUtil.convertEntity(constructionEntity.getAddedAt()))
            .build();
    }

    private ConstructionRequirements convertRequirements(
        Map<String, Integer> resourceRequirements,
        Integer requiredWorkPoints
    ) {
        return ConstructionRequirements.builder()
            .requiredWorkPoints(requiredWorkPoints)
            .requiredResources(new HashMap<>(resourceRequirements))
            .build();
    }

    @Override
    protected ConstructionEntity processDomainConversion(Construction domain) {
        return ConstructionEntity.builder()
            .constructionId(uuidConverter.convertDomain(domain.getConstructionId()))
            .gameId(uuidConverter.convertDomain(domain.getGameId()))
            .starId(uuidConverter.convertDomain(domain.getStarId()))
            .playerId(uuidConverter.convertDomain(domain.getPlayerId()))
            .currentWorkPoints(domain.getCurrentWorkPoints())
            .requiredWorkPoints(domain.getConstructionRequirements().getRequiredWorkPoints())
            .constructionStatus(domain.getConstructionStatus())
            .constructionType(domain.getConstructionType())
            .currentWorkPoints(domain.getCurrentWorkPoints())
            .priority(domain.getPriority())
            .externalId(uuidConverter.convertDomain(domain.getExternalId()))
            .surfaceId(uuidConverter.convertDomain(domain.getSurfaceId()))
            .additionalData(domain.getAdditionalData())
            .addedAt(dateTimeUtil.convertDomain(domain.getAddedAt()))
            .build();
    }
}
