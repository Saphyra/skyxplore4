package com.github.saphyra.skyxplore.app.common.dao.cache_repository;

import org.springframework.data.domain.Persistable;

public interface SettablePersistable<ID> extends Persistable<ID> {
    void setNew(boolean value);
}
