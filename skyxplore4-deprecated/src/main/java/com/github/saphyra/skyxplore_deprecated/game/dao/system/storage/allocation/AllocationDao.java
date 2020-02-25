package com.github.saphyra.skyxplore_deprecated.game.dao.system.storage.allocation;

import com.github.saphyra.dao.AbstractDao;
import com.github.saphyra.skyxplore.app.common.utils.UuidConverter;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.storage.resource.StorageType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@Slf4j
class AllocationDao extends AbstractDao<AllocationEntity, Allocation, String, AllocationRepository> {
    private final UuidConverter uuidConverter;

    AllocationDao(AllocationConverter converter, AllocationRepository repository, UuidConverter uuidConverter) {
        super(converter, repository);
        this.uuidConverter = uuidConverter;
    }

    void deleteByExternalReferenceAndPlayerId(UUID constructionId, UUID playerId) {
        repository.deleteByExternalReferenceAndPlayerId(
            uuidConverter.convertDomain(constructionId),
            uuidConverter.convertDomain(playerId)
        );
    }

    public void deleteByGameId(UUID gameId) {
        log.info("Deleting allocations for gameId {}", gameId);
        repository.deleteByGameId(uuidConverter.convertDomain(gameId));
    }

    List<Allocation> getByExternalReferenceAndPlayerId(UUID externalReference, UUID playerId) {
        return converter.convertEntity(repository.getByExternalReferenceAndPlayerId(
            uuidConverter.convertDomain(externalReference),
            uuidConverter.convertDomain(playerId)
        ));
    }

    public List<Allocation> getByGameId(UUID gameId) {
        return converter.convertEntity(repository.getByGameId(
            uuidConverter.convertDomain(gameId)
        ));
    }

    List<Allocation> getByStarIdAndStorageTypeAndPlayerId(UUID starId, StorageType storageType, UUID playerId) {
        return converter.convertEntity(repository.getByStarIdAndStorageTypeAndPlayerId(
            uuidConverter.convertDomain(starId),
            storageType,
            uuidConverter.convertDomain(playerId)
        ));
    }

    List<Allocation> getByStarIdAndDataIdAndPlayerId(UUID starId, String dataId, UUID playerId) {
        return converter.convertEntity(
            repository.getByStarIdAndDataIdAndPlayerId(
                uuidConverter.convertDomain(starId),
                dataId,
                uuidConverter.convertDomain(playerId)
            )
        );
    }
}
