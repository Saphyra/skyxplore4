package com.github.saphyra.skyxplore_deprecated.game.dao.map.star;

import com.github.saphyra.skyxplore_deprecated.game.common.interfaces.CommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ResearchCommandService implements CommandService<Research> {
    private final ResearchDao researchDao;

    @Override
    public void delete(Research domain) {
        researchDao.delete(domain);
    }

    @Override
    public void deleteAll(List<Research> domains) {
        researchDao.deleteAll(domains);
    }

    @Override
    public void deleteByGameId(UUID gameId) {
        researchDao.deleteByGameId(gameId);
    }

    @Override
    public void save(Research domain) {
        researchDao.save(domain);
    }

    @Override
    public void saveAll(List<Research> domains) {
        researchDao.saveAll(domains);
    }

    @Override
    public Class<Research> getType() {
        return Research.class;
    }
}
