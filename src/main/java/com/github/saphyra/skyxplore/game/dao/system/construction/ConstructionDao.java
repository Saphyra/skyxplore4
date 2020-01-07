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

    void deleteByConstructionIdAndPlayerId(UUID constructionId, UUID playerId) {
        repository.deleteByConstructionIdAndPlayerId(
            uuidConverter.convertDomain(constructionId),
            uuidConverter.convertDomain(playerId)
        );
    }

    @Override
    public void deleteByGameId(UUID gameId) {
        log.info("Deleting constructions for gameId {}", gameId);
        repository.deleteByGameId(uuidConverter.convertDomain(gameId));
    }

    Optional<Construction> findByConstructionIdAndPlayerId(UUID constructionId, UUID playerId) {
        return converter.convertEntity(
            repository.findByConstructionIdAndPlayerId(
                uuidConverter.convertDomain(constructionId),
                uuidConverter.convertDomain(playerId)
            )
        );
    }

    Optional<Construction> findByConstructionTypeAndExternalIdAndPlayerId(ConstructionType constructionType, UUID externalId, UUID playerId) {
        return converter.convertEntity(repository.findByConstructionTypeAndExternalIdAndPlayerId(
            constructionType,
            uuidConverter.convertDomain(externalId),
            uuidConverter.convertDomain(playerId)
        ));
    }

    Optional<Construction> findByConstructionTypeAndSurfaceIdAndPlayerId(ConstructionType constructionType, UUID surfaceId, UUID playerId) {
        return converter.convertEntity(
            repository.findByConstructionTypeAndSurfaceIdAndPlayerId(
                constructionType,
                uuidConverter.convertDomain(surfaceId),
                uuidConverter.convertDomain(playerId)
            )
        );
    }

    List<Construction> getByStarIdAndPlayerId(UUID starId, UUID playerId) {
        return converter.convertEntity(repository.getByStarIdAndPlayerId(
            uuidConverter.convertDomain(starId),
            uuidConverter.convertDomain(playerId)
            )
        );
    }
}
