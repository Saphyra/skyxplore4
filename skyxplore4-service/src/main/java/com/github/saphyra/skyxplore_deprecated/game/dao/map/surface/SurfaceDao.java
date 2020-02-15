package com.github.saphyra.skyxplore_deprecated.game.dao.map.surface;

import com.github.saphyra.dao.AbstractDao;
import com.github.saphyra.skyxplore.common.utils.UuidConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
class SurfaceDao extends AbstractDao<SurfaceEntity, Surface, String, SurfaceRepository> {
    private final UuidConverter uuidConverter;

    SurfaceDao(SurfaceConverter converter, SurfaceRepository repository, UuidConverter uuidConverter) {
        super(converter, repository);
        this.uuidConverter = uuidConverter;
    }

    public void deleteByGameId(UUID gameId) {
        log.info("Deleting surfaces for gameId {}", gameId);
        repository.deleteByGameId(uuidConverter.convertDomain(gameId));
    }

    @Override
    public void saveAll(List<Surface> surfaces) {
        repository.saveAll(converter.convertDomain(surfaces));
    }

    List<Surface> getByStarIdAndPlayerId(UUID starId, UUID playerId) {
        return converter.convertEntity(repository.getByStarIdAndPlayerId(
            uuidConverter.convertDomain(starId),
            uuidConverter.convertDomain(playerId)
        ));
    }

    Optional<Surface> findBySurfaceIdAndPlayerId(UUID surfaceId, UUID playerId) {
        return converter.convertEntity(repository.findBySurfaceIdAndPlayerId(
            uuidConverter.convertDomain(surfaceId),
            uuidConverter.convertDomain(playerId)
        ));
    }
}
