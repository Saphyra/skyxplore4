package com.github.saphyra.skyxplore.game.service.system.costruction;

import com.github.saphyra.skyxplore.common.DateTimeUtil;
import com.github.saphyra.skyxplore.game.dao.common.ConstructionRequirements;
import com.github.saphyra.skyxplore.game.dao.system.construction.Construction;
import com.github.saphyra.skyxplore.game.dao.system.construction.ConstructionCommandService;
import com.github.saphyra.skyxplore.game.dao.system.construction.ConstructionStatus;
import com.github.saphyra.skyxplore.game.dao.system.construction.ConstructionType;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConstructionService {
    private static final int DEFAULT_PRIORITY = 5;

    private final ConstructionCommandService constructionCommandService;
    private final DateTimeUtil dateTimeUtil;
    private final IdGenerator idGenerator;

    public UUID create(
        UUID gameId,
        UUID starId,
        UUID surfaceId,
        ConstructionRequirements constructionRequirements,
        ConstructionType constructionType,
        UUID externalId,
        String additionalData,
        UUID playerId
    ) {
        Construction construction = Construction.builder()
            .constructionId(idGenerator.randomUUID())
            .gameId(gameId)
            .starId(starId)
            .playerId(playerId)
            .surfaceId(surfaceId)
            .constructionRequirements(constructionRequirements)
            .constructionType(constructionType)
            .constructionStatus(ConstructionStatus.QUEUED)
            .currentWorkPoints(0)
            .priority(DEFAULT_PRIORITY)
            .externalId(externalId)
            .additionalData(additionalData)
            .addedAt(dateTimeUtil.now())
            .build();
        constructionCommandService.save(construction);
        return construction.getConstructionId();
    }
}
