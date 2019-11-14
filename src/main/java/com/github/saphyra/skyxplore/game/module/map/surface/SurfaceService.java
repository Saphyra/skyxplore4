package com.github.saphyra.skyxplore.game.module.map.surface;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.common.event.GameDeletedEvent;
import com.github.saphyra.skyxplore.game.dao.map.surface.SurfaceDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SurfaceService {
    private final SurfaceDao surfaceDao;

    @EventListener
    void gameDeletedEventListener(GameDeletedEvent event){
        log.debug("Deleting surfaces related to game {}", event);
        surfaceDao.deleteByGameIdAndUserId(event.getGameId(), event.getUserId());
    }
}
