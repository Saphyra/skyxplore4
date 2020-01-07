package com.github.saphyra.skyxplore.game.service.map.surface.terraform;

import com.github.saphyra.skyxplore.common.ExceptionFactory;
import com.github.saphyra.skyxplore.data.gamedata.domain.terraforming.TerraformingPossibilitiesService;
import com.github.saphyra.skyxplore.data.gamedata.domain.terraforming.TerraformingPossibility;
import com.github.saphyra.skyxplore.game.dao.map.surface.Surface;
import com.github.saphyra.skyxplore.game.dao.map.surface.SurfaceQueryService;
import com.github.saphyra.skyxplore.game.dao.map.surface.SurfaceType;
import com.github.saphyra.skyxplore.game.dao.system.construction.ConstructionQueryService;
import com.github.saphyra.skyxplore.game.dao.system.construction.ConstructionType;
import com.github.saphyra.skyxplore.game.dao.system.storage.reservation.ReservationType;
import com.github.saphyra.skyxplore.game.service.ResearchRequirementChecker;
import com.github.saphyra.skyxplore.game.service.system.costruction.ConstructionService;
import com.github.saphyra.skyxplore.game.service.system.storage.ResourceReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class TerraformSurfaceService {
    private final ConstructionQueryService constructionQueryService;
    private final ConstructionService constructionService;
    private final ResearchRequirementChecker researchRequirementChecker;
    private final ResourceReservationService resourceReservationService;
    private final SurfaceQueryService surfaceQueryService;
    private final TerraformingPossibilitiesService terraformingPossibilitiesService;

    @Transactional
    public void terraform(UUID gameId, UUID surfaceId, SurfaceType surfaceType) {
        Surface surface = surfaceQueryService.findBySurfaceIdAndGameIdAndPlayerId(surfaceId);
        TerraformingPossibility terraformingPossibility = getTerraformingPossibility(surface.getSurfaceType(), surfaceType);
        verifyTerraformAvailable(surface, surfaceType, terraformingPossibility.getConstructionRequirements().getResearchRequirements());

        UUID constructionId = constructionService.create(
            gameId,
            surface.getStarId(),
            surfaceId,
            terraformingPossibility.getConstructionRequirements(),
            ConstructionType.TERRAFORMING,
            surfaceId,
            surfaceType.name(),
            surface.getPlayerId()
        );
        resourceReservationService.reserveResources(surface, terraformingPossibility.getConstructionRequirements().getRequiredResources(), ReservationType.TERRAFORMING, constructionId);
    }

    private void verifyTerraformAvailable(Surface surface, SurfaceType surfaceType, List<String> researchRequirements) {
        terraformingPossibilitiesService.getOptional(surface.getSurfaceType())
            .filter(terraformingPossibilities -> terraformingPossibilities.stream().anyMatch(terraformingPossibility -> terraformingPossibility.getSurfaceType().equals(surfaceType)))
            .orElseThrow(() -> ExceptionFactory.terraformingNotPossible(surface.getSurfaceId(), surfaceType));

        if (constructionQueryService.findByConstructionTypeAndExternalIdAndPlayerId(ConstructionType.TERRAFORMING, surface.getSurfaceId()).isPresent()) {
            throw ExceptionFactory.terraformingAlreadyInProgress(surface.getSurfaceId());
        }

        if(constructionQueryService.findByConstructionTypeAndSurfaceIdAndGameIdAndPlayerId(ConstructionType.BUILDING, surface.getSurfaceId()).isPresent()){
            throw ExceptionFactory.constructionInProgress(surface.getSurfaceId());
        }

        researchRequirementChecker.checkResearchRequirements(surface.getStarId(), researchRequirements);
    }

    private TerraformingPossibility getTerraformingPossibility(SurfaceType from, SurfaceType to) {
        return terraformingPossibilitiesService.get(from).stream()
            .filter(terraformingPossibility -> terraformingPossibility.getSurfaceType().equals(to))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException(String.format("TerraformingPossibility not found from %s to %s", from, to)));
    }
}
