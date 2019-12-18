package com.github.saphyra.skyxplore.game.dao.system.storage.resource;

import com.github.saphyra.converter.Converter;
import com.github.saphyra.dao.AbstractDao;
import com.github.saphyra.skyxplore.common.UuidConverter;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
//TODO make package-private
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

    public Optional<Resource> findByStarIdAndDataIdAndRound(UUID starId, String dataId, int round) {
        return converter.convertEntity(repository.findByStarIdAndDataIdAndRound(uuidConverter.convertDomain(starId), dataId, round));
    }

    public Optional<Resource> findLatestByStarIdAndDataId(UUID starId, String dataId) {
        return converter.convertEntity(
            repository.findFirstByStarIdAndDataIdOrderByRoundDesc(
                uuidConverter.convertDomain(starId),
                dataId
            )
        );
    }

    public List<Resource> getByStarIdAndStorageType(UUID starId, StorageType storageType) {
        return converter.convertEntity(repository.getByStarIdAndStorageType(uuidConverter.convertDomain(starId), storageType));
    }

    public List<Resource> getByStarIdAndDataId(UUID starId, String dataId) {
        return converter.convertEntity(repository.getByStarIdAndDataId(uuidConverter.convertDomain(starId), dataId));
    }

    public List<Resource> getLatestByStarIdAndStorageType(UUID starId, StorageType storageType) {
        return getByStarIdAndStorageType(starId, storageType).stream()
            .collect(Collectors.groupingBy(Resource::getDataId))
            .values()
            .stream()
            .map(resources -> resources.stream()
                .max(Comparator.comparingInt(Resource::getRound))
                .orElseThrow(() -> new IllegalStateException("Resource not found in resource list"))
            )
            .collect(Collectors.toList());
    }
}
