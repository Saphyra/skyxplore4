package com.github.saphyra.skyxplore_deprecated.game.dao.system.storage.allocation;

import com.github.saphyra.skyxplore.common.utils.UuidConverter;
import com.github.saphyra.skyxplore_deprecated.common.context.RequestContext;
import com.github.saphyra.skyxplore_deprecated.common.context.RequestContextHolder;
import com.github.saphyra.skyxplore_deprecated.game.common.interfaces.CommandService;
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
    private final UuidConverter uuidConverter;

    @Override
    public void delete(Allocation domain) {
        allocationDao.delete(domain);
    }

    public void delete(UUID allocationId) {
        allocationDao.deleteById(uuidConverter.convertDomain(allocationId));
    }

    @Override
    public void deleteAll(List<Allocation> domains) {
        allocationDao.deleteAll(domains);
    }

    @Override
    public void deleteByGameId(UUID gameId) {
        allocationDao.deleteByGameId(gameId);
    }

    @Override
    public void save(Allocation allocation) {
        allocationDao.save(allocation);
    }

    public void deleteByExternalReferenceAndPlayerId(UUID externalReference) {
        RequestContext context = requestContextHolder.get();
        UUID playerId = context.getPlayerId();
        allocationDao.deleteByExternalReferenceAndPlayerId(externalReference, playerId);
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
