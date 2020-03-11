package com.github.saphyra.skyxplore.app.common.dao.cache_repository.component;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.app.common.dao.cache_repository.CacheContext;
import com.github.saphyra.skyxplore.app.common.dao.cache_repository.EntityMapping;
import com.github.saphyra.skyxplore.app.common.dao.cache_repository.ExpirableEntity;
import com.github.saphyra.skyxplore.app.common.dao.cache_repository.ModifiableEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
//TODO unit test
class EntityWrapperComponent {
    <ID, ENTITY> ExpirableEntity<EntityMapping<ID, ENTITY>> wrapEntities(Map<ID, ENTITY> map, CacheContext cacheContext) {
        Map<ID, ModifiableEntity<ENTITY>> entities = map.entrySet()
            .stream()
            .collect(Collectors.toMap(Map.Entry::getKey, identityEntry -> new ModifiableEntity<>(identityEntry.getValue(), false)));
        return new ExpirableEntity<>(new EntityMapping<>(entities), cacheContext);
    }
}
