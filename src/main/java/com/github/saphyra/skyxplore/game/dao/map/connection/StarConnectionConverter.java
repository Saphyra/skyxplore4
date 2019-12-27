package com.github.saphyra.skyxplore.game.dao.map.connection;

import com.github.saphyra.converter.ConverterBase;
import com.github.saphyra.skyxplore.common.UuidConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class StarConnectionConverter extends ConverterBase<StarConnectionEntity, StarConnection> {
    private final UuidConverter uuidConverter;

    @Override
    protected StarConnection processEntityConversion(StarConnectionEntity entity) {
        return StarConnection.builder()
            .connectionId(uuidConverter.convertEntity(entity.getConnectionId()))
            .gameId(uuidConverter.convertEntity(entity.getGameId()))
            .userId(uuidConverter.convertEntity(entity.getUserId()))
            .star1(uuidConverter.convertEntity(entity.getStar1()))
            .star2(uuidConverter.convertEntity(entity.getStar2()))
            .isNew(entity.isNew())
            .build();
    }

    @Override
    protected StarConnectionEntity processDomainConversion(StarConnection domain) {
        return StarConnectionEntity.builder()
            .connectionId(uuidConverter.convertDomain(domain.getConnectionId()))
            .gameId(uuidConverter.convertDomain(domain.getGameId()))
            .userId(uuidConverter.convertDomain(domain.getUserId()))
            .star1(uuidConverter.convertDomain(domain.getStar1()))
            .star2(uuidConverter.convertDomain(domain.getStar2()))
            .isNew(domain.isNew())
            .build();
    }
}
