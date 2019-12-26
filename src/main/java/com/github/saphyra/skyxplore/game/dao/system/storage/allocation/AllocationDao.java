package com.github.saphyra.skyxplore.game.dao.system.storage.allocation;

import com.github.saphyra.dao.AbstractDao;
import com.github.saphyra.skyxplore.common.UuidConverter;
import com.github.saphyra.skyxplore.game.common.interfaces.DeletableByGameId;
import com.github.saphyra.skyxplore.game.dao.system.storage.resource.StorageType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@Slf4j
class AllocationDao extends AbstractDao<AllocationEntity, Allocation, String, AllocationRepository> implements DeletableByGameId {
    private final UuidConverter uuidConverter;

    AllocationDao(AllocationConverter converter, AllocationRepository repository, UuidConverter uuidConverter) {
        super(converter, repository);
        this.uuidConverter = uuidConverter;
    }

    void deleteByExternalReferenceAndGameIdAndPlayerId(UUID constructionId, UUID gameId, UUID playerId) {
        repository.deleteByExternalReferenceAndGameIdAndPlayerId(
            uuidConverter.convertDomain(constructionId),
            uuidConverter.convertDomain(gameId),
            uuidConverter.convertDomain(playerId)
        );
    }

    @Override
    public void deleteByGameId(UUID gameId) {
        log.info("Deleting allocations for gameId {}", gameId);
        repository.deleteByGameId(uuidConverter.convertDomain(gameId));
    }

    List<Allocation> getByExternalReferenceAndGameIdAndPlayerId(UUID externalReference, UUID gameId, UUID playerId) {
        return converter.convertEntity(repository.getByExternalReferenceAndGameIdAndPlayerId(
            uuidConverter.convertDomain(externalReference),
            uuidConverter.convertDomain(gameId),
            uuidConverter.convertDomain(playerId)
        ));
    }

    List<Allocation> getByStarIdAndStorageTypeAndGameIdAndPlayerId(UUID starId, StorageType storageType, UUID gameId, UUID playerId) {
        return converter.convertEntity(repository.getByStarIdAndStorageTypeAndGameIdAndPlayerId(
            uuidConverter.convertDomain(starId),
            storageType,
            uuidConverter.convertDomain(gameId),
            uuidConverter.convertDomain(playerId)
        ));
    }

    List<Allocation> getByStarIdAndDataIdAndGameIdAndPlayerId(UUID starId, String dataId, UUID gameId, UUID playerId) {
        return converter.convertEntity(
            repository.getByStarIdAndDataIdAndGameIdAndPlayerId(
                uuidConverter.convertDomain(starId),
                dataId,
                uuidConverter.convertDomain(gameId),
                uuidConverter.convertDomain(playerId)
            )
        );
    }
}
