package com.github.saphyra.skyxplore.game.dao.system.storage.resource;

import com.github.saphyra.skyxplore.common.event.GameDeletedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResourceCommandService {
    private final ResourceDao resourceDao;

    @EventListener
    void gameDeletedEventListener(GameDeletedEvent event) {
        log.info("Deleting resources based on {}", event);
        resourceDao.deleteByGameIdAndUserId(event.getGameId(), event.getUserId());
    }
}
