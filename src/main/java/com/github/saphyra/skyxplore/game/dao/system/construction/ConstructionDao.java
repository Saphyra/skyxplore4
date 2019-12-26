package com.github.saphyra.skyxplore.game.dao.system.construction;

import com.github.saphyra.dao.AbstractDao;
import com.github.saphyra.skyxplore.common.UuidConverter;
import com.github.saphyra.skyxplore.game.common.interfaces.DeletableByGameId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
class ConstructionDao extends AbstractDao<ConstructionEntity, Construction, String, ConstructionRepository> implements DeletableByGameId {
    private final UuidConverter uuidConverter;

    ConstructionDao(ConstructionConverter converter, ConstructionRepository repository, UuidConverter uuidConverter) {
        super(converter, repository);
        this.uuidConverter = uuidConverter;
    }

    void deleteByConstructionIdAndGameIdAndPlayerId(UUID constructionId, UUID gameId, UUID playerId) {
        repository.deleteByConstructionIdAndGameIdAndPlayerId(
            uuidConverter.convertDomain(constructionId),
            uuidConverter.convertDomain(gameId),
            uuidConverter.convertDomain(playerId)
        );
    }

    @Override
    public void deleteByGameId(UUID gameId) {
        log.info("Deleting constructions for gameId {}", gameId);
        repository.deleteByGameId(uuidConverter.convertDomain(gameId));
    }

    Optional<Construction> findByConstructionIdAndGameIdAndPlayerId(UUID constructionId, UUID gameId, UUID playerId) {
        return converter.convertEntity(
            repository.findByConstructionIdAndGameIdAndPlayerId(
                uuidConverter.convertDomain(constructionId),
                uuidConverter.convertDomain(gameId),
                uuidConverter.convertDomain(playerId)
            )
        );
    }

    Optional<Construction> findByConstructionTypeAndExternalIdAndGameIdAndPlayerId(ConstructionType constructionType, UUID externalId, UUID gameId, UUID playerId) {
        return converter.convertEntity(repository.findByConstructionTypeAndExternalIdAndGameIdAndPlayerId(
            constructionType,
            uuidConverter.convertDomain(externalId),
            uuidConverter.convertDomain(gameId),
            uuidConverter.convertDomain(playerId)
        ));
    }

    Optional<Construction> findByConstructionTypeAndSurfaceIdAndGameIdAndPlayerId(ConstructionType constructionType, UUID surfaceId, UUID gameId, UUID playerId) {
        return converter.convertEntity(
            repository.findByConstructionTypeAndSurfaceIdAndGameIdAndPlayerId(
                constructionType,
                uuidConverter.convertDomain(surfaceId),
                uuidConverter.convertDomain(gameId),
                uuidConverter.convertDomain(playerId)
            )
        );
    }

    List<Construction> getByStarIdAndGameIdAndPlayerId(UUID starId, UUID gameId, UUID playerId) {
        return converter.convertEntity(repository.getByStarIdAndGameIdAndPlayerId(
            uuidConverter.convertDomain(starId),
            uuidConverter.convertDomain(gameId),
            uuidConverter.convertDomain(playerId)
            )
        );
    }
}
