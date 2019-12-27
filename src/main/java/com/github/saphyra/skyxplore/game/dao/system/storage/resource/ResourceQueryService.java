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
        UUID gameId = context.getGameId();
        UUID playerId = context.getPlayerId();
        return resourceDao.findByStarIdAndDataIdAndRoundAndGameIdAndPlayerId(starId, dataId, round, gameId, playerId);
    }

    public Optional<Resource> findLatestByStarIdAndDataIdAndGameIdAndPlayerId(UUID starId, String dataId) {
        RequestContext context = requestContextHolder.get();
        UUID gameId = context.getGameId();
        UUID playerId = context.getPlayerId();
        return resourceDao.findLatestByStarIdAndDataIdAndGameIdAndPlayerId(starId, dataId, gameId, playerId);
    }

    public List<Resource> getByStarIdAndDataIdAndGameIdAndPlayerId(UUID starId, String dataId) {
        RequestContext context = requestContextHolder.get();
        UUID gameId = context.getGameId();
        UUID playerId = context.getPlayerId();
        return resourceDao.getByStarIdAndDataIdAndGameIdAndPlayerId(starId, dataId, gameId, playerId);
    }

    public List<Resource> getByStarIdAndStorageTypeAndGameIdAndPlayerId(UUID starId, StorageType storageType) {
        RequestContext context = requestContextHolder.get();
        UUID gameId = context.getGameId();
        UUID playerId = context.getPlayerId();
        return resourceDao.getByStarIdAndStorageTypeAndGameIdAndPlayerId(starId, storageType, gameId, playerId);
    }

    public List<Resource> getLatestByStarIdAndStorageType(UUID starId, StorageType storageType) {
        return getByStarIdAndStorageTypeAndGameIdAndPlayerId(starId, storageType).stream()
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
