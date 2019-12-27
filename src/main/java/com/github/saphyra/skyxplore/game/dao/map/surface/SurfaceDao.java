package com.github.saphyra.skyxplore.game.dao.map.surface;

import com.github.saphyra.converter.Converter;
import com.github.saphyra.dao.AbstractDao;
import com.github.saphyra.skyxplore.common.UuidConverter;
import com.github.saphyra.skyxplore.game.common.interfaces.DeletableByGameId;
import com.github.saphyra.skyxplore.game.common.interfaces.SaveAllDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
class SurfaceDao extends AbstractDao<SurfaceEntity, Surface, String, SurfaceRepository> implements DeletableByGameId, SaveAllDao<Surface> {
    private final UuidConverter uuidConverter;

    SurfaceDao(Converter<SurfaceEntity, Surface> converter, SurfaceRepository repository, UuidConverter uuidConverter) {
        super(converter, repository);
        this.uuidConverter = uuidConverter;
    }

    @Override
    public void deleteByGameId(UUID gameId) {
        log.info("Deleting surfaces for gameId {}", gameId);
        repository.deleteByGameId(uuidConverter.convertDomain(gameId));
    }

    @Override
    public void saveAll(List<Surface> surfaces) {
        repository.saveAll(converter.convertDomain(surfaces));
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
