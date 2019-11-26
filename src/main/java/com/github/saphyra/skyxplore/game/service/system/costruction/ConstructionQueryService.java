package com.github.saphyra.skyxplore.game.service.system.costruction;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.common.ExceptionFactory;
import com.github.saphyra.skyxplore.common.UuidConverter;
import com.github.saphyra.skyxplore.game.common.interfaces.Queueable;
import com.github.saphyra.skyxplore.game.dao.system.construction.Construction;
import com.github.saphyra.skyxplore.game.dao.system.construction.ConstructionDao;
import com.github.saphyra.skyxplore.game.dao.system.construction.ConstructionType;
import com.github.saphyra.skyxplore.game.service.QueueItemProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConstructionQueryService implements QueueItemProvider {
    private final ConstructionDao constructionDao;
    private final UuidConverter uuidConverter;

    Construction findByConstructionId(UUID constructionId) {
        return constructionDao.findById(uuidConverter.convertDomain(constructionId))
            .orElseThrow(() -> ExceptionFactory.constructionNotFound(constructionId));
    }

    public Optional<Construction> findByConstructionTypeAndExternalId(ConstructionType constructionType, UUID externalId) {
        return constructionDao.findByConstructionTypeAndExternalId(constructionType, externalId);
    }

    public Optional<Construction> findByConstructionTypeAndSurfaceId(ConstructionType constructionType, UUID surfaceId) {
        return constructionDao.findByConstructionTypeAndSurfaceId(constructionType, surfaceId);
    }

    @Override
    public List<? extends Queueable> getQueueOfStar(UUID starId) {
        return constructionDao.getByStarId(starId);
    }
}
