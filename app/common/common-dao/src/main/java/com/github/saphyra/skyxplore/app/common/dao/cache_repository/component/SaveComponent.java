package com.github.saphyra.skyxplore.app.common.dao.cache_repository.component;

import com.github.saphyra.skyxplore.app.common.dao.cache_repository.CacheContext;
import com.github.saphyra.skyxplore.app.common.dao.cache_repository.CacheRepository;
import com.github.saphyra.skyxplore.app.common.dao.cache_repository.EntityMapping;
import com.github.saphyra.skyxplore.app.common.dao.cache_repository.ModifiableEntity;
import com.github.saphyra.skyxplore.app.common.dao.cache_repository.SettablePersistable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.function.Function;

@Component
@Slf4j
@RequiredArgsConstructor
public class SaveComponent {
    private final GetEntityMappingComponent getEntityMappingComponent;

    public <KEY, ENTITY extends SettablePersistable<ID>, ID, REPOSITORY extends CrudRepository<ENTITY, ID>, S extends ENTITY> S save(CacheRepository<KEY, ENTITY, ID, REPOSITORY> repository, Function<ENTITY, KEY> keyMapper, S entity, CacheContext cacheContext) {
        log.debug("Saving entity {}", entity);
        KEY key = keyMapper.apply(entity);
        EntityMapping<ID, ENTITY> entities = getEntityMappingComponent.getEntityMapping(repository, key, cacheContext);
        entities.put(Objects.requireNonNull(entity.getId()), new ModifiableEntity<>(entity, true));
        repository.getDeleteQueue().remove(entity.getId());
        return entity;
    }
}
