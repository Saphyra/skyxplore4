package com.github.saphyra.skyxplore.game.dao.player;

import com.github.saphyra.skyxplore.game.common.interfaces.CommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PlayerCommandService implements CommandService<Player> {
    private final PlayerDao playerDao;

    @Override
    public void delete(Player domain) {
        playerDao.delete(domain);
    }

    @Override
    public void deleteAll(List<Player> domains) {
        playerDao.deleteAll(domains);
    }

    @Override
    public void deleteByGameId(UUID gameId) {
        playerDao.deleteByGameId(gameId);
    }

    @Override
    public void save(Player domain) {
        playerDao.save(domain);
    }

    @Override
    public void saveAll(List<Player> domains) {
        playerDao.saveAll(domains);
    }

    @Override
    public Class<Player> getType() {
        return Player.class;
    }
}
