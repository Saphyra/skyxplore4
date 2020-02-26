package com.github.saphyra.skyxplore_deprecated.game.dao.system.construction;

import org.springframework.stereotype.Component;

import com.github.saphyra.converter.ConverterBase;
import com.github.saphyra.skyxplore.app.common.utils.UuidConverter;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class ConstructionResourceRequirementConverter extends ConverterBase<ConstructionResourceRequirementEntity, ConstructionResourceRequirement> {
    private final UuidConverter uuidConverter;

    @Override
    protected ConstructionResourceRequirement processEntityConversion(ConstructionResourceRequirementEntity entity) {
        return ConstructionResourceRequirement.builder()
            .constructionResourceRequirementId(uuidConverter.convertEntity(entity.getConstructionResourceRequirementId()))
            .gameId(uuidConverter.convertEntity(entity.getGameId()))
            .constructionId(uuidConverter.convertEntity(entity.getConstructionId()))
            .resourceId(entity.getResourceId())
            .requiredAmount(entity.getRequiredAmount())
            .isNew(entity.isNew())
            .build();
    }

    @Override
    protected ConstructionResourceRequirementEntity processDomainConversion(ConstructionResourceRequirement domain) {
        return ConstructionResourceRequirementEntity.builder()
            .constructionResourceRequirementId(uuidConverter.convertDomain(domain.getConstructionResourceRequirementId()))
            .gameId(uuidConverter.convertDomain(domain.getGameId()))
            .constructionId(uuidConverter.convertDomain(domain.getConstructionId()))
            .resourceId(domain.getResourceId())
            .requiredAmount(domain.getRequiredAmount())
            .isNew(domain.isNew())
            .build();
    }
}
