package com.github.saphyra.skyxplore.game.dao.map.connection;

import com.github.saphyra.skyxplore.game.common.interfaces.CommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class StarConnectionCommandService implements CommandService<StarConnection> {
    private final StarConnectionDao starConnectionDao;

    @Override
    public void delete(StarConnection domain) {
        starConnectionDao.delete(domain);
    }

    @Override
    public void deleteByGameId(UUID gameId) {
        starConnectionDao.deleteByGameId(gameId);
    }

    @Override
    public void save(StarConnection domain) {
        starConnectionDao.save(domain);
    }

    @Override
    public void saveAll(List<StarConnection> domains) {
        starConnectionDao.saveAll(domains);
    }

    @Override
    public Class<StarConnection> getType() {
        return StarConnection.class;
    }
}
