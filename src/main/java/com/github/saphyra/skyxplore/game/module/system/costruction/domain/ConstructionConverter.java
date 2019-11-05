package com.github.saphyra.skyxplore.game.module.system.costruction.domain;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.github.saphyra.converter.ConverterBase;
import com.github.saphyra.skyxplore.common.UuidConverter;
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
            .resourceRequirements(new HashMap<>(constructionEntity.getResourceRequirements()))
            .priority(constructionEntity.getPriority())
            .build();
    }

    @Override
    protected ConstructionEntity processDomainConversion(Construction domain) {
        return ConstructionEntity.builder()
            .constructionId(uuidConverter.convertDomain(domain.getConstructionId()))
            .gameId(uuidConverter.convertDomain(domain.getGameId()))
            .userId(uuidConverter.convertDomain(domain.getUserId()))
            .resourceRequirements(new HashMap<>(domain.getResourceRequirements()))
            .priority(domain.getPriority())
            .build();
    }
}
