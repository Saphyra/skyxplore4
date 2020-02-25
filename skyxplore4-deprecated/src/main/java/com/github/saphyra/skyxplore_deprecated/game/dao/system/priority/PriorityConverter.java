package com.github.saphyra.skyxplore_deprecated.game.dao.system.priority;

import com.github.saphyra.converter.ConverterBase;
import com.github.saphyra.skyxplore.app.common.utils.UuidConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PriorityConverter extends ConverterBase<PriorityEntity, Priority> {
    private final UuidConverter uuidConverter;

    @Override
    protected Priority processEntityConversion(PriorityEntity entity) {
        return Priority.builder()
            .starId(uuidConverter.convertEntity(entity.getId().getStarId()))
            .type(entity.getId().getType())
            .gameId(uuidConverter.convertEntity(entity.getGameId()))
            .playerId(uuidConverter.convertEntity(entity.getPlayerId()))
            .value(entity.getValue())
            .isNew(entity.isNew())
            .build();
    }

    @Override
    protected PriorityEntity processDomainConversion(Priority domain) {
        return PriorityEntity.builder()
            .id(new PriorityEntityId(
                uuidConverter.convertDomain(domain.getStarId()),
                domain.getType()
            ))
            .gameId(uuidConverter.convertDomain(domain.getGameId()))
            .playerId(uuidConverter.convertDomain(domain.getPlayerId()))
            .value(domain.getValue())
            .isNew(domain.isNew())
            .build();
    }
}
