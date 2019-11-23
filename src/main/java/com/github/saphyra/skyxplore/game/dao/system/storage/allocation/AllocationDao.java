package com.github.saphyra.skyxplore.game.dao.system.storage.allocation;

import com.github.saphyra.dao.AbstractDao;
import com.github.saphyra.skyxplore.common.UuidConverter;
import com.github.saphyra.skyxplore.game.dao.system.storage.resource.StorageType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

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


    public List<Allocation> getByExternalReference(UUID externalReference) {
        return converter.convertEntity(repository.getByExternalReference(uuidConverter.convertDomain(externalReference)));
    }

    public List<Allocation> getByStarIdAndStorageType(UUID starId, StorageType storageType) {
        return converter.convertEntity(repository.getByStarIdAndStorageType(
            uuidConverter.convertDomain(starId),
            storageType
        ));
    }

    public List<Allocation> getByStarIdAndDataId(UUID starId, String dataId) {
        return converter.convertEntity(
            repository.getByStarIdAndDataId(
                uuidConverter.convertDomain(starId),
                dataId
            )
        );
    }
}
