package com.github.saphyra.skyxplore.game.dao.player;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PlayerCommandService {
    private final PlayerDao playerDao;

    public void save(Player player) {
        playerDao.save(player);
    }
}
