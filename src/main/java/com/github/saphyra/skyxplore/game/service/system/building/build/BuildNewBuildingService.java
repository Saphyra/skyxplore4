package com.github.saphyra.skyxplore.game.service.system.building.build;

import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.saphyra.skyxplore.common.ExceptionFactory;
import com.github.saphyra.skyxplore.data.gamedata.GameDataQueryService;
import com.github.saphyra.skyxplore.data.gamedata.domain.building.BuildingData;
import com.github.saphyra.skyxplore.game.dao.common.ConstructionRequirements;
import com.github.saphyra.skyxplore.game.dao.map.surface.Surface;
import com.github.saphyra.skyxplore.game.dao.map.surface.SurfaceDao;
import com.github.saphyra.skyxplore.game.dao.system.building.Building;
import com.github.saphyra.skyxplore.game.dao.system.building.BuildingDao;
import com.github.saphyra.skyxplore.game.dao.system.construction.ConstructionType;
import com.github.saphyra.skyxplore.game.dao.system.storage.reservation.ReservationType;
import com.github.saphyra.skyxplore.game.service.ResearchRequirementChecker;
import com.github.saphyra.skyxplore.game.service.map.surface.SurfaceQueryService;
import com.github.saphyra.skyxplore.game.service.system.costruction.ConstructionQueryService;
import com.github.saphyra.skyxplore.game.service.system.costruction.ConstructionService;
import com.github.saphyra.skyxplore.game.service.system.storage.ResourceReservationService;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BuildNewBuildingService {
    private final BuildingDao buildingDao;
    private final ConstructionQueryService constructionQueryService;
    private final ConstructionService constructionService;
    private final GameDataQueryService gameDataQueryService;
    private final IdGenerator idGenerator;
    private final ResourceReservationService resourceReservationService;
    private final ResearchRequirementChecker researchRequirementChecker;
    private final SurfaceDao surfaceDao;
    private final SurfaceQueryService surfaceQueryService;

    @Transactional
    public void buildNewBuilding(UUID gameId, UUID surfaceId, String dataId) {
        BuildingData buildingData = gameDataQueryService.findBuildingData(dataId);
        Surface surface = surfaceQueryService.findBySurfaceId(surfaceId);
        validateBuildingLocation(buildingData, surface);

        ConstructionRequirements constructionRequirements = buildingData.getConstructionRequirements().get(1);
        researchRequirementChecker.checkResearchRequirements(surface.getStarId(), constructionRequirements.getResearchRequirements());
        Map<String, Integer> resources = constructionRequirements.getRequiredResources();

        UUID buildingId = idGenerator.randomUUID();
        UUID constructionId = constructionService.create(
            gameId,
            surface.getUserId(),
            surface.getStarId(),
            surfaceId,
            constructionRequirements,
            ConstructionType.BUILDING,
            buildingId,
            buildingData.getId()
        );
        resourceReservationService.reserveResources(surface, resources, ReservationType.CONSTRUCTION, constructionId);

        Building building = Building.builder()
            .buildingId(buildingId)
            .buildingDataId(dataId)
            .gameId(gameId)
            .userId(surface.getUserId())
            .starId(surface.getStarId())
            .surfaceId(surfaceId)
            .level(0)
            .constructionId(constructionId)
            .build();
        buildingDao.save(building);
        surfaceDao.save(surface);
    }

    private void validateBuildingLocation(BuildingData buildingData, Surface surface) {
        if (!buildingData.getPlaceableSurfaceTypes().contains(surface.getSurfaceType())) {
            throw ExceptionFactory.invalidBuildLocation(buildingData.getId(), surface.getSurfaceId());
        }

        if (buildingDao.findBySurfaceId(surface.getSurfaceId()).isPresent()) {
            throw ExceptionFactory.invalidBuildLocation(buildingData.getId(), surface.getSurfaceId());
        }

        if (constructionQueryService.findByConstructionTypeAndExternalId(ConstructionType.TERRAFORMING, surface.getStarId()).isPresent()) {
            throw ExceptionFactory.terraformingAlreadyInProgress(surface.getSurfaceId());
        }
    }
}
