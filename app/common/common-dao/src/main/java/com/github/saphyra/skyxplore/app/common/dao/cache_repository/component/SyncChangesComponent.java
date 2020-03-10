package com.github.saphyra.skyxplore.app.common.dao.cache_repository.component;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.app.common.dao.cache_repository.CacheRepository;
import com.github.saphyra.skyxplore.app.common.dao.cache_repository.EntityMapping;
import com.github.saphyra.skyxplore.app.common.dao.cache_repository.ModifiableEntity;
import com.github.saphyra.skyxplore.app.common.dao.cache_repository.SettablePersistable;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
//TODO unit test
public class SyncChangesComponent {

    public <KEY, ENTITY extends SettablePersistable<ID>, ID, REPOSITORY extends CrudRepository<ENTITY, ID>> void syncChanges(CacheRepository<KEY, ENTITY, ID, REPOSITORY> repository) {
        log.info("Synchronizing modifications for entity {}...", repository.getEntityName());
        int synchedEntitiesAmount = repository.getCacheMap().values()
            .stream()
            .map(map -> {
                //noinspection SynchronizationOnLocalVariableOrMethodParameter
                synchronized (map) {
                    List<ENTITY> entities = filterModified(map.getEntity());
                    repository.saveAll(entities);
                    entities.forEach(entity -> entity.setNew(false));
                    return entities.size();
                }
            })
            .mapToInt(Integer::intValue)
            .sum();
        log.info("Synchronization finished for entity {}. Updated entities: {}", repository.getEntityName(), synchedEntitiesAmount);
    }

    private <ID, ENTITY> List<ENTITY> filterModified(EntityMapping<ID, ENTITY> map) {
        return map.values()
            .stream()
            .filter(ModifiableEntity::isModified)
            .map(ModifiableEntity::getEntity)
            .collect(Collectors.toList());
    }
}
