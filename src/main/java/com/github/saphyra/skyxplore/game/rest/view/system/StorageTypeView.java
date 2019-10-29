package com.github.saphyra.skyxplore.game.rest.view.system;

import com.github.saphyra.skyxplore.game.module.system.resource.domain.StorageType;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Builder
@Data
public class StorageTypeView {
    @NonNull
    private final StorageType storageType;
    @NonNull
    private final Integer capacity;
    @NonNull
    private final Integer actual;
    @NonNull
    private final Integer reserved;
    @NonNull
    private final List<ResourceDetailsView> resources;
}
