package com.github.saphyra.skyxplore.app.service.game_creation.player;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.app.common.service.DomainSaverService;
import com.github.saphyra.skyxplore.app.domain.player.Player;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class PlayerCreationService {
    private final DomainSaverService domainSaverService;
    private final IdGenerator idGenerator;
    private final PlayerNameProvider playerNameProvider;

    public Player create(UUID gameId, UUID userId, boolean isAi, List<String> usedPlayerNames) {
        Player player = Player.builder()
            .playerId(idGenerator.randomUUID())
            .gameId(gameId)
            .ai(isAi)
            .playerName(playerNameProvider.getPlayerName(isAi, userId, usedPlayerNames))
            .isNew(true)
            .build();

        domainSaverService.add(player);
        log.debug("Player created: {}", player);
        return player;
    }
}
