package com.github.saphyra.skyxplore.app.common.dao.cache_repository.component;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Persistable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.app.common.dao.cache_repository.CacheRepository;
import com.github.saphyra.skyxplore.app.common.dao.cache_repository.SettablePersistable;
import com.github.saphyra.skyxplore.app.common.utils.CollectionUtil;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
//TODO unit test
public class DeleteAllComponent {
    public <KEY, ENTITY extends SettablePersistable<ID>, ID, REPOSITORY extends CrudRepository<ENTITY, ID>> void deleteAll(CacheRepository<KEY, ENTITY, ID, REPOSITORY> repository, Iterable<? extends ENTITY> iterable) {
        List<ID> ids = CollectionUtil.toList(iterable)
            .stream()
            .map(Persistable::getId)
            .collect(Collectors.toList());

        log.debug("Deleting entities {} with id {}", repository.getEntityName(), ids);

        repository.getCacheMap().values()
            .forEach(expirableMap -> {
                boolean elementWasRemoved = expirableMap.getEntity().entrySet().removeIf(entry -> ids.contains(entry.getKey()));
                if (elementWasRemoved) {
                    expirableMap.updateLastAccess();
                }
            });
        repository.getDeleteQueue().addAll(ids);
    }
}
