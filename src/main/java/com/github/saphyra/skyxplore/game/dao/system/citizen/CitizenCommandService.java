package com.github.saphyra.skyxplore.game.dao.system.citizen;

import com.github.saphyra.skyxplore.common.event.GameDeletedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
@Slf4j
public class CitizenCommandService {
    private final CitizenDao citizenDao;

    @EventListener
    void gameDeletedEventListener(GameDeletedEvent event) {
        log.info("Deleting citizens based on {}", event);
        citizenDao.deleteByGameIdAndUserId(event.getGameId(), event.getUserId());
    }

    public void saveAll(List<Citizen> citizens) {
        citizenDao.saveAll(citizens);
    }
}
