package com.github.saphyra.skyxplore.game.module.system.resource;

import com.github.saphyra.skyxplore.common.event.GameDeletedEvent;
import com.github.saphyra.skyxplore.game.module.system.resource.domain.ResourceDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResourceService {
    private final ResourceDao resourceDao;

    @EventListener
    void gameDeletedEventListener(GameDeletedEvent event) {
        log.info("Deleting resources based on {}", event);
        resourceDao.deleteByGameIdAndUserId(event.getGameId(), event.getUserId());
    }
}
