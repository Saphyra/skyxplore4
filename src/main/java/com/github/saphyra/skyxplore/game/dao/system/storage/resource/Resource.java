package com.github.saphyra.skyxplore.game.dao.system.storage.resource;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.UUID;

@Data
@Builder(toBuilder = true)
public class Resource {
    @NonNull
    private final UUID resourceId;

    @NonNull
    private final UUID gameId;

    @NonNull
    private final UUID playerId;

    @NonNull
    private final StorageType storageType;

    @NonNull
    private final String dataId;

    @NonNull
    private final UUID starId;

    @NonNull
    private Integer amount;

    @NonNull
    private final Integer round;

    private final boolean isNew;

    public void addAmount(Integer addition) {
        amount += addition;
    }

    public void removeAmount(int removal) {
        amount -= removal;
    }
}
