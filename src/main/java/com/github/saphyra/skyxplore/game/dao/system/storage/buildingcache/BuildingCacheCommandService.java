package com.github.saphyra.skyxplore.game.dao.system.storage.buildingcache;

import com.github.saphyra.skyxplore.game.common.interfaces.CommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
public class BuildingCacheCommandService implements CommandService<BuildingCache> {
    private final BuildingCacheDao buildingCacheDao;

    @Override
    public void delete(BuildingCache domain) {
        buildingCacheDao.delete(domain);
    }

    @Override
    public void deleteAll(List<BuildingCache> domains) {
        buildingCacheDao.deleteAll(domains);
    }

    @Override
    public void deleteByGameId(UUID gameId) {
        buildingCacheDao.deleteByGameId(gameId);
    }

    @Override
    public void save(BuildingCache domain) {
        buildingCacheDao.save(domain);
    }

    @Override
    public void saveAll(List<BuildingCache> domains) {
        buildingCacheDao.saveAll(domains);
    }

    @Override
    public Class<BuildingCache> getType() {
        return BuildingCache.class;
    }
}
