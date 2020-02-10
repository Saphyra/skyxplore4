package com.github.saphyra.skyxplore.game.dao.system.construction;

import com.github.saphyra.converter.ConverterBase;
import com.github.saphyra.skyxplore.common.DateTimeUtil;
import com.github.saphyra.skyxplore.common.UuidConverter;
import com.github.saphyra.skyxplore.game.dao.common.ConstructionRequirements;
import com.github.saphyra.util.ObjectMapperWrapper;
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
    private final ObjectMapperWrapper objectMapperWrapper;
    private final UuidConverter uuidConverter;

    @Override
    protected Construction processEntityConversion(ConstructionEntity entity) {
        UUID constructionId = uuidConverter.convertEntity(entity.getConstructionId());
        return Construction.builder()
            .constructionId(constructionId)
            .gameId(uuidConverter.convertEntity(entity.getGameId()))
            .starId(uuidConverter.convertEntity(entity.getStarId()))
            .playerId(uuidConverter.convertEntity(entity.getPlayerId()))
            .constructionRequirements(convertRequirements(
                constructionResourceRequirementDao.getByConstructionId(constructionId),
                entity.getRequiredWorkPoints()
            ))
            .constructionType(entity.getConstructionType())
            .constructionStatus(entity.getConstructionStatus())
            .currentWorkPoints(entity.getCurrentWorkPoints())
            .priority(entity.getPriority())
            .externalId(uuidConverter.convertEntity(entity.getExternalId()))
            .surfaceId(uuidConverter.convertEntity(entity.getSurfaceId()))
            .additionalData(entity.getAdditionalData())
            .addedAt(dateTimeUtil.convertEntity(entity.getAddedAt()))
            .existingResourceRequirements(objectMapperWrapper.readArrayValue(entity.getExistingResourceRequirements(), String[].class))
            .isNew(entity.isNew())
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
            .existingResourceRequirements(objectMapperWrapper.writeValueAsString(domain.getExistingResourceRequirements()))
            .isNew(domain.isNew())
            .build();
    }
}
