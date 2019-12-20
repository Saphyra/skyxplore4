package com.github.saphyra.skyxplore.game.service.map.star;

import com.github.saphyra.skyxplore.common.event.GameDeletedEvent;
import com.github.saphyra.skyxplore.game.dao.map.star.StarCommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
class StarService {
    private final StarCommandService starCommandService;

    @EventListener
    void gameDeletedEventListener(GameDeletedEvent event) {
        log.debug("Deleting stars related to game {}", event);
        starCommandService.deleteByGameIdAndUserId(event.getGameId(), event.getUserId());
    }
}
