package com.github.saphyra.skyxplore.game.dao.common.cache;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@AllArgsConstructor
@Data
class ModifiableEntity<TYPE> {
    @NonNull
    private final TYPE entity;

    private volatile boolean modified;
}
