package com.github.saphyra.skyxplore.game.service.game.deletion;

import com.github.saphyra.skyxplore.common.ExceptionFactory;
import com.github.saphyra.skyxplore.common.UuidConverter;
import com.github.saphyra.skyxplore.common.event.GameDeletedEvent;
import com.github.saphyra.skyxplore.game.dao.game.Game;
import com.github.saphyra.skyxplore.game.dao.game.GameDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class GameDeletionService {
    private final ApplicationEventPublisher eventPublisher;
    private final GameDao gameDao;
    private final UuidConverter uuidConverter;

    public void deleteByGameIdAndUserId(UUID gameId, UUID userId) {
        Optional<Game> gameOptional = gameDao.findById(uuidConverter.convertDomain(gameId));
        if (gameOptional.isPresent()) {
            Game game = gameOptional.get();
            if (!game.getUserId().equals(userId)) {
                throw ExceptionFactory.invalidGameAccess(userId, gameId);
            }
            gameDao.delete(game);
            eventPublisher.publishEvent(new GameDeletedEvent(userId, gameId));
        } else {
            throw ExceptionFactory.gameNotFound(gameId);
        }
        log.info("Game with id {} is deleted.", gameId);
    }
}
