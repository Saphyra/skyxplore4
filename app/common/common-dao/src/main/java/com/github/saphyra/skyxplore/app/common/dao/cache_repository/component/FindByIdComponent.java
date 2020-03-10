package com.github.saphyra.skyxplore.app.common.dao.cache_repository.component;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.app.common.dao.cache_repository.CacheContext;
import com.github.saphyra.skyxplore.app.common.dao.cache_repository.CacheMap;
import com.github.saphyra.skyxplore.app.common.dao.cache_repository.CacheRepository;
import com.github.saphyra.skyxplore.app.common.dao.cache_repository.ExpirableEntity;
import com.github.saphyra.skyxplore.app.common.dao.cache_repository.SettablePersistable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
//TODO unit test
public class FindByIdComponent {
    private final ExtractEntitiesComponent extractEntitiesComponent;
    private final SearchInRepositoryComponent searchInRepository;

    public <KEY, ENTITY extends SettablePersistable<ID>, ID, REPOSITORY extends CrudRepository<ENTITY, ID>> Optional<ENTITY> findById(CacheRepository<KEY, ENTITY, ID, REPOSITORY> repository, Function<ENTITY, KEY> keyMapper, ID id, CacheContext cacheContext) {
        if (repository.getDeleteQueue().contains(id)) {
            log.debug("Entity {} with id {} is already deleted.", repository.getEntityName(), id);
            return Optional.empty();
        }
        CacheMap<KEY, ID, ENTITY> cacheMap = repository.getCacheMap();
        Optional<ENTITY> cachedEntity = cacheMap.values()
            .stream()
            .map(ExpirableEntity::getEntity)
            .flatMap(map -> extractEntitiesComponent.extractEntities(map).values().stream())
            .filter(entity -> Objects.equals(entity.getId(), id))
            .findAny();

        cachedEntity.ifPresent(entity -> cacheMap.get(keyMapper.apply(entity)).updateLastAccess());
        return cachedEntity.isPresent() ? cachedEntity : searchInRepository.searchInRepository(repository, keyMapper, id, cacheContext);
    }
}
