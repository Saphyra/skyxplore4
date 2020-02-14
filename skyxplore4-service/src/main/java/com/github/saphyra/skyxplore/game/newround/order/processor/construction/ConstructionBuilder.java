package com.github.saphyra.skyxplore.game.newround.order.processor.construction;

import com.github.saphyra.skyxplore.game.dao.system.citizen.SkillType;
import com.github.saphyra.skyxplore.game.dao.system.construction.Construction;
import com.github.saphyra.skyxplore.game.dao.system.construction.ConstructionCommandService;
import com.github.saphyra.skyxplore.game.newround.hr.HumanResource;
import com.github.saphyra.skyxplore.game.newround.hr.HumanResourceService;
import com.github.saphyra.skyxplore.game.newround.hr.ProductionProcess;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
class ConstructionBuilder {
    private final ConstructionCommandService constructionCommandService;
    private final HumanResourceService humanResourceService;

    boolean build(Construction construction) {
        Optional<HumanResource> optionalHumanResource = humanResourceService.getOne(
            construction.getGameId(),
            construction.getStarId(),
            construction.getConstructionId(),
            SkillType.BUILDING
        );
        if (optionalHumanResource.isPresent()) {
            HumanResource humanResource = optionalHumanResource.get();
            humanResource.setAllocation(construction.getConstructionId());
            ProductionProcess productionProcess = humanResource.produce(
                SkillType.BUILDING,
                1,
                construction.getConstructionRequirements().getRequiredWorkPoints(),
                construction.getCurrentWorkPoints()
            );
            if (productionProcess.getFinishedProducts() == 1) {
                return true;
            } else {
                construction.setCurrentWorkPoints(construction.getCurrentWorkPoints() + productionProcess.getCurrentProgress());
                constructionCommandService.save(construction);
                return false;
            }
        }

        return false;
    }
}
