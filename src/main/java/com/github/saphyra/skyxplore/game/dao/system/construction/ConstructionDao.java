package com.github.saphyra.skyxplore.game.dao.system.construction;

import com.github.saphyra.dao.AbstractDao;
import com.github.saphyra.skyxplore.common.UuidConverter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
class ConstructionDao extends AbstractDao<ConstructionEntity, Construction, String, ConstructionRepository> {
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

    void deleteByGameIdAndUserId(UUID gameId, UUID userId) {
        repository.deleteByGameIdAndUserId(
            uuidConverter.convertDomain(gameId),
            uuidConverter.convertDomain(userId)
        );
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
