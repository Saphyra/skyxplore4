package com.github.saphyra.skyxplore.game.service.system.costruction;

import com.github.saphyra.skyxplore.common.event.GameDeletedEvent;
import com.github.saphyra.skyxplore.game.dao.common.ConstructionRequirements;
import com.github.saphyra.skyxplore.game.dao.system.construction.Construction;
import com.github.saphyra.skyxplore.game.dao.system.construction.ConstructionDao;
import com.github.saphyra.skyxplore.game.dao.system.construction.ConstructionStatus;
import com.github.saphyra.skyxplore.game.dao.system.construction.ConstructionType;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConstructionService {
    private static final int DEFAULT_PRIORITY = 5;

    private final ConstructionDao constructionDao;
    private final IdGenerator idGenerator;

    @EventListener
    void gameDeletedEventListener(GameDeletedEvent gameDeletedEvent) {
        constructionDao.deleteByGameIdAndUserId(gameDeletedEvent.getGameId(), gameDeletedEvent.getUserId());
    }

    public UUID create(UUID gameId, UUID userId, UUID starId, ConstructionRequirements constructionRequirements, ConstructionType constructionType) {
        Construction construction = Construction.builder()
                .constructionId(idGenerator.randomUUID())
                .gameId(gameId)
                .userId(userId)
                .starId(starId)
                .constructionRequirements(constructionRequirements)
                .constructionType(constructionType)
                .constructionStatus(ConstructionStatus.QUEUED)
                .currentWorkPoints(0)
                .priority(DEFAULT_PRIORITY)
                .build();
        constructionDao.save(construction);
        return construction.getConstructionId();
    }
}
