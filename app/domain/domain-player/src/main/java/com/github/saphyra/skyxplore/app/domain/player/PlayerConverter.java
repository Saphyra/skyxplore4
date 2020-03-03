package com.github.saphyra.skyxplore.app.domain.player;

import org.springframework.stereotype.Component;

import com.github.saphyra.converter.ConverterBase;
import com.github.saphyra.encryption.impl.StringEncryptor;
import com.github.saphyra.skyxplore.app.common.request_context.RequestContextHolder;
import com.github.saphyra.skyxplore.app.common.utils.UuidConverter;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
//TODO unit test
public class PlayerConverter extends ConverterBase<PlayerEntity, Player> {
    private final UuidConverter uuidConverter;
    private final RequestContextHolder requestContextHolder;
    private final StringEncryptor stringEncryptor;

    @Override
    protected Player processEntityConversion(PlayerEntity entity) {
        return Player.builder()
            .playerId(uuidConverter.convertEntity(entity.getPlayerId()))
            .gameId(uuidConverter.convertEntity(entity.getGameId()))
            .ai(entity.isAi())
            .playerName(stringEncryptor.decryptEntity(entity.getPlayerName(), uuidConverter.convertDomain(requestContextHolder.get().getUserId())))
            .isNew(entity.isNew())
            .build();
    }

    @Override
    protected PlayerEntity processDomainConversion(Player domain) {
        return PlayerEntity.builder()
            .playerId(uuidConverter.convertDomain(domain.getPlayerId()))
            .gameId(uuidConverter.convertDomain(domain.getGameId()))
            .ai(domain.isAi())
            .playerName(stringEncryptor.encryptEntity(domain.getPlayerName(), uuidConverter.convertDomain(requestContextHolder.get().getUserId())))
            .isNew(domain.isNew())
            .build();
    }
}