package com.github.saphyra.skyxplore.game.dao.map.surface;

import com.github.saphyra.converter.Converter;
import com.github.saphyra.dao.AbstractDao;
import com.github.saphyra.skyxplore.common.UuidConverter;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
class SurfaceDao extends AbstractDao<SurfaceEntity, Surface, String, SurfaceRepository> {
    private final EntityManager entityManager;
    private final UuidConverter uuidConverter;

    SurfaceDao(Converter<SurfaceEntity, Surface> converter, SurfaceRepository repository, EntityManager entityManager, UuidConverter uuidConverter) {
        super(converter, repository);
        this.entityManager = entityManager;
        this.uuidConverter = uuidConverter;
    }

    void deleteByGameIdAndUserId(UUID gameId, UUID userId) {
        repository.deleteByGameIdAndUserId(
            uuidConverter.convertDomain(gameId),
            uuidConverter.convertDomain(userId)
        );
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
