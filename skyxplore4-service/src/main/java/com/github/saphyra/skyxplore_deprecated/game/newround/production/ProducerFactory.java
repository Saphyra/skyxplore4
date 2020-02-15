package com.github.saphyra.skyxplore_deprecated.game.newround.production;

import com.github.saphyra.skyxplore_deprecated.data.gamedata.domain.building.production.ProductionBuildingService;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.building.Building;
import com.github.saphyra.skyxplore_deprecated.game.newround.production.building.ProductionBuildingProducer;
import com.github.saphyra.skyxplore_deprecated.game.newround.production.building.ProductionBuildingProducerContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class ProducerFactory {
    private final ProductionBuildingProducerContext productionBuildingProducerContext;
    private final ProductionBuildingService productionBuildingService;

    Producer convertToProducer(Building building) {
        return ProductionBuildingProducer.builder()
            .building(building)
            .context(productionBuildingProducerContext)
            .productionBuilding(productionBuildingService.get(building.getBuildingDataId()))
            .build();
    }
}
