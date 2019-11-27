package com.github.saphyra.skyxplore.game.service.map.surface.terraform;

import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.common.ExceptionFactory;
import com.github.saphyra.skyxplore.data.gamedata.TerraformingPossibilitiesService;
import com.github.saphyra.skyxplore.data.gamedata.domain.TerraformingPossibility;
import com.github.saphyra.skyxplore.game.dao.map.surface.Surface;
import com.github.saphyra.skyxplore.game.dao.map.surface.SurfaceType;
import com.github.saphyra.skyxplore.game.dao.system.construction.ConstructionType;
import com.github.saphyra.skyxplore.game.dao.system.storage.reservation.ReservationType;
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

    @Transactional
    public void terraform(UUID gameId, UUID playerId, UUID surfaceId, SurfaceType surfaceType) {
        Surface surface = surfaceQueryService.findBySurfaceId(surfaceId);
        verifyTerraformAvailable(surface, surfaceType);

        TerraformingPossibility terraformingPossibility = getTerraformingPossibility(surface.getSurfaceType(), surfaceType);

        UUID constructionId = constructionService.create(
            gameId,
            surface.getUserId(),
            surface.getStarId(),
            surfaceId,
            terraformingPossibility.getConstructionRequirements(),
            ConstructionType.TERRAFORMING,
            surfaceId,
            surfaceType.name()
        );
        resourceReservationService.reserveResources(surface, terraformingPossibility.getConstructionRequirements().getRequiredResources(), ReservationType.TERRAFORMING, constructionId);
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
