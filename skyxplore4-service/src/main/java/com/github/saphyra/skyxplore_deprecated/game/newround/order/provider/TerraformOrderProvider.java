package com.github.saphyra.skyxplore_deprecated.game.newround.order.provider;

import com.github.saphyra.skyxplore_deprecated.game.dao.system.construction.Construction;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.construction.ConstructionQueryService;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.construction.ConstructionType;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.priority.PriorityQueryService;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.priority.PriorityType;
import com.github.saphyra.skyxplore_deprecated.game.newround.order.Order;
import com.github.saphyra.skyxplore_deprecated.game.newround.order.TerraformOrder;
import com.github.saphyra.skyxplore_deprecated.game.newround.order.processor.construction.TerraformOrderProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class TerraformOrderProvider implements OrderProvider {
    private final ConstructionQueryService constructionQueryService;
    private final PriorityQueryService priorityQueryService;
    private final TerraformOrderProcessor terraformOrderProcessor;

    @Override
    public List<Order> getForStar(UUID starId) {
        log.info("Qurying TerraformOrders for starId {}", starId);
        int basePriority = priorityQueryService.findByStarIdAndPriorityTypeAndPlayerIdValidated(starId, PriorityType.CONSTRUCTION).getValue();
        return constructionQueryService.getByStarIdAndConstructionTypeAndPlayerId(starId, ConstructionType.TERRAFORMING)
            .stream()
            .map(construction -> convert(construction, basePriority))
            .collect(Collectors.toList());
    }

    private Order convert(Construction construction, int basePriority) {
        return TerraformOrder.builder()
            .construction(construction)
            .priority(construction.getPriority() * basePriority)
            .terraformOrderProcessor(terraformOrderProcessor)
            .build();
    }
}
