package com.github.saphyra.skyxplore.game.service.system.costruction;

import com.github.saphyra.skyxplore.game.common.interfaces.QueueType;
import com.github.saphyra.skyxplore.game.common.interfaces.Queueable;
import com.github.saphyra.skyxplore.game.dao.system.building.Building;
import com.github.saphyra.skyxplore.game.dao.system.building.BuildingCommandService;
import com.github.saphyra.skyxplore.game.dao.system.building.BuildingQueryService;
import com.github.saphyra.skyxplore.game.dao.system.construction.Construction;
import com.github.saphyra.skyxplore.game.dao.system.construction.ConstructionCommandService;
import com.github.saphyra.skyxplore.game.dao.system.construction.ConstructionQueryService;
import com.github.saphyra.skyxplore.game.dao.system.construction.ConstructionType;
import com.github.saphyra.skyxplore.game.dao.system.storage.allocation.AllocationCommandService;
import com.github.saphyra.skyxplore.game.dao.system.storage.reservation.ReservationCommandService;
import com.github.saphyra.skyxplore.game.rest.request.UpdatePriorityRequest;
import com.github.saphyra.skyxplore.game.service.queue.QueueItemDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Component
public class ConstructionQueueDao implements QueueItemDao {
    private final AllocationCommandService allocationCommandService;
    private final BuildingCommandService buildingCommandService;
    private final BuildingQueryService buildingQueryService;
    private final ConstructionCommandService constructionCommandService;
    private final ConstructionQueryService constructionQueryService;
    private final ReservationCommandService reservationCommandService;

    @Override
    public boolean canHandle(QueueType queueType) {
        return queueType == QueueType.CONSTRUCTION || queueType == QueueType.TERRAFORMING;
    }

    @Override
    public List<? extends Queueable> getQueueOfStar(UUID starId) {
        return constructionQueryService.getByStarIdAndGameIdAndPlayerId(starId);
    }

    @Override
    public void updatePriority(UUID starId, UUID queueItemId, UpdatePriorityRequest request) {
        Construction construction = constructionQueryService.findByConstructionIdAndPlayerId(queueItemId);
        construction.setPriority(request.getPriority());
        constructionCommandService.save(construction);
    }

    @Override
    public void cancel(UUID starId, UUID queueItemId, QueueType queueType) {
        Construction construction = constructionQueryService.findByConstructionIdAndPlayerId(queueItemId);
        if (construction.getConstructionType() == ConstructionType.UPGRADE_BUILDING || construction.getConstructionType() == ConstructionType.BUILDING) {
            Building building = buildingQueryService.findByBuildingIdAndPlayerId(construction.getExternalId());
            if (building.getLevel() == 0) {
                buildingCommandService.delete(building);
            } else {
                building.setConstructionId(null);
                buildingCommandService.save(building);
            }
        }
        constructionCommandService.deleteByConstructionIdAndGameIdAndPlayerId(queueItemId);
        allocationCommandService.deleteByExternalReferenceAndGameIdAndPlayerId(construction.getConstructionId());
        reservationCommandService.deleteByExternalReferenceAndGameIdAndPlayerId(construction.getConstructionId());
    }
}
