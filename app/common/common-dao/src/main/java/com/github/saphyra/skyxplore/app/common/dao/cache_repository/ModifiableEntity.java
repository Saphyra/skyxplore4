package com.github.saphyra.skyxplore.app.common.dao.cache_repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@AllArgsConstructor
@Data
public class ModifiableEntity<TYPE> {
    @NonNull
    private final TYPE entity;

    private volatile boolean modified;
}
