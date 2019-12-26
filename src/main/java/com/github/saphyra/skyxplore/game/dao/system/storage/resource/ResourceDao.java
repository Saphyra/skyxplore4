package com.github.saphyra.skyxplore.game.dao.system.storage.resource;

import com.github.saphyra.converter.Converter;
import com.github.saphyra.dao.AbstractDao;
import com.github.saphyra.skyxplore.common.UuidConverter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
class ResourceDao extends AbstractDao<ResourceEntity, Resource, String, ResourceRepository> {
    private final UuidConverter uuidConverter;

    ResourceDao(Converter<ResourceEntity, Resource> converter, ResourceRepository repository, UuidConverter uuidConverter) {
        super(converter, repository);
        this.uuidConverter = uuidConverter;
    }

    void deleteByGameIdAndUserId(UUID gameId, UUID userId) {
        repository.deleteByGameIdAndUserId(
            uuidConverter.convertDomain(gameId),
            uuidConverter.convertDomain(userId)
        );
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
