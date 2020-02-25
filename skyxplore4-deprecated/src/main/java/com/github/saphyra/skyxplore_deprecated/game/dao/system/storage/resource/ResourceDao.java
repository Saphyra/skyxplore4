package com.github.saphyra.skyxplore_deprecated.game.dao.system.storage.resource;

import com.github.saphyra.dao.AbstractDao;
import com.github.saphyra.skyxplore.app.common.utils.UuidConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
class ResourceDao extends AbstractDao<ResourceEntity, Resource, String, ResourceRepository> {
    private final UuidConverter uuidConverter;

    ResourceDao(ResourceConverter converter, ResourceRepository repository, UuidConverter uuidConverter) {
        super(converter, repository);
        this.uuidConverter = uuidConverter;
    }

    public void deleteByGameId(UUID gameId) {
        log.info("Deleting resources for gameId {}", gameId);
        repository.deleteByGameId(uuidConverter.convertDomain(gameId));
    }

    void deleteExpiredByGameId(UUID gameId, int expiration) {
        repository.deleteByGameIdAndRoundBefore(
            uuidConverter.convertDomain(gameId),
            expiration
        );
    }

    Optional<Resource> findByStarIdAndDataIdAndRoundAndPlayerId(UUID starId, String dataId, int round, UUID playerId) {
        return converter.convertEntity(repository.findByStarIdAndDataIdAndRoundAndPlayerId(
            uuidConverter.convertDomain(starId),
            dataId,
            round,
            uuidConverter.convertDomain(playerId)
        ));
    }

    Optional<Resource> findLatestByStarIdAndDataIdAndPlayerId(UUID starId, String dataId, UUID playerId) {
        return converter.convertEntity(
            repository.findTopByStarIdAndDataIdAndPlayerIdOrderByRoundDesc(
                uuidConverter.convertDomain(starId),
                dataId,
                uuidConverter.convertDomain(playerId)
            )
        );
    }

    List<Resource> getByGameIdAndRound(UUID gameId, Integer round) {
        return converter.convertEntity(repository.getByGameIdAndRound(
            uuidConverter.convertDomain(gameId),
            round
        ));
    }

    List<Resource> getByStarIdAndStorageTypeAndPlayerId(UUID starId, StorageType storageType, UUID playerId) {
        return converter.convertEntity(repository.getByStarIdAndStorageTypeAndPlayerId(
            uuidConverter.convertDomain(starId),
            storageType,
            uuidConverter.convertDomain(playerId)
        ));
    }

    List<Resource> getByStarIdAndDataIdAndPlayerId(UUID starId, String dataId, UUID playerId) {
        return converter.convertEntity(repository.getByStarIdAndDataIdAndPlayerId(
            uuidConverter.convertDomain(starId),
            dataId,
            uuidConverter.convertDomain(playerId)
        ));
    }
}
