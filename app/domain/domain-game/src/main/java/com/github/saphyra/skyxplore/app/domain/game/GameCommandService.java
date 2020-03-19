package com.github.saphyra.skyxplore.app.domain.game;

import com.github.saphyra.skyxplore.app.common.game_context.CommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class GameCommandService implements CommandService<Game> {
    private final GameDao gameDao;

    @Override
    public void delete(Game domain) {
        gameDao.delete(domain);
    }

    @Override
    public void deleteAll(List<Game> domains) {
        gameDao.deleteAll(domains);
    }

    @Override
    public void deleteByGameId(UUID gameId) {

    }

    public void save(Game game) {
        gameDao.save(game);
    }

    @Override
    public void saveAll(List<Game> domains) {
        gameDao.saveAll(domains);
    }

    @Override
    public Class<Game> getType() {
        return Game.class;
    }
}
