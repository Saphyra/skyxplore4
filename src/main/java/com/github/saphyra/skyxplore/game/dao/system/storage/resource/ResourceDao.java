package com.github.saphyra.skyxplore.game.dao.system.storage.resource;

import com.github.saphyra.converter.Converter;
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

    ResourceDao(Converter<ResourceEntity, Resource> converter, ResourceRepository repository, UuidConverter uuidConverter) {
        super(converter, repository);
        this.uuidConverter = uuidConverter;
    }

    @Override
    public void deleteByGameId(UUID gameId) {
        log.info("Deleting resources for gameId {}", gameId);
        repository.deleteByGameId(uuidConverter.convertDomain(gameId));
    }

    Optional<Resource> findByStarIdAndDataIdAndRoundAndGameIdAndPlayerId(UUID starId, String dataId, int round, UUID gameId, UUID playerId) {
        return converter.convertEntity(repository.findByStarIdAndDataIdAndRoundAndGameIdAndPlayerId(
            uuidConverter.convertDomain(starId),
            dataId,
            round,
            uuidConverter.convertDomain(gameId),
            uuidConverter.convertDomain(playerId)
        ));
    }

    Optional<Resource> findLatestByStarIdAndDataIdAndGameIdAndPlayerId(UUID starId, String dataId, UUID gameId, UUID playerId) {
        return converter.convertEntity(
            repository.findLatestByStarIdAndDataIdAndGameIdAndPlayerId(
                uuidConverter.convertDomain(starId),
                dataId,
                uuidConverter.convertDomain(gameId),
                uuidConverter.convertDomain(playerId)
            )
        );
    }

    List<Resource> getByStarIdAndStorageTypeAndGameIdAndPlayerId(UUID starId, StorageType storageType, UUID gameId, UUID playerId) {
        return converter.convertEntity(repository.getByStarIdAndStorageTypeAndGameIdAndPlayerId(
            uuidConverter.convertDomain(starId),
            storageType,
            uuidConverter.convertDomain(gameId),
            uuidConverter.convertDomain(playerId)
        ));
    }

    List<Resource> getByStarIdAndDataIdAndGameIdAndPlayerId(UUID starId, String dataId, UUID gameId, UUID playerId) {
        return converter.convertEntity(repository.getByStarIdAndDataIdAndGameIdAndPlayerId(
            uuidConverter.convertDomain(starId),
            dataId,
            uuidConverter.convertDomain(gameId),
            uuidConverter.convertDomain(playerId)
        ));
    }
}
