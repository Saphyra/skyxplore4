package com.github.saphyra.skyxplore.game.dao.player;

import com.github.saphyra.skyxplore.common.event.GameDeletedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PlayerCommandService {
    private final PlayerDao playerDao;

    @EventListener
    void gameDeletedEventListener(GameDeletedEvent event) {
        log.info("Deleting players for event {}", event);
        playerDao.deleteByGameIdAndUserId(event.getGameId(), event.getUserId());
    }

    public void save(Player player) {
        playerDao.save(player);
    }
}
