package com.github.saphyra.skyxplore.game.service.system.costruction;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.common.UuidConverter;
import com.github.saphyra.skyxplore.game.common.interfaces.QueueType;
import com.github.saphyra.skyxplore.game.common.interfaces.Queueable;
import com.github.saphyra.skyxplore.game.dao.system.building.Building;
import com.github.saphyra.skyxplore.game.dao.system.building.BuildingDao;
import com.github.saphyra.skyxplore.game.dao.system.construction.Construction;
import com.github.saphyra.skyxplore.game.dao.system.construction.ConstructionDao;
import com.github.saphyra.skyxplore.game.dao.system.construction.ConstructionType;
import com.github.saphyra.skyxplore.game.rest.request.UpdatePriorityRequest;
import com.github.saphyra.skyxplore.game.service.queue.QueueItemDao;
import com.github.saphyra.skyxplore.game.service.system.building.BuildingQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Component
public class ConstructionQueueDao implements QueueItemDao {
    private final ConstructionDao constructionDao;
    private final ConstructionQueryService constructionQueryService;
    private final BuildingDao buildingDao;
    private final BuildingQueryService buildingQueryService;
    private final UuidConverter uuidConverter;

    @Override
    public boolean canHandle(QueueType queueType) {
        return queueType == QueueType.CONSTRUCTION || queueType == QueueType.TERRAFORMING;
    }

    @Override
    public List<? extends Queueable> getQueueOfStar(UUID starId) {
        return constructionDao.getByStarId(starId);
    }

    @Override
    public void updatePriority(UUID starId, UUID queueItemId, UpdatePriorityRequest request) {
        Construction construction = constructionQueryService.findByConstructionId(queueItemId);
        construction.setPriority(request.getPriority());
        constructionDao.save(construction);
    }

    @Override
    public void cancel(UUID starId, UUID queueItemId, QueueType queueType) {
        Construction construction = constructionQueryService.findByConstructionId(queueItemId);
        if (construction.getConstructionType() == ConstructionType.UPGRADE_BUILDING || construction.getConstructionType() == ConstructionType.BUILDING) {
            Building building = buildingQueryService.findOneValidated(construction.getExternalId());
            if(building.getLevel() == 0){
                buildingDao.delete(building);
            }else {
                building.setConstructionId(null);
                buildingDao.save(building);
            }
        }
        constructionDao.deleteById(uuidConverter.convertDomain(queueItemId));
    }
}
