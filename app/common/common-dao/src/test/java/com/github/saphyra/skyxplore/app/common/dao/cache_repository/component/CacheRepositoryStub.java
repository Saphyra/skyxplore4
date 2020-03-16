package com.github.saphyra.skyxplore.app.common.dao.cache_repository.component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.github.saphyra.skyxplore.app.common.dao.cache_repository.CacheContext;
import com.github.saphyra.skyxplore.app.common.dao.cache_repository.CacheRepository;

public class CacheRepositoryStub extends CacheRepository<UUID, EntityStub, UUID, CrudRepository<EntityStub, UUID>> {
    public CacheRepositoryStub(CrudRepository<EntityStub, UUID> repository, CacheContext cacheContext) {
        super(repository, EntityStub::getKey, cacheContext);
    }

    @Override
    public List<EntityStub> getByKey(UUID key) {
        List<EntityStub> result = new ArrayList<>();
        for (EntityStub entityStub : repository.findAll()) {
            if (entityStub.getKey().equals(key)) {
                result.add(entityStub);
            }
        }
        return result;
    }

    @Override
    public void deleteByIds(List<UUID> ids) {
        ids.forEach(repository::deleteById);
    }
}
