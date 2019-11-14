package com.github.saphyra.skyxplore.game.dao.map.surface;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Component;

import com.github.saphyra.converter.Converter;
import com.github.saphyra.dao.AbstractDao;
import com.github.saphyra.skyxplore.common.UuidConverter;

@Component
public class SurfaceDao extends AbstractDao<SurfaceEntity, Surface, String, SurfaceRepository> {
    private final EntityManager entityManager;
    private final UuidConverter uuidConverter;

    public SurfaceDao(Converter<SurfaceEntity, Surface> converter, SurfaceRepository repository, EntityManager entityManager, UuidConverter uuidConverter) {
        super(converter, repository);
        this.entityManager = entityManager;
        this.uuidConverter = uuidConverter;
    }

    public void deleteByGameIdAndUserId(UUID gameId, UUID userId) {
        repository.deleteByGameIdAndUserId(
            uuidConverter.convertDomain(gameId),
            uuidConverter.convertDomain(userId)
        );
    }

    public void saveAll(List<Surface> surfaces) {
        List<SurfaceEntity> entities = converter.convertDomain(surfaces);
        entities.forEach(entityManager::persist);
        entityManager.flush();
    }

    public List<Surface> getByStarId(UUID starId) {
        return converter.convertEntity(repository.getByStarId(uuidConverter.convertDomain(starId)));
    }
}
