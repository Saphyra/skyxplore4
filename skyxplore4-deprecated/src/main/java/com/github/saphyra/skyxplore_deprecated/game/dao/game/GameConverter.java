package com.github.saphyra.skyxplore_deprecated.game.dao.game;

import org.springframework.stereotype.Component;

import com.github.saphyra.converter.ConverterBase;
import com.github.saphyra.encryption.impl.StringEncryptor;
import com.github.saphyra.skyxplore.app.common.utils.UuidConverter;
import com.github.saphyra.skyxplore_deprecated.common.context.RequestContextHolder;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GameConverter extends ConverterBase<GameEntity, Game> {
    private final RequestContextHolder requestContextHolder;
    private final StringEncryptor stringEncryptor;
    private final UuidConverter uuidConverter;

    @Override
    protected Game processEntityConversion(GameEntity gameEntity) {
        return Game.builder()
            .gameId(uuidConverter.convertEntity(gameEntity.getGameId()))
            .userId(uuidConverter.convertEntity(gameEntity.getUserId()))
            .gameName(stringEncryptor.decryptEntity(gameEntity.getGameName(), uuidConverter.convertDomain(requestContextHolder.get().getUserId())))
            .round(gameEntity.getRound())
            .build();
    }

    @Override
    protected GameEntity processDomainConversion(Game game) {
        return GameEntity.builder()
            .gameId(uuidConverter.convertDomain(game.getGameId()))
            .userId(uuidConverter.convertDomain(game.getUserId()))
            .gameName(stringEncryptor.encryptEntity(game.getGameName(), uuidConverter.convertDomain(requestContextHolder.get().getUserId())))
            .round(game.getRound())
            .build();
    }
}
