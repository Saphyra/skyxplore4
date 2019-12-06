package com.github.saphyra.skyxplore.game.dao.map.star;

import org.springframework.stereotype.Component;

import com.github.saphyra.converter.ConverterBase;
import com.github.saphyra.skyxplore.common.UuidConverter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class ResearchConverter extends ConverterBase<ResearchEntity, Research> {
    private final UuidConverter uuidConverter;

    @Override
    protected Research processEntityConversion(ResearchEntity entity) {
        return Research.builder()
            .researchId(uuidConverter.convertEntity(entity.getResearchId()))
            .starId(uuidConverter.convertEntity(entity.getStarId()))
            .dataId(entity.getDataId())
            .build();
    }

    @Override
    protected ResearchEntity processDomainConversion(Research domain) {
        return ResearchEntity.builder()
            .researchId(uuidConverter.convertDomain(domain.getResearchId()))
            .starId(uuidConverter.convertDomain(domain.getStarId()))
            .dataId(domain.getDataId())
            .build();
    }
}
