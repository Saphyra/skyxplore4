package com.github.saphyra.skyxplore_deprecated.game.rest.view.system;

import com.github.saphyra.skyxplore_deprecated.game.dao.system.storage.resource.StorageType;
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
    private final Integer allocated;
    @NonNull
    private final Integer reserved;
    @NonNull
    private final List<ResourceDetailsView> resources;
}
