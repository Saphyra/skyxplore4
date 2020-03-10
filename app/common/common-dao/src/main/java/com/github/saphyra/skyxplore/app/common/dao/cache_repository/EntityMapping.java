package com.github.saphyra.skyxplore.app.common.dao.cache_repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EntityMapping<ID, ENTITY> extends ConcurrentHashMap<ID, ModifiableEntity<ENTITY>> {
    public EntityMapping(Map<ID, ModifiableEntity<ENTITY>> entities) {
        super(entities);
    }
}
