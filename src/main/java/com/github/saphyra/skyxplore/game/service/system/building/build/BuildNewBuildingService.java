package com.github.saphyra.skyxplore.game.service.system.building.build;

import com.github.saphyra.skyxplore.common.ExceptionFactory;
import com.github.saphyra.skyxplore.data.gamedata.GameDataQueryService;
import com.github.saphyra.skyxplore.data.gamedata.domain.building.BuildingData;
import com.github.saphyra.skyxplore.game.dao.common.ConstructionRequirements;
import com.github.saphyra.skyxplore.game.dao.map.surface.Surface;
import com.github.saphyra.skyxplore.game.dao.map.surface.SurfaceCommandService;
import com.github.saphyra.skyxplore.game.dao.map.surface.SurfaceQueryService;
import com.github.saphyra.skyxplore.game.dao.system.building.Building;
import com.github.saphyra.skyxplore.game.dao.system.building.BuildingCommandService;
import com.github.saphyra.skyxplore.game.dao.system.building.BuildingQueryService;
import com.github.saphyra.skyxplore.game.dao.system.construction.ConstructionQueryService;
import com.github.saphyra.skyxplore.game.dao.system.construction.ConstructionType;
import com.github.saphyra.skyxplore.game.dao.system.storage.reservation.ReservationType;
import com.github.saphyra.skyxplore.game.service.ResearchRequirementChecker;
import com.github.saphyra.skyxplore.game.service.system.costruction.ConstructionService;
import com.github.saphyra.skyxplore.game.service.system.storage.ResourceReservationService;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class BuildNewBuildingService {
    private final BuildingCommandService buildingCommandService;
    private final BuildingQueryService buildingQueryService;
    private final ConstructionQueryService constructionQueryService;
    private final ConstructionService constructionService;
    private final GameDataQueryService gameDataQueryService;
    private final IdGenerator idGenerator;
    private final ResourceReservationService resourceReservationService;
    private final ResearchRequirementChecker researchRequirementChecker;
    private final SurfaceCommandService surfaceCommandService;
    private final SurfaceQueryService surfaceQueryService;

    @Transactional
    public void buildNewBuilding(UUID gameId, UUID surfaceId, String dataId) {
        BuildingData buildingData = gameDataQueryService.findBuildingData(dataId);
        Surface surface = surfaceQueryService.findBySurfaceIdAndPlayerId(surfaceId);
        validateBuildingLocation(buildingData, surface);

        ConstructionRequirements constructionRequirements = buildingData.getConstructionRequirements().get(1);
        researchRequirementChecker.checkResearchRequirements(surface.getStarId(), constructionRequirements.getResearchRequirements());
        Map<String, Integer> resources = constructionRequirements.getRequiredResources();

        UUID buildingId = idGenerator.randomUUID();
        UUID constructionId = constructionService.create(
            gameId,
            surface.getStarId(),
            surfaceId,
            constructionRequirements,
            ConstructionType.BUILDING,
            buildingId,
            buildingData.getId(),
            surface.getPlayerId()
        );
        resourceReservationService.reserveResources(surface, resources, ReservationType.CONSTRUCTION, constructionId);

        Building building = Building.builder()
            .buildingId(buildingId)
            .buildingDataId(dataId)
            .gameId(gameId)
            .starId(surface.getStarId())
            .surfaceId(surfaceId)
            .playerId(surface.getPlayerId())
            .level(0)
            .constructionId(constructionId)
            .build();
        buildingCommandService.save(building);
        surfaceCommandService.save(surface);
    }

    private void validateBuildingLocation(BuildingData buildingData, Surface surface) {
        if (!buildingData.getPlaceableSurfaceTypes().contains(surface.getSurfaceType())) {
            throw ExceptionFactory.invalidBuildLocation(buildingData.getId(), surface.getSurfaceId());
        }

        if (buildingQueryService.findBySurfaceIdAndPlayerId(surface.getSurfaceId()).isPresent()) {
            throw ExceptionFactory.invalidBuildLocation(buildingData.getId(), surface.getSurfaceId());
        }

        if (constructionQueryService.findByConstructionTypeAndExternalIdAndPlayerId(ConstructionType.TERRAFORMING, surface.getStarId()).isPresent()) {
            throw ExceptionFactory.terraformingAlreadyInProgress(surface.getSurfaceId());
        }
    }
}
