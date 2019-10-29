package com.github.saphyra.skyxplore.game.module.player.domain;

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
    protected Player processEntityConversion(PlayerEntity playerEntity) {
        return Player.builder()
            .playerId(uuidConverter.convertEntity(playerEntity.getPlayerId()))
            .gameId(uuidConverter.convertEntity(playerEntity.getGameId()))
            .userId(uuidConverter.convertEntity(playerEntity.getUserId()))
            .ai(playerEntity.isAi())
            .playerName(stringEncryptor.decryptEntity(playerEntity.getPlayerName(), playerEntity.getUserId()))
            .build();
    }

    @Override
    protected PlayerEntity processDomainConversion(Player player) {
        return PlayerEntity.builder()
            .playerId(uuidConverter.convertDomain(player.getPlayerId()))
            .gameId(uuidConverter.convertDomain(player.getGameId()))
            .userId(uuidConverter.convertDomain(player.getUserId()))
            .ai(player.isAi())
            .playerName(stringEncryptor.encryptEntity(player.getPlayerName(), uuidConverter.convertDomain(player.getUserId())))
            .build();
    }
}
