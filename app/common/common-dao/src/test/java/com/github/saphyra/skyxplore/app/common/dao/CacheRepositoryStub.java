package com.github.saphyra.skyxplore.app.common.dao;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CacheRepositoryStub extends CacheRepository<UUID, EntityStub, UUID, CrudRepository<EntityStub, UUID>> implements CrudRepository<EntityStub, UUID> {

    protected CacheRepositoryStub(CrudRepository<EntityStub, UUID> repository, CacheContext cacheContext) {
        super(repository, EntityStub::getKey, cacheContext);
    }

    @Override
    protected List<EntityStub> getByKey(UUID key) {
        Iterable<EntityStub> all = repository.findAll();
        List<EntityStub> result = StreamSupport.stream(all.spliterator(), false)
            .filter(entityStub -> entityStub.getKey().equals(key))
            .collect(Collectors.toList());
        return result;
    }

    @Override
    protected void deleteByIds(List<UUID> ids) {
        ids.forEach(repository::deleteById);
    }
}
