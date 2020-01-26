package com.github.saphyra.skyxplore.game.newround.production;

import com.github.saphyra.skyxplore.game.dao.system.order.production.ProductionOrderCommandService;
import com.github.saphyra.skyxplore.game.dao.system.order.production.ProductionOrderQueryService;
import lombok.Getter;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Getter
class ProductionContext {
    private final ProductionOrderCommandService productionOrderCommandService;
    private final ProductionOrderQueryService productionOrderQueryService;
    private final ProductionResourceProvider productionResourceProvider;
    private final ResourceProductionOrderFactory resourceProductionOrderFactory;

    ProductionContext(
        @Lazy ProductionOrderCommandService productionOrderCommandService,
        @Lazy ProductionOrderQueryService productionOrderQueryService,
        @Lazy ProductionResourceProvider productionResourceProvider,
        @Lazy ResourceProductionOrderFactory resourceProductionOrderFactory
    ) {
        this.productionOrderCommandService = productionOrderCommandService;
        this.productionOrderQueryService = productionOrderQueryService;
        this.productionResourceProvider = productionResourceProvider;
        this.resourceProductionOrderFactory = resourceProductionOrderFactory;
    }
}
