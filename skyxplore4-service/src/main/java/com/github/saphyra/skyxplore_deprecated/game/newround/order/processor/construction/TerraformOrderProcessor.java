package com.github.saphyra.skyxplore_deprecated.game.newround.order.processor.construction;

import com.github.saphyra.skyxplore_deprecated.game.dao.map.surface.Surface;
import com.github.saphyra.skyxplore_deprecated.game.dao.map.surface.SurfaceCommandService;
import com.github.saphyra.skyxplore_deprecated.game.dao.map.surface.SurfaceQueryService;
import com.github.saphyra.skyxplore_deprecated.game.dao.map.surface.SurfaceType;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.construction.Construction;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.construction.ConstructionCommandService;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.construction.ConstructionStatus;
import com.github.saphyra.skyxplore_deprecated.game.newround.order.TerraformOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TerraformOrderProcessor {
    private final ConstructionBuilder constructionBuilder;
    private final ConstructionCommandService constructionCommandService;
    private final ConstructionResourceCollector constructionResourceCollector;
    private final SurfaceCommandService surfaceCommandService;
    private final SurfaceQueryService surfaceQueryService;

    public void process(TerraformOrder terraformOrder) {
        log.info("Processing TerraformOrder {}", terraformOrder);

        Construction construction = terraformOrder.getConstruction();
        if (construction.getConstructionStatus() == ConstructionStatus.QUEUED) {
            construction.setConstructionStatus(ConstructionStatus.RESOURCE_COLLECTION);
        }

        if (construction.getConstructionStatus() == ConstructionStatus.RESOURCE_COLLECTION) {
            constructionResourceCollector.handleResourceCollection(construction);
        }

        if (construction.getConstructionStatus() == ConstructionStatus.IN_PROGRESS) {
            boolean constructionFinished = constructionBuilder.build(construction);
            if (constructionFinished) {
                Surface surface = surfaceQueryService.findBySurfaceIdAndPlayerId(construction.getExternalId());
                surface.setSurfaceType(SurfaceType.valueOf(construction.getAdditionalData()));
                surfaceCommandService.save(surface);
                constructionCommandService.delete(construction);
            }
        }
    }
}
