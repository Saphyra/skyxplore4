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
public class SyncChangesComponent {

    public <KEY, ENTITY extends SettablePersistable<ID>, ID, REPOSITORY extends CrudRepository<ENTITY, ID>> void syncChanges(CacheRepository<KEY, ENTITY, ID, REPOSITORY> repository) {
        log.info("Synchronizing modifications for entity {}...", repository.getEntityName());
        int synchedEntitiesAmount = repository.getCacheMap().values()
            .stream()
            .map(expirableEntity -> {
                //noinspection SynchronizationOnLocalVariableOrMethodParameter
                synchronized (expirableEntity) {
                    List<ModifiableEntity<ENTITY>> modifiableEntities = filterModified(expirableEntity.getEntity());
                    List<ENTITY> entities = modifiableEntities.stream()
                        .map(ModifiableEntity::getEntity)
                        .collect(Collectors.toList());
                    repository.saveAll(entities);
                    entities.forEach(entity -> entity.setNew(false));
                    modifiableEntities.forEach(modifiableEntity -> modifiableEntity.setModified(false));
                    return entities.size();
                }
            })
            .mapToInt(Integer::intValue)
            .sum();
        log.info("Synchronization finished for entity {}. Updated entities: {}", repository.getEntityName(), synchedEntitiesAmount);
    }

    private <ID, ENTITY> List<ModifiableEntity<ENTITY>> filterModified(EntityMapping<ID, ENTITY> map) {
        return map.values()
            .stream()
            .filter(ModifiableEntity::isModified)
            .collect(Collectors.toList());
    }
}
