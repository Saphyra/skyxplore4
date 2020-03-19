package com.github.saphyra.skyxplore.app.domain.star_connection;

import com.github.saphyra.skyxplore.app.common.game_context.CommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
//TODO unit test
public class StarConnectionCommandService implements CommandService<StarConnection> {
    private final StarConnectionDao starConnectionDao;

    @Override
    public void delete(StarConnection domain) {
        starConnectionDao.delete(domain);
    }

    @Override
    public void deleteAll(List<StarConnection> domains) {
        starConnectionDao.deleteAll(domains);
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
