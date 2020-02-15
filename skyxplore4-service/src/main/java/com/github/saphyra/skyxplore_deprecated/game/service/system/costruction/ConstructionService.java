package com.github.saphyra.skyxplore_deprecated.game.service.system.costruction;

import com.github.saphyra.skyxplore.common.utils.DateTimeUtil;
import com.github.saphyra.skyxplore_deprecated.game.dao.common.ConstructionRequirements;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.construction.Construction;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.construction.ConstructionCommandService;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.construction.ConstructionRequiredResourcesCommandService;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.construction.ConstructionResourceRequirement;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.construction.ConstructionStatus;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.construction.ConstructionType;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConstructionService {
    private static final int DEFAULT_PRIORITY = 5;

    private final ConstructionCommandService constructionCommandService;
    private final ConstructionRequiredResourcesCommandService constructionRequiredResourcesCommandService;
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
        UUID constructionId = idGenerator.randomUUID();
        Construction construction = Construction.builder()
            .constructionId(constructionId)
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
            .isNew(true)
            .build();
        saveRequirements(gameId, constructionId, constructionRequirements);
        constructionCommandService.save(construction);
        return construction.getConstructionId();
    }

    private void saveRequirements(UUID gameId, UUID constructionId, ConstructionRequirements constructionRequirements) {
        constructionRequirements.getRequiredResources().entrySet().stream()
            .map(entry -> createRequirement(entry, constructionId, gameId))
            .forEach(constructionRequiredResourcesCommandService::save);
    }

    private ConstructionResourceRequirement createRequirement(Map.Entry<String, Integer> entry, UUID constructionId, UUID gameId) {
        return ConstructionResourceRequirement.builder()
            .constructionResourceRequirementId(idGenerator.randomUUID())
            .constructionId(constructionId)
            .gameId(gameId)
            .resourceId(entry.getKey())
            .requiredAmount(entry.getValue())
            .isNew(true)
            .build();
    }
}
