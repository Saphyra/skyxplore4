package com.github.saphyra.skyxplore.app.common.dao.cache_repository.component;

import java.util.UUID;

import com.github.saphyra.skyxplore.app.common.dao.cache_repository.SettablePersistable;
import lombok.Data;

@Data
public class EntityStub implements SettablePersistable<UUID> {
    private UUID id;
    private UUID key;
    private boolean isNew;
}
