package com.github.saphyra.skyxplore.game.module.system.citizen;

import com.github.saphyra.skyxplore.common.event.GameDeletedEvent;
import com.github.saphyra.skyxplore.game.module.system.citizen.domain.CitizenDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CitizenService {
    private final CitizenDao citizenDao;

    @EventListener
    void gameDeletedEventListener(GameDeletedEvent event) {
        log.info("Deleting citizens based on {}", event);
        citizenDao.deleteByGameIdAndUserId(event.getGameId(), event.getUserId());
    }
}