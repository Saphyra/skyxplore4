package com.github.saphyra.skyxplore.game.dao.system.construction;

import com.github.saphyra.skyxplore.common.context.RequestContext;
import com.github.saphyra.skyxplore.common.context.RequestContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class ConstructionCommandService {
    private final ConstructionDao constructionDao;
    private final ConstructionResourceRequirementDao constructionResourceRequirementDao;
    private final RequestContextHolder requestContextHolder;

    public void deleteByConstructionIdAndGameIdAndPlayerId(UUID constructionId) {
        RequestContext context = requestContextHolder.get();
        UUID playerId = context.getPlayerId();
        constructionResourceRequirementDao.deleteByConstructionId(constructionId);
        constructionDao.deleteByConstructionIdAndPlayerId(constructionId, playerId);
    }

    public void save(Construction construction) {
        constructionDao.save(construction);
    }
}
