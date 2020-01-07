package com.github.saphyra.skyxplore.game.dao.system.storage.resource;

import com.github.saphyra.skyxplore.common.context.RequestContext;
import com.github.saphyra.skyxplore.common.context.RequestContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class ResourceQueryService {
    private final RequestContextHolder requestContextHolder;
    private final ResourceDao resourceDao;

    public Optional<Resource> findByStarIdAndDataIdAndRoundAndGameIdAndPlayerId(UUID starId, String dataId, int round) {
        RequestContext context = requestContextHolder.get();
        UUID playerId = context.getPlayerId();
        return resourceDao.findByStarIdAndDataIdAndRoundAndPlayerId(starId, dataId, round, playerId);
    }

    public Optional<Resource> findLatestByStarIdAndDataIdAndGameIdAndPlayerId(UUID starId, String dataId) {
        RequestContext context = requestContextHolder.get();
        UUID playerId = context.getPlayerId();
        return resourceDao.findLatestByStarIdAndDataIdAndPlayerId(starId, dataId, playerId);
    }

    public List<Resource> getByStarIdAndDataIdAndGameIdAndPlayerId(UUID starId, String dataId) {
        RequestContext context = requestContextHolder.get();
        UUID playerId = context.getPlayerId();
        return resourceDao.getByStarIdAndDataIdAndPlayerId(starId, dataId, playerId);
    }

    public List<Resource> getByStarIdAndStorageTypeAndPlayerId(UUID starId, StorageType storageType) {
        RequestContext context = requestContextHolder.get();
        UUID playerId = context.getPlayerId();
        return resourceDao.getByStarIdAndStorageTypeAndPlayerId(starId, storageType, playerId);
    }

    public List<Resource> getLatestByStarIdAndStorageType(UUID starId, StorageType storageType) {
        return getByStarIdAndStorageTypeAndPlayerId(starId, storageType).stream()
            .collect(Collectors.groupingBy(Resource::getDataId))
            .values()
            .stream()
            .map(this::getLatest)
            .collect(Collectors.toList());
    }

    private Resource getLatest(List<Resource> resources) {
        return resources.stream()
            .max(Comparator.comparingInt(Resource::getRound))
            .orElseThrow(() -> new IllegalStateException("Resource not found in resource list"));
    }
}
