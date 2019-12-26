package com.github.saphyra.skyxplore.game.dao.map.surface;

import com.github.saphyra.converter.Converter;
import com.github.saphyra.dao.AbstractDao;
import com.github.saphyra.skyxplore.common.UuidConverter;
import com.github.saphyra.skyxplore.game.common.interfaces.DeletableByGameId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
class SurfaceDao extends AbstractDao<SurfaceEntity, Surface, String, SurfaceRepository> implements DeletableByGameId {
    private final EntityManager entityManager;
    private final UuidConverter uuidConverter;

    SurfaceDao(Converter<SurfaceEntity, Surface> converter, SurfaceRepository repository, EntityManager entityManager, UuidConverter uuidConverter) {
        super(converter, repository);
        this.entityManager = entityManager;
        this.uuidConverter = uuidConverter;
    }

    @Override
    public void deleteByGameId(UUID gameId) {
        log.info("Deleting surfaces for gameId {}", gameId);
        repository.deleteByGameId(uuidConverter.convertDomain(gameId));
    }

    void saveAll(List<Surface> surfaces) {
        List<SurfaceEntity> entities = converter.convertDomain(surfaces);
        entities.forEach(entityManager::persist);
        entityManager.flush();
    }

    List<Surface> getByStarIdAndGameIdAndPlayerId(UUID starId, UUID gameId, UUID playerId) {
        return converter.convertEntity(repository.getByStarIdAndGameIdAndPlayerId(
            uuidConverter.convertDomain(starId),
            uuidConverter.convertDomain(gameId),
            uuidConverter.convertDomain(playerId)
        ));
    }

    Optional<Surface> findBySurfaceIdAndGameIdAndPlayerId(UUID surfaceId, UUID gameId, UUID playerId) {
        return converter.convertEntity(repository.findBySurfaceIdAndGameIdAndPlayerId(
            uuidConverter.convertDomain(surfaceId),
            uuidConverter.convertDomain(gameId),
            uuidConverter.convertDomain(playerId)
        ));
    }
}
