package com.github.saphyra.skyxplore.game.service.system.building.upgrade;

import com.github.saphyra.skyxplore.common.ExceptionFactory;
import com.github.saphyra.skyxplore.data.gamedata.GameDataQueryService;
import com.github.saphyra.skyxplore.data.gamedata.domain.building.BuildingData;
import com.github.saphyra.skyxplore.game.dao.common.ConstructionRequirements;
import com.github.saphyra.skyxplore.game.dao.system.building.Building;
import com.github.saphyra.skyxplore.game.dao.system.building.BuildingCommandService;
import com.github.saphyra.skyxplore.game.dao.system.building.BuildingQueryService;
import com.github.saphyra.skyxplore.game.dao.system.construction.ConstructionType;
import com.github.saphyra.skyxplore.game.dao.system.storage.reservation.ReservationType;
import com.github.saphyra.skyxplore.game.service.ResearchRequirementChecker;
import com.github.saphyra.skyxplore.game.service.system.costruction.ConstructionService;
import com.github.saphyra.skyxplore.game.service.system.storage.ResourceReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

import static java.util.Objects.isNull;

@Service
@Slf4j
@RequiredArgsConstructor
public class UpgradeBuildingService {
    private final BuildingCommandService buildingCommandService;
    private final BuildingQueryService buildingQueryService;
    private final ConstructionService constructionService;
    private final GameDataQueryService gameDataQueryService;
    private final ResearchRequirementChecker researchRequirementChecker;
    private final ResourceReservationService resourceReservationService;

    public void upgrade(UUID gameId, UUID buildingId) {
        Building building = buildingQueryService.findByBuildingIdAndPlayerId(buildingId);
        BuildingData buildingData = gameDataQueryService.findBuildingData(building.getBuildingDataId());
        validateUpgradeRequirements(building, buildingData);

        ConstructionRequirements constructionRequirements = buildingData.getConstructionRequirements().get(building.getLevel() + 1);
        researchRequirementChecker.checkResearchRequirements(building.getStarId(), constructionRequirements.getResearchRequirements());
        Map<String, Integer> resources = constructionRequirements.getRequiredResources();

        UUID constructionId = constructionService.create(
            gameId,
            building.getStarId(),
            building.getSurfaceId(),
            constructionRequirements,
            ConstructionType.UPGRADE_BUILDING,
            buildingId,
            buildingData.getId(),
            building.getPlayerId()
        );

        resourceReservationService.reserveResources(
            building.getSurfaceId(),
            resources,
            ReservationType.CONSTRUCTION,
            constructionId
        );

        building.setConstructionId(constructionId);
        buildingCommandService.save(building);
    }

    private void validateUpgradeRequirements(Building building, BuildingData buildingData) {
        if (!isNull(building.getConstructionId())) {
            throw ExceptionFactory.upgradeAlreadyInProgress(building.getBuildingId());
        }


        if (building.getLevel() >= buildingData.getConstructionRequirements().size()) {
            throw ExceptionFactory.maxLevelReached(building.getBuildingId());
        }
    }
}
