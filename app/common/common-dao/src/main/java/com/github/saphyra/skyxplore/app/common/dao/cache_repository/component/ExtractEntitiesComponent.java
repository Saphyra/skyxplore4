package com.github.saphyra.skyxplore.app.common.dao.cache_repository.component;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.app.common.dao.cache_repository.EntityMapping;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
class ExtractEntitiesComponent {

    <ID, ENTITY> Map<ID, ENTITY> extractEntities(EntityMapping<ID, ENTITY> map) {
        return map.entrySet()
            .stream()
            .collect(Collectors.toMap(Map.Entry::getKey, idModifiableEntityEntry -> idModifiableEntityEntry.getValue().getEntity()));
    }
}
