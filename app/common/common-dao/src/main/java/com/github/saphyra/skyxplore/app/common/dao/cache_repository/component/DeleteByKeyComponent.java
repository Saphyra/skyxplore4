package com.github.saphyra.skyxplore.app.common.dao.cache_repository.component;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.app.common.dao.cache_repository.CacheRepository;
import com.github.saphyra.skyxplore.app.common.dao.cache_repository.SettablePersistable;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DeleteByKeyComponent {
    public <KEY, ENTITY extends SettablePersistable<ID>, ID, REPOSITORY extends CrudRepository<ENTITY, ID>> void deleteByKey(CacheRepository<KEY, ENTITY, ID, REPOSITORY> repository, KEY key) {
        log.debug("Deleting {}(s) for key {}", repository.getEntityName(), key);
        List<ENTITY> entities = repository.getByKey(key);
        log.debug("Number of {}(s) to delete: {}", entities, entities.size());
        repository.deleteAll(entities);
    }
}
