package com.github.saphyra.skyxplore.game.dao.system.construction;

import com.github.saphyra.converter.ConverterBase;
import com.github.saphyra.skyxplore.common.UuidConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
            .build();
    }
}
