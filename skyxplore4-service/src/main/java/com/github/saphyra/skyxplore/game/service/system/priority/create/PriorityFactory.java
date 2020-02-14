package com.github.saphyra.skyxplore.game.service.system.priority.create;

import com.github.saphyra.skyxplore.game.dao.map.star.Star;
import com.github.saphyra.skyxplore.game.dao.system.priority.Priority;
import com.github.saphyra.skyxplore.game.dao.system.priority.PriorityType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class PriorityFactory {
    private static final Integer DEFAULT_PRIORITY = 5;

    Priority create(Star star, PriorityType priorityType) {
        return Priority.builder()
            .starId(star.getStarId())
            .type(priorityType)
            .gameId(star.getGameId())
            .playerId(star.getOwnerId())
            .value(DEFAULT_PRIORITY)
            .isNew(true)
            .build();
    }
}
