package com.github.saphyra.skyxplore_deprecated.game.dao.system.storage.resource;

import com.github.saphyra.skyxplore_deprecated.common.context.RequestContext;
import com.github.saphyra.skyxplore_deprecated.common.context.RequestContextHolder;
import com.github.saphyra.skyxplore_deprecated.game.common.interfaces.CommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResourceCommandService implements CommandService<Resource> {
    private final RequestContextHolder requestContextHolder;
    private final ResourceDao resourceDao;

    @Override
    public void delete(Resource domain) {
        resourceDao.delete(domain);
    }

    @Override
    public void deleteAll(List<Resource> domains) {
        resourceDao.deleteAll(domains);
    }

    @Override
    public void deleteByGameId(UUID gameId) {
        resourceDao.deleteByGameId(gameId);
    }

    public void deleteExpiredByGameId(int expiration) {
        RequestContext context = requestContextHolder.get();
        UUID gameId = context.getGameId();
        resourceDao.deleteExpiredByGameId(gameId, expiration);
    }

    @Override
    public void save(Resource domain) {
        log.info("Saving Resource {}", domain);
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
