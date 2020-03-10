package com.github.saphyra.skyxplore.app.common.dao.cache_repository;

import java.util.concurrent.ConcurrentHashMap;

public class CacheMap<KEY, ID, ENTITY> extends ConcurrentHashMap<KEY, ExpirableEntity<EntityMapping<ID, ENTITY>>> {
}
