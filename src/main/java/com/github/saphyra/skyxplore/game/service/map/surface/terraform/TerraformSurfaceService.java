package com.github.saphyra.skyxplore.game.service.map.surface.terraform;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.common.ExceptionFactory;
import com.github.saphyra.skyxplore.data.gamedata.TerraformingPossibilitiesService;
import com.github.saphyra.skyxplore.data.gamedata.domain.TerraformingPossibility;
import com.github.saphyra.skyxplore.game.dao.map.surface.Surface;
import com.github.saphyra.skyxplore.game.dao.map.surface.SurfaceType;
import com.github.saphyra.skyxplore.game.dao.system.construction.ConstructionType;
import com.github.saphyra.skyxplore.game.service.map.surface.SurfaceQueryService;
import com.github.saphyra.skyxplore.game.service.system.costruction.ConstructionQueryService;
import com.github.saphyra.skyxplore.game.service.system.costruction.ConstructionService;
import com.github.saphyra.skyxplore.game.service.system.storage.ResourceReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class TerraformSurfaceService {
    private final ConstructionQueryService constructionQueryService;
    private final ConstructionService constructionService;
    private final ResourceReservationService resourceReservationService;
    private final SurfaceQueryService surfaceQueryService;
    private final TerraformingPossibilitiesService terraformingPossibilitiesService;

    public void terraform(UUID gameId, UUID playerId, UUID surfaceId, SurfaceType surfaceType) {
        Surface surface = surfaceQueryService.findBySurfaceId(surfaceId);
        verifyTerraformAvailable(surface, surfaceType);

        TerraformingPossibility terraformingPossibility = getTerraformingPossibility(surface.getSurfaceType(), surfaceType);

        resourceReservationService.reserveResources(surface, terraformingPossibility.getConstructionRequirements().getResources());
        constructionService.create(
            gameId,
            surface.getUserId(),
            surface.getStarId(),
            terraformingPossibility.getConstructionRequirements(),
            ConstructionType.TERRAFORMING,
            surfaceId
        );
    }

    private void verifyTerraformAvailable(Surface surface, SurfaceType surfaceType) {
        terraformingPossibilitiesService.getOptional(surface.getSurfaceType())
            .filter(terraformingPossibilities -> terraformingPossibilities.stream().anyMatch(terraformingPossibility -> terraformingPossibility.getSurfaceType().equals(surfaceType)))
            .orElseThrow(() -> ExceptionFactory.terraformingNotPossible(surface.getSurfaceId(), surfaceType));

        if (constructionQueryService.findByConstructionTypeAndExternalId(ConstructionType.TERRAFORMING, surface.getSurfaceId()).isPresent()) {
            throw ExceptionFactory.terraformingAlreadyInProgress(surface.getSurfaceId());
        }

        if(constructionQueryService.findByConstructionTypeAndSurfaceId(ConstructionType.BUILDING, surface.getSurfaceId()).isPresent()){
            throw ExceptionFactory.constructionInProgress(surface.getSurfaceId());
        }

        //TODO check researchRequirement
    }

    private TerraformingPossibility getTerraformingPossibility(SurfaceType from, SurfaceType to) {
        return terraformingPossibilitiesService.get(from).stream()
            .filter(terraformingPossibility -> terraformingPossibility.getSurfaceType().equals(to))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException(String.format("TerraformingPossibility not found from %s to %s", from, to)));
    }
}
