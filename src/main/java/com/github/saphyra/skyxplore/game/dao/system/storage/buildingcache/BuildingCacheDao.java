package com.github.saphyra.skyxplore.game.dao.system.storage.buildingcache;

import com.github.saphyra.dao.AbstractDao;
import com.github.saphyra.skyxplore.common.UuidConverter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
class BuildingCacheDao extends AbstractDao<BuildingCacheEntity, BuildingCache, String, BuildingCacheRepository> {
    private final UuidConverter uuidConverter;

    public BuildingCacheDao(BuildingCacheConverter converter, BuildingCacheRepository repository, UuidConverter uuidConverter) {
        super(converter, repository);
        this.uuidConverter = uuidConverter;
    }

    public void deleteByGameId(UUID gameId) {
        repository.deleteByGameId(uuidConverter.convertDomain(gameId));
    }
}
