package com.github.saphyra.skyxplore.app.service.game_creation.star_connection;

import com.github.saphyra.skyxplore.app.domain.star.Star;
import com.github.saphyra.skyxplore.app.domain.star_connection.StarConnection;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
//TODO unit test
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
