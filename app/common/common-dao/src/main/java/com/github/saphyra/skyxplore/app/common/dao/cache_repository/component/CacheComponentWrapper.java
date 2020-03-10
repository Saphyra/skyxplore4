package com.github.saphyra.skyxplore.app.common.dao.cache_repository.component;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Getter
public class CacheComponentWrapper {
    private final DeleteAllComponent deleteAll;
    private final DeleteByKeyComponent deleteByKey;
    private final EvictExpiredEntitiesComponent evictExpiredEntitiesComponent;
    private final FindAllComponent findAll;
    private final FindByIdComponent findById;
    private final GetMapByKeyComponent getMapByKey;
    private final ProcessDeletionsComponent processDeletionsComponent;
    private final SaveComponent saveComponent;
    private final SyncChangesComponent syncChangesComponent;
}
