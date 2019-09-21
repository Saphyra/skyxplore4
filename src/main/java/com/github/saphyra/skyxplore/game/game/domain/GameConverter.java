package com.github.saphyra.skyxplore.game.game.domain;

import com.github.saphyra.converter.ConverterBase;
import com.github.saphyra.encryption.impl.StringEncryptor;
import com.github.saphyra.skyxplore.common.UuidConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GameConverter extends ConverterBase<GameEntity, Game> {
    private final StringEncryptor stringEncryptor;
    private final UuidConverter uuidConverter;

    @Override
    protected Game processEntityConversion(GameEntity gameEntity) {
        return Game.builder()
            .gameId(uuidConverter.convertEntity(gameEntity.getGameId()))
            .userId(uuidConverter.convertEntity(gameEntity.getUserId()))
            .gameName(stringEncryptor.decryptEntity(gameEntity.getGameName(), gameEntity.getUserId()))
            .build();
    }

    @Override
    protected GameEntity processDomainConversion(Game game) {
        return GameEntity.builder()
            .gameId(uuidConverter.convertDomain(game.getGameId()))
            .userId(uuidConverter.convertDomain(game.getUserId()))
            .gameName(stringEncryptor.encryptEntity(game.getGameName(), game.getUserId().toString()))
            .build();
    }
}
