package com.github.saphyra.skyxplore.game.dao.map.star;

import com.github.saphyra.skyxplore.game.common.interfaces.CommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class StarCommandService implements CommandService<Star> {
    private final StarDao starDao;

    @Override
    public void delete(Star domain) {
        starDao.delete(domain);
    }

    @Override
    public void deleteByGameId(UUID gameId) {
        starDao.deleteByGameId(gameId);
    }

    @Override
    public void save(Star domain) {
        starDao.save(domain);
    }

    @Override
    public void saveAll(List<Star> domains) {
        starDao.saveAll(domains);
    }

    @Override
    public Class<Star> getType() {
        return Star.class;
    }
}
