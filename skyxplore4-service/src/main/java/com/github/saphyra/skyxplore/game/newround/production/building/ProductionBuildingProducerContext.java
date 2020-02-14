package com.github.saphyra.skyxplore.game.newround.production.building;

import com.github.saphyra.skyxplore.data.gamedata.domain.building.production.ProductionBuildingService;
import com.github.saphyra.skyxplore.game.dao.system.order.production.ProductionOrderCommandService;
import com.github.saphyra.skyxplore.game.dao.system.order.production.ProductionOrderQueryService;
import com.github.saphyra.skyxplore.game.newround.hr.HumanResourceService;
import com.github.saphyra.skyxplore.game.newround.production.ProducerQueryService;
import com.github.saphyra.skyxplore.game.newround.production.ongoing.OngoingProductionOrderQueryService;
import lombok.Getter;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ProductionBuildingProducerContext {
    private final HumanResourceService humanResourceService;
    private final OngoingProductionOrderQueryService ongoingProductionOrderQueryService;
    private final ProducerQueryService producerQueryService;
    private final ProductionBuildingService productionBuildingService;
    private final ProductionOrderCommandService productionOrderCommandService;
    private final ProductionOrderQueryService productionOrderQueryService;
    private final ResourceProducerService resourceProducerService;

    ProductionBuildingProducerContext(
        @Lazy HumanResourceService humanResourceService,
        @Lazy OngoingProductionOrderQueryService ongoingProductionOrderQueryService,
        @Lazy ProducerQueryService producerQueryService,
        @Lazy ProductionBuildingService productionBuildingService,
        @Lazy ProductionOrderCommandService productionOrderCommandService,
        @Lazy ProductionOrderQueryService productionOrderQueryService,
        @Lazy ResourceProducerService resourceProducerService
    ) {
        this.humanResourceService = humanResourceService;
        this.ongoingProductionOrderQueryService = ongoingProductionOrderQueryService;
        this.producerQueryService = producerQueryService;
        this.productionBuildingService = productionBuildingService;
        this.productionOrderCommandService = productionOrderCommandService;
        this.productionOrderQueryService = productionOrderQueryService;
        this.resourceProducerService = resourceProducerService;
    }
}
