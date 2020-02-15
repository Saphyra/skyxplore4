package com.github.saphyra.skyxplore_deprecated.game.newround.production;

import com.github.saphyra.skyxplore_deprecated.game.dao.system.order.production.ProductionOrder;

import java.util.UUID;

public interface Producer {
    Double getLoad();

    UUID getId();

    boolean produce(ProductionOrder order);
}
