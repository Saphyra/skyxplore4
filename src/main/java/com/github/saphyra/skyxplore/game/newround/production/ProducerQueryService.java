package com.github.saphyra.skyxplore.game.newround.production;

import com.github.saphyra.skyxplore.data.gamedata.domain.building.production.ProductionBuildingService;
import com.github.saphyra.skyxplore.game.dao.system.building.BuildingQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProducerQueryService {
    private final BuildingQueryService buildingQueryService;
    private final ProducerFactory producerFactory;
    private final ProductionBuildingService productionBuildingService;

    public List<Producer> getByStarIdAndDataId(UUID starId, String dataId) {
        return productionBuildingService.values()
            .stream()
            .filter(productionBuilding -> productionBuilding.getGives().containsKey(dataId))
            .flatMap(productionBuilding -> buildingQueryService.getByStarIdAndDataIdAndPlayerId(starId, productionBuilding.getId()).stream())
            .filter(building -> isNull(building.getConstructionId()))
            .map(producerFactory::convertToProducer)
            .collect(Collectors.toList());
    }
}
