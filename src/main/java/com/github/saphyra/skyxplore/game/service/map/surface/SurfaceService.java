package com.github.saphyra.skyxplore.game.service.map.surface;

import com.github.saphyra.skyxplore.game.dao.map.surface.SurfaceCommandService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.common.event.GameDeletedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SurfaceService {
    private final SurfaceCommandService surfaceCommandService;

    @EventListener
    void gameDeletedEventListener(GameDeletedEvent event){
        log.debug("Deleting surfaces related to game {}", event);
        surfaceCommandService.deleteByGameIdAndUserId(event.getGameId(), event.getUserId());
    }
}
