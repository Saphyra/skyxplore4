package com.github.saphyra.skyxplore.game.service.system.costruction;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.game.dao.system.construction.Construction;
import com.github.saphyra.skyxplore.game.dao.system.construction.ConstructionDao;
import com.github.saphyra.skyxplore.game.dao.system.construction.ConstructionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConstructionQueryService {
    private final ConstructionDao constructionDao;

    public Optional<Construction> findByConstructionTypeAndExternalId(ConstructionType constructionType, UUID externalId) {
        return constructionDao.findByConstructionTypeAndExternalId(constructionType, externalId);
    }

    public Optional<Construction> findByConstructionTypeAndSurfaceId(ConstructionType constructionType, UUID surfaceId) {
        return constructionDao.findByConstructionTypeAndSurfaceId(constructionType, surfaceId);
    }
}
