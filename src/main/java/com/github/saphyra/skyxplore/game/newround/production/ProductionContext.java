package com.github.saphyra.skyxplore.game.newround.production;

import com.github.saphyra.skyxplore.data.gamedata.domain.building.production.ProductionBuildingService;
import com.github.saphyra.skyxplore.game.dao.system.order.production.ProductionOrderCommandService;
import com.github.saphyra.skyxplore.game.dao.system.order.production.ProductionOrderQueryService;
import com.github.saphyra.skyxplore.game.newround.hr.HumanResourceService;
import lombok.Getter;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Getter
class ProductionContext {
    private final HumanResourceService humanResourceService;
    private final ProductionBuildingService productionBuildingService;
    private final ProductionOrderCommandService productionOrderCommandService;
    private final ProductionOrderQueryService productionOrderQueryService;
    private final ProductionResourceProvider productionResourceProvider;
    private final ResourceProductionOrderFactory resourceProductionOrderFactory;

    ProductionContext(
        @Lazy HumanResourceService humanResourceService,
        @Lazy ProductionBuildingService productionBuildingService,
        @Lazy ProductionOrderCommandService productionOrderCommandService,
        @Lazy ProductionOrderQueryService productionOrderQueryService,
        @Lazy ProductionResourceProvider productionResourceProvider,
        @Lazy ResourceProductionOrderFactory resourceProductionOrderFactory
    ) {
        this.humanResourceService = humanResourceService;
        this.productionBuildingService = productionBuildingService;
        this.productionOrderCommandService = productionOrderCommandService;
        this.productionOrderQueryService = productionOrderQueryService;
        this.productionResourceProvider = productionResourceProvider;
        this.resourceProductionOrderFactory = resourceProductionOrderFactory;
    }
}
