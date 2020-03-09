package com.github.saphyra.skyxplore.app.common.dao.cache_repository;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
class EntityStub implements SettablePersistable<UUID> {
    private UUID id;
    private UUID key;
    private boolean isNew;
}
