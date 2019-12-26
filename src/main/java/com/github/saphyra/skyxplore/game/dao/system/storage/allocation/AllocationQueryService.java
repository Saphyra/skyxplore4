package com.github.saphyra.skyxplore.game.dao.system.storage.allocation;

import com.github.saphyra.skyxplore.common.context.RequestContext;
import com.github.saphyra.skyxplore.common.context.RequestContextHolder;
import com.github.saphyra.skyxplore.game.dao.system.storage.resource.StorageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Component
public class AllocationQueryService {
    private final AllocationDao allocationDao;
    private final RequestContextHolder requestContextHolder;

    public List<Allocation> getByStarIdAndStorageTypeAndGameIdAndPlayerId(UUID starId, StorageType storageType) {
        RequestContext context = requestContextHolder.get();
        UUID gameId = context.getGameId();
        UUID playerId = context.getPlayerId();
        return allocationDao.getByStarIdAndStorageTypeAndGameIdAndPlayerId(starId, storageType, gameId, playerId);
    }

    public List<Allocation> getByExternalReferenceAndGameIdAndPlayerId(UUID externalReference) {
        RequestContext context = requestContextHolder.get();
        UUID gameId = context.getGameId();
        UUID playerId = context.getPlayerId();
        return allocationDao.getByExternalReferenceAndGameIdAndPlayerId(externalReference, gameId, playerId);
    }

    public List<Allocation> getByStarIdAndDataIdAndGameIdAndPlayerId(UUID starId, String dataId) {
        RequestContext context = requestContextHolder.get();
        UUID gameId = context.getGameId();
        UUID playerId = context.getPlayerId();
        return allocationDao.getByStarIdAndDataIdAndGameIdAndPlayerId(starId, dataId, gameId, playerId);
    }
}
