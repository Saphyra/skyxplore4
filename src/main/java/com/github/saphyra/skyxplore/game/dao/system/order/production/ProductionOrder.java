package com.github.saphyra.skyxplore.game.dao.system.order.production;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class ProductionOrder {
    @NonNull
    private final UUID productionOrderId;

    @NonNull
    private final UUID gameId;

    @NonNull
    private final UUID starId;

    @NonNull
    private final UUID playerId;

    @NonNull
    private final UUID orderId;

    @NonNull
    private final UUID customerId;

    private UUID producerBuildingId;

    @NonNull
    private final String dataId;

    @NonNull
    private final Integer targetAmount;

    @NonNull
    private Integer producedAmount;

    @NonNull
    @Builder.Default
    private final List<String> existingResourceRequirements = new ArrayList<>();

    private final boolean isNew;

    public void addProduced(int addition) {
        producedAmount += addition;
    }

    public int getMissingAmount() {
        return targetAmount - producedAmount;
    }

    public boolean isReady() {
        return targetAmount <= producedAmount;
    }
}
