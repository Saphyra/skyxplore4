package com.github.saphyra.skyxplore.game.dao.system.storage.allocation;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.github.saphyra.dao.AbstractDao;
import com.github.saphyra.skyxplore.common.UuidConverter;
import com.github.saphyra.skyxplore.game.dao.system.storage.resource.StorageType;

@Component
public class AllocationDao extends AbstractDao<AllocationEntity, Allocation, String, AllocationRepository> {
    private final UuidConverter uuidConverter;

    public AllocationDao(AllocationConverter converter, AllocationRepository repository, UuidConverter uuidConverter) {
        super(converter, repository);
        this.uuidConverter = uuidConverter;
    }

    public void deleteByGameIdAndUserId(UUID gameId, UUID userId) {
        repository.deleteByGameIdAndUserId(
            uuidConverter.convertDomain(gameId),
            uuidConverter.convertDomain(userId)
        );
    }

    public List<Allocation> getByStarIdAndStorageType(UUID starId, StorageType storageType) {
        return converter.convertEntity(repository.getByStarIdAndStorageType(
            uuidConverter.convertDomain(starId),
            storageType
        ));
    }
}
