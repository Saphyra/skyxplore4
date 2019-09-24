package com.github.saphyra.skyxplore.game.map.connection;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.common.event.GameDeletedEvent;
import com.github.saphyra.skyxplore.game.map.connection.domain.StarConnectionDao;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConnectionService {
    private final StarConnectionDao starConnectionDao;

    @EventListener
    void gameDeletedEventListener(GameDeletedEvent event){
        starConnectionDao.deleteByGameIdAndUserId(event.getGameId(), event.getUserId());
    }
}
