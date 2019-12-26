package com.github.saphyra.skyxplore.game.service.map.connection;

import com.github.saphyra.skyxplore.game.dao.map.connection.StarConnectionCommandService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.common.event.GameDeletedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConnectionService {
    private final StarConnectionCommandService starConnectionCommandService;

    @EventListener
    void gameDeletedEventListener(GameDeletedEvent event) {
        log.info("Deleting connections related to game {}", event);
        starConnectionCommandService.deleteByGameIdAndUserId(event.getGameId(), event.getUserId());
    }
}
