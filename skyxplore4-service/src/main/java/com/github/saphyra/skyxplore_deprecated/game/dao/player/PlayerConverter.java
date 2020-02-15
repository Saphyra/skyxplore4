package com.github.saphyra.skyxplore_deprecated.game.dao.player;

import com.github.saphyra.converter.ConverterBase;
import com.github.saphyra.encryption.impl.StringEncryptor;
import com.github.saphyra.skyxplore.common.utils.UuidConverter;
import com.github.saphyra.skyxplore_deprecated.common.context.RequestContextHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
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
