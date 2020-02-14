package com.github.saphyra.skyxplore.game.newround.order.processor.construction;

import com.github.saphyra.skyxplore.game.dao.system.building.Building;
import com.github.saphyra.skyxplore.game.dao.system.building.BuildingCommandService;
import com.github.saphyra.skyxplore.game.dao.system.building.BuildingQueryService;
import com.github.saphyra.skyxplore.game.dao.system.construction.Construction;
import com.github.saphyra.skyxplore.game.dao.system.construction.ConstructionCommandService;
import com.github.saphyra.skyxplore.game.dao.system.construction.ConstructionStatus;
import com.github.saphyra.skyxplore.game.newround.order.BuildingOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class BuildingOrderProcessor {
    private final BuildingCommandService buildingCommandService;
    private final BuildingQueryService buildingQueryService;
    private final ConstructionBuilder constructionBuilder;
    private final ConstructionCommandService constructionCommandService;
    private final ConstructionResourceCollector constructionResourceCollector;

    public void process(BuildingOrder buildingOrder) {
        log.info("Processing BuildingOrder {}", buildingOrder);
        Construction construction = buildingOrder.getConstruction();
        if (construction.getConstructionStatus() == ConstructionStatus.QUEUED) {
            construction.setConstructionStatus(ConstructionStatus.RESOURCE_COLLECTION);
        }

        if (construction.getConstructionStatus() == ConstructionStatus.RESOURCE_COLLECTION) {
            constructionResourceCollector.handleResourceCollection(construction);
        }

        if (construction.getConstructionStatus() == ConstructionStatus.IN_PROGRESS) {
            boolean constructionFinished = constructionBuilder.build(construction);
            if (constructionFinished) {
                Building building = buildingQueryService.findByBuildingIdAndPlayerId(construction.getExternalId());
                building.increaseLevel();
                building.setConstructionId(null);
                buildingCommandService.save(building);
                constructionCommandService.delete(construction);
            }
        }
    }
}
