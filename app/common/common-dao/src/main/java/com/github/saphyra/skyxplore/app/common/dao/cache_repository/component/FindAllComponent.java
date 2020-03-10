package com.github.saphyra.skyxplore.app.common.dao.cache_repository.component;

import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.app.common.dao.cache_repository.CacheContext;
import com.github.saphyra.skyxplore.app.common.dao.cache_repository.CacheRepository;
import com.github.saphyra.skyxplore.app.common.dao.cache_repository.SettablePersistable;
import com.github.saphyra.skyxplore.app.common.utils.CollectionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
//TODO unit test
public class FindAllComponent {
    private final AddToCacheComponent addToCacheComponent;

    public <KEY, ENTITY extends SettablePersistable<ID>, ID, REPOSITORY extends CrudRepository<ENTITY, ID>> Iterable<ENTITY> findAll(CacheRepository<KEY, ENTITY, ID, REPOSITORY> repository, Function<ENTITY, KEY> keyMapper, CacheContext cacheContext) {
        Iterable<ENTITY> entities = repository.findAll();
        return CollectionUtil.toList(entities).stream()
            .filter(entity -> !repository.getDeleteQueue().contains(entity.getId()))
            .collect(Collectors.groupingBy(keyMapper))
            .entrySet()
            .stream()
            .peek(entry -> addToCacheComponent.addToCache(repository, entry.getKey(), entry.getValue(), cacheContext))
            .flatMap(entry -> entry.getValue().stream())
            .collect(Collectors.toList());
    }
}
