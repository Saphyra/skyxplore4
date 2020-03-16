package com.github.saphyra.skyxplore.app.domain.star;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.app.common.game_context.CommandService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
//TODO unit test
public class StarCommandService implements CommandService<Star> {
    private final StarDao starDao;

    @Override
    public void delete(Star domain) {
        starDao.delete(domain);
    }

    @Override
    public void deleteAll(List<Star> domains) {
        starDao.deleteAll(domains);
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
