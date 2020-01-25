package com.github.saphyra.skyxplore.game.newround.production;

import com.github.saphyra.skyxplore.data.gamedata.domain.building.production.ProductionBuildingService;
import com.github.saphyra.skyxplore.game.dao.system.building.Building;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class ProducerFactory {
    private final ProductionContext productionContext;
    private final ProductionBuildingService productionBuildingService;

    Producer convertToProducer(Building building) {
        return ProductionBuildingProducer.builder()
            .building(building)
            .productionContext(productionContext)
            .productionBuilding(productionBuildingService.get(building.getBuildingDataId()))
            .build();
    }
}
