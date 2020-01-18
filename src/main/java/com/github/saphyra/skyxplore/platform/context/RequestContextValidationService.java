package com.github.saphyra.skyxplore.platform.context;

import com.github.saphyra.skyxplore.common.context.RequestContext;
import com.github.saphyra.skyxplore.game.dao.game.GameQueryService;
import com.github.saphyra.skyxplore.game.dao.player.PlayerQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
class RequestContextValidationService {
    private final GameQueryService gameQueryService;
    private final PlayerQueryService playerQueryService;

    void validateContext(RequestContext context) {
        if (context.isGameIdPresent()) {
            if (!gameQueryService.findByGameIdAndUserId(context.getGameId()).isPresent()) {
                throw new InvalidContextException("Invalid gameId");
            }
        }

        if (context.isPlayerIdPresent()) {
            if (!context.isGameIdPresent()) {
                throw new InvalidContextException("GameId is mandatory when playerId is present.");
            }

            if (!playerQueryService.findByGameIdAndPlayerId().isPresent()){
                throw new InvalidContextException("Invalid playerId");
            }
        }
    }
}
