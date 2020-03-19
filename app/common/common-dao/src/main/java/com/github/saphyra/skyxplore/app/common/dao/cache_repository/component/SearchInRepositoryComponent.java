package com.github.saphyra.skyxplore.app.common.dao.cache_repository.component;

import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.app.common.dao.cache_repository.CacheContext;
import com.github.saphyra.skyxplore.app.common.dao.cache_repository.CacheRepository;
import com.github.saphyra.skyxplore.app.common.dao.cache_repository.SettablePersistable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
class SearchInRepositoryComponent {
    private final LoadByKeyComponent loadByKey;

    <ENTITY extends SettablePersistable<ID>, ID, REPOSITORY extends CrudRepository<ENTITY, ID>, KEY> Optional<ENTITY> searchInRepository(CacheRepository<KEY, ENTITY, ID, REPOSITORY> repository, Function<ENTITY, KEY> keyMapper, ID id, CacheContext cacheContext) {
        log.debug("Entity {} is not found in cache with id {}. Searching in repository...", repository.getEntityName(), id);
        Optional<ENTITY> entityOptional = repository.findById(id);
        entityOptional.ifPresent(entity -> {
            log.debug("Entity {} is found in repository with id {}. Loading to cache...", repository.getEntityName(), id);
            loadByKey.loadByKey(repository, keyMapper.apply(entity), cacheContext);
        });
        return entityOptional;
    }
}
