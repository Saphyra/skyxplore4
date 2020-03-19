package com.github.saphyra.skyxplore.app.service.game_creation.player;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.app.domain.player.Player;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
class PlayerFactory {
    private final IdGenerator idGenerator;
    private final PlayerNameProvider playerNameProvider;

    Player create(UUID gameId, UUID userId, boolean isAi, List<String> usedPlayerNames){
        return Player.builder()
            .playerId(idGenerator.randomUUID())
            .gameId(gameId)
            .ai(isAi)
            .playerName(playerNameProvider.getPlayerName(isAi, userId, usedPlayerNames))
            .isNew(true)
            .build();
    }
}
