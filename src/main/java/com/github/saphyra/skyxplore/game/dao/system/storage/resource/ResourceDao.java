package com.github.saphyra.skyxplore.game.dao.system.storage.resource;

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
class ResourceDao extends AbstractDao<ResourceEntity, Resource, String, ResourceRepository> implements DeletableByGameId {
    private final UuidConverter uuidConverter;

    ResourceDao(ResourceConverter converter, ResourceRepository repository, UuidConverter uuidConverter) {
        super(converter, repository);
        this.uuidConverter = uuidConverter;
    }

    @Override
    public void deleteByGameId(UUID gameId) {
        log.info("Deleting resources for gameId {}", gameId);
        repository.deleteByGameId(uuidConverter.convertDomain(gameId));
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
            repository.findLatestByStarIdAndDataIdAndPlayerId(
                uuidConverter.convertDomain(starId),
                dataId,
                uuidConverter.convertDomain(playerId)
            )
        );
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
