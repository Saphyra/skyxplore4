package com.github.saphyra.skyxplore.game.service.game.deletion;

import com.github.saphyra.skyxplore.common.ExceptionFactory;
import com.github.saphyra.skyxplore.common.context.RequestContextHolder;
import com.github.saphyra.skyxplore.game.common.interfaces.DeletableByGameId;
import com.github.saphyra.skyxplore.game.dao.game.Game;
import com.github.saphyra.skyxplore.game.dao.game.GameQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class GameDeletionService {
    private final List<DeletableByGameId> deletables;
    private final GameQueryService gameQueryService;
    private final RequestContextHolder requestContextHolder;

    public void deleteByGameIdAndUserId(UUID gameId) {
        Optional<Game> gameOptional = gameQueryService.findByGameIdAndUserId(gameId);
        if (gameOptional.isPresent()) {
            deletables.forEach(deletableByGameId -> deletableByGameId.deleteByGameId(gameId));
            log.info("Game with id {} is deleted.", gameId);
        } else {
            throw ExceptionFactory.gameNotFound(gameId, requestContextHolder.get().getUserId());
        }
    }
}
