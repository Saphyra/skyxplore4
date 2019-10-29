package com.github.saphyra.skyxplore.game.module.map.connection;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.common.event.GameDeletedEvent;
import com.github.saphyra.skyxplore.game.module.map.connection.domain.StarConnectionDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConnectionService {
    private final StarConnectionDao starConnectionDao;

    @EventListener
    void gameDeletedEventListener(GameDeletedEvent event){
        log.debug("Deleting connections related to game {}", event);
        starConnectionDao.deleteByGameIdAndUserId(event.getGameId(), event.getUserId());
    }
}
