package com.github.saphyra.skyxplore.app.common.dao;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
class EntityStub implements SettablePersistable<UUID> {
    private UUID id;
    private UUID key;
    private boolean isNew;
}
