package com.github.saphyra.skyxplore.app.common.dao;

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
