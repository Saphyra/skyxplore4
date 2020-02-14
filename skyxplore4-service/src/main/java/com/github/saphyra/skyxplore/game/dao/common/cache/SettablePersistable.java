package com.github.saphyra.skyxplore.game.dao.common.cache;

import org.springframework.data.domain.Persistable;

public interface SettablePersistable<ID> extends Persistable<ID> {
    void setNew(boolean value);
}
