package com.github.saphyra.skyxplore.game.dao.player;

import org.springframework.stereotype.Component;

import com.github.saphyra.converter.ConverterBase;
import com.github.saphyra.encryption.impl.StringEncryptor;
import com.github.saphyra.skyxplore.common.UuidConverter;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PlayerConverter extends ConverterBase<PlayerEntity, Player> {
    private final UuidConverter uuidConverter;
    private final StringEncryptor stringEncryptor;

    @Override
    protected Player processEntityConversion(PlayerEntity entity) {
        return Player.builder()
            .playerId(uuidConverter.convertEntity(entity.getPlayerId()))
            .gameId(uuidConverter.convertEntity(entity.getGameId()))
            .userId(uuidConverter.convertEntity(entity.getUserId()))
            .ai(entity.isAi())
            .playerName(stringEncryptor.decryptEntity(entity.getPlayerName(), entity.getUserId()))
            .isNew(entity.isNew())
            .build();
    }

    @Override
    protected PlayerEntity processDomainConversion(Player domain) {
        return PlayerEntity.builder()
            .playerId(uuidConverter.convertDomain(domain.getPlayerId()))
            .gameId(uuidConverter.convertDomain(domain.getGameId()))
            .userId(uuidConverter.convertDomain(domain.getUserId()))
            .ai(domain.isAi())
            .playerName(stringEncryptor.encryptEntity(domain.getPlayerName(), uuidConverter.convertDomain(domain.getUserId())))
            .isNew(domain.isNew())
            .build();
    }
}
