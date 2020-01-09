package com.github.saphyra.skyxplore.game.dao.system.storage.allocation;

import com.github.saphyra.skyxplore.common.context.RequestContext;
import com.github.saphyra.skyxplore.common.context.RequestContextHolder;
import com.github.saphyra.skyxplore.game.common.interfaces.CommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Component
public class AllocationCommandService implements CommandService<Allocation> {
    private final AllocationDao allocationDao;
    private final RequestContextHolder requestContextHolder;

    @Override
    public void delete(Allocation domain) {
        allocationDao.delete(domain);
    }

    @Override
    public void deleteByGameId(UUID gameId) {
        allocationDao.deleteByGameId(gameId);
    }

    @Override
    public void save(Allocation allocation) {
        allocationDao.save(allocation);
    }

    public void deleteByExternalReferenceAndGameIdAndPlayerId(UUID externalReference) {
        RequestContext context = requestContextHolder.get();
        UUID gameId = context.getGameId();
        UUID playerId = context.getPlayerId();
        allocationDao.deleteByExternalReferenceAndGameIdAndPlayerId(externalReference, gameId, playerId);
    }

    @Override
    public void saveAll(List<Allocation> domains) {
        allocationDao.saveAll(domains);
    }

    @Override
    public Class<Allocation> getType() {
        return Allocation.class;
    }
}
