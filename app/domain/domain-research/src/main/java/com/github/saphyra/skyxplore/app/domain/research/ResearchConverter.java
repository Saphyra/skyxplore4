package com.github.saphyra.skyxplore.app.domain.research;

import org.springframework.stereotype.Component;

import com.github.saphyra.converter.ConverterBase;
import com.github.saphyra.skyxplore.app.common.utils.UuidConverter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
//TODO unit test
public class ResearchConverter extends ConverterBase<ResearchEntity, Research> {
    private final UuidConverter uuidConverter;

    @Override
    protected Research processEntityConversion(ResearchEntity entity) {
        return Research.builder()
            .researchId(uuidConverter.convertEntity(entity.getResearchId()))
            .starId(uuidConverter.convertEntity(entity.getStarId()))
            .gameId(uuidConverter.convertEntity(entity.getGameId()))
            .playerId(uuidConverter.convertEntity(entity.getPlayerId()))
            .dataId(entity.getDataId())
            .isNew(entity.isNew())
            .build();
    }

    @Override
    protected ResearchEntity processDomainConversion(Research domain) {
        return ResearchEntity.builder()
            .researchId(uuidConverter.convertDomain(domain.getResearchId()))
            .starId(uuidConverter.convertDomain(domain.getStarId()))
            .gameId(uuidConverter.convertDomain(domain.getGameId()))
            .playerId(uuidConverter.convertDomain(domain.getPlayerId()))
            .dataId(domain.getDataId())
            .isNew(domain.isNew())
            .build();
    }
}
