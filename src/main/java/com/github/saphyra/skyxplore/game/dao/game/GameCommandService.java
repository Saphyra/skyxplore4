package com.github.saphyra.skyxplore.game.dao.game;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class GameCommandService {
    private final GameDao gameDao;

    public void save(Game game) {
        gameDao.save(game);
    }
}
