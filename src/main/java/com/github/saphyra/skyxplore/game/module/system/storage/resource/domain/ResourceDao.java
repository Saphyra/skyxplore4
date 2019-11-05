package com.github.saphyra.skyxplore.game.module.system.storage.resource.domain;

import com.github.saphyra.converter.Converter;
import com.github.saphyra.dao.AbstractDao;
import com.github.saphyra.skyxplore.common.UuidConverter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ResourceDao extends AbstractDao<ResourceEntity, Resource, String, ResourceRepository> {
    private final UuidConverter uuidConverter;

    public ResourceDao(Converter<ResourceEntity, Resource> converter, ResourceRepository repository, UuidConverter uuidConverter) {
        super(converter, repository);
        this.uuidConverter = uuidConverter;
    }

    public void deleteByGameIdAndUserId(UUID gameId, UUID userId) {
        repository.deleteByGameIdAndUserId(
                uuidConverter.convertDomain(gameId),
                uuidConverter.convertDomain(userId)
        );
    }

    public List<Resource> getByStarIdAndStorageType(UUID starId, StorageType storageType) {
        return converter.convertEntity(repository.getByStarIdAndStorageType(uuidConverter.convertDomain(starId), storageType));
    }

    public Optional<Resource> findByStarIdAndDataIdAndRound(UUID starId, String dataId, int round) {
        return converter.convertEntity(repository.findByStarIdAndDataIdAndRound(uuidConverter.convertDomain(starId), dataId, round));
    }

    public List<Resource> getByStarIdAndDataId(UUID starId, String dataId) {
        return converter.convertEntity(repository.getByStarIdAndDataId(uuidConverter.convertDomain(starId), dataId));
    }
}
