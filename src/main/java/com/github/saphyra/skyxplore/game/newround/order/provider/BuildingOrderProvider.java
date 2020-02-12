package com.github.saphyra.skyxplore.game.newround.order.provider;

import com.github.saphyra.skyxplore.game.dao.system.construction.Construction;
import com.github.saphyra.skyxplore.game.dao.system.construction.ConstructionQueryService;
import com.github.saphyra.skyxplore.game.dao.system.construction.ConstructionType;
import com.github.saphyra.skyxplore.game.dao.system.priority.PriorityQueryService;
import com.github.saphyra.skyxplore.game.dao.system.priority.PriorityType;
import com.github.saphyra.skyxplore.game.newround.order.BuildingOrder;
import com.github.saphyra.skyxplore.game.newround.order.Order;
import com.github.saphyra.skyxplore.game.newround.order.processor.construction.BuildingOrderProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
@Slf4j
public class BuildingOrderProvider implements OrderProvider {
    private final ConstructionQueryService constructionQueryService;
    private final PriorityQueryService priorityQueryService;
    private final BuildingOrderProcessor buildingOrderProcessor;

    @Override
    public List<Order> getForStar(UUID starId) {
        log.info("Qurying BuildingOrders for starId {}", starId);
        int basePriority = priorityQueryService.findByStarIdAndPriorityTypeAndPlayerIdValidated(starId, PriorityType.CONSTRUCTION).getValue();
        return Stream.concat(
            constructionQueryService.getByStarIdAndConstructionTypeAndPlayerId(starId, ConstructionType.BUILDING).stream(),
            constructionQueryService.getByStarIdAndConstructionTypeAndPlayerId(starId, ConstructionType.UPGRADE_BUILDING).stream()
        )
            .map(construction -> convert(construction, basePriority))
            .collect(Collectors.toList());
    }

    private Order convert(Construction construction, int basePriority) {
        return BuildingOrder.builder()
            .construction(construction)
            .priority(construction.getPriority() * basePriority)
            .buildingOrderProcessor(buildingOrderProcessor)
            .build();
    }
}
