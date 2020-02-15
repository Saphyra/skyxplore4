package com.github.saphyra.skyxplore_deprecated.game.dao.system.storage.allocation;

import com.github.saphyra.skyxplore_deprecated.game.dao.system.storage.resource.StorageType;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.UUID;

@Data
@Builder
//Used for existing resources, what will be used in a specific production
public class Allocation {
    @NonNull
    private final UUID allocationId;

    @NonNull
    private final UUID gameId;

    @NonNull
    private final UUID starId;

    @NonNull
    private final UUID playerId;

    /**
     * Id of the order/construction the allocation belongs to
     */
    @NonNull
    private final UUID externalReference;

    /**
     * Id of the resource
     */
    @NonNull
    private final String dataId;

    @NonNull
    private final StorageType storageType;

    @NonNull
    private Integer amount;

    @NonNull
    private final AllocationType allocationType;

    private final boolean isNew;

    public void addAmount(int producedAmount) {
        amount += producedAmount;
    }
}
