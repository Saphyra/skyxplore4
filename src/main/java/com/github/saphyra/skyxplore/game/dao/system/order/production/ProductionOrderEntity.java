package com.github.saphyra.skyxplore.game.dao.system.order.production;

import com.github.saphyra.skyxplore.game.dao.common.cache.SettablePersistable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "production_order")
class ProductionOrderEntity implements SettablePersistable<String> {
    @Id
    private String productionOrderId;
    private String gameId;
    private String starId;
    private String playerId;
    private String orderId;
    private String customerId;
    private String dataId;
    private String producerBuildingId;
    private Integer targetAmount;
    private Integer producedAmount;
    private Integer currentProgress;
    private String existingResourceRequirements;

    @Transient
    private boolean isNew;


    @Override
    public String getId() {
        return productionOrderId;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }
}
