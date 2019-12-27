package com.github.saphyra.skyxplore.game.service.map.connection.creation;

import com.github.saphyra.skyxplore.game.dao.map.connection.StarConnection;
import com.github.saphyra.skyxplore.game.dao.map.star.Star;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
class StarConnectionFactory {
    private final IdGenerator idGenerator;

    StarConnection createConnection(Star star, Star targetStar) {
        log.debug("Creating connection between stars {}, {}", star, targetStar);
        StarConnection starConnection = StarConnection.builder()
            .connectionId(idGenerator.randomUUID())
            .gameId(star.getGameId())
            .star1(star.getStarId())
            .star2(targetStar.getStarId())
            .isNew(true)
            .build();
        log.debug("StarConnection created: {}", starConnection);
        return starConnection;
    }
}
