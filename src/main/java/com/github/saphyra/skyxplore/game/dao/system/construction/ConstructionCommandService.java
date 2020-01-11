package com.github.saphyra.skyxplore.game.dao.system.construction;

import com.github.saphyra.skyxplore.common.context.RequestContext;
import com.github.saphyra.skyxplore.common.context.RequestContextHolder;
import com.github.saphyra.skyxplore.game.common.interfaces.CommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class ConstructionCommandService implements CommandService<Construction> {
    private final ConstructionDao constructionDao;
    private final ConstructionResourceRequirementDao constructionResourceRequirementDao;
    private final RequestContextHolder requestContextHolder;

    public void deleteByConstructionIdAndGameIdAndPlayerId(UUID constructionId) {
        RequestContext context = requestContextHolder.get();
        UUID playerId = context.getPlayerId();
        constructionResourceRequirementDao.deleteByConstructionId(constructionId);
        constructionDao.deleteByConstructionIdAndPlayerId(constructionId, playerId);
    }

    @Override
    public void delete(Construction domain) {
        constructionDao.delete(domain);
    }

    @Override
    public void deleteByGameId(UUID gameId) {
        constructionDao.deleteByGameId(gameId);
    }

    @Override
    public void save(Construction construction) {
        constructionDao.save(construction);
    }

    @Override
    public void saveAll(List<Construction> domains) {
        constructionDao.saveAll(domains);
    }

    @Override
    public Class<Construction> getType() {
        return Construction.class;
    }
}
