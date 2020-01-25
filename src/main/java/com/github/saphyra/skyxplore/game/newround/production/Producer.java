package com.github.saphyra.skyxplore.game.newround.production;

import com.github.saphyra.skyxplore.game.dao.system.order.production.ProductionOrder;

import java.util.UUID;

public interface Producer {
    UUID getId();

    boolean produce(ProductionOrder order);
}
