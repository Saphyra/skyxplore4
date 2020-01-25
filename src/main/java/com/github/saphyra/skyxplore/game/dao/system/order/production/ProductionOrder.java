package com.github.saphyra.skyxplore.game.dao.system.order.production;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.UUID;

@Data
@Builder
public class ProductionOrder {
    @NonNull
    private final UUID productionOrderId;

    @NonNull
    private final UUID gameId;

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

    private final boolean isNew;

    public boolean isReady() {
        return targetAmount.equals(producedAmount);
    }
}
