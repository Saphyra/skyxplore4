package com.github.saphyra.skyxplore.game.dao.system.storage.resource;

import com.github.saphyra.skyxplore.game.common.interfaces.CommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResourceCommandService implements CommandService<Resource> {
    private final ResourceDao resourceDao;

    @Override
    public void delete(Resource domain) {
        resourceDao.delete(domain);
    }

    @Override
    public void deleteByGameId(UUID gameId) {
        resourceDao.deleteByGameId(gameId);
    }

    @Override
    public void save(Resource domain) {
        resourceDao.save(domain);
    }

    @Override
    public void saveAll(List<Resource> domains) {
        resourceDao.saveAll(domains);
    }

    @Override
    public Class<Resource> getType() {
        return Resource.class;
    }
}
