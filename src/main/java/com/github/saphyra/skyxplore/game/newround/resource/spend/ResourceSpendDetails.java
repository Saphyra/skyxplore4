package com.github.saphyra.skyxplore.game.newround.resource.spend;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.UUID;

@Data
@Builder(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ResourceSpendDetails {
    @NonNull
    private final UUID starId;

    @NonNull
    private final String dataId;

    private final UUID allocationId;

    @NonNull
    private final Integer requestedAmount;

    @NonNull
    private final Integer availableAmount;
}
