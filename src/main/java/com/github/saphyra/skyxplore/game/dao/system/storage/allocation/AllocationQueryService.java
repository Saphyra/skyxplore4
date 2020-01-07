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

    public List<Allocation> getByStarIdAndStorageTypeAndPlayerId(UUID starId, StorageType storageType) {
        RequestContext context = requestContextHolder.get();
        UUID playerId = context.getPlayerId();
        return allocationDao.getByStarIdAndStorageTypeAndPlayerId(starId, storageType, playerId);
    }

    public List<Allocation> getByExternalReferenceAndPlayerId(UUID externalReference) {
        RequestContext context = requestContextHolder.get();
        UUID playerId = context.getPlayerId();
        return allocationDao.getByExternalReferenceAndPlayerId(externalReference, playerId);
    }

    public List<Allocation> getByStarIdAndDataIdAndPlayerId(UUID starId, String dataId) {
        RequestContext context = requestContextHolder.get();
        UUID playerId = context.getPlayerId();
        return allocationDao.getByStarIdAndDataIdAndPlayerId(starId, dataId, playerId);
    }
}
