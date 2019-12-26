package com.github.saphyra.skyxplore.game.dao.system.storage.allocation;

import com.github.saphyra.skyxplore.common.context.RequestContext;
import com.github.saphyra.skyxplore.common.context.RequestContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Component
public class AllocationCommandService {
    private final AllocationDao allocationDao;
    private final RequestContextHolder requestContextHolder;

    public void save(Allocation allocation) {
        allocationDao.save(allocation);
    }

    public void deleteByExternalReferenceAndGameIdAndPlayerId(UUID externalReference) {
        RequestContext context = requestContextHolder.get();
        UUID gameId = context.getGameId();
        UUID playerId = context.getPlayerId();
        allocationDao.deleteByExternalReferenceAndGameIdAndPlayerId(externalReference, gameId, playerId);
    }
}
