package com.github.saphyra.skyxplore.game.service.system.storage.allocation;

import com.github.saphyra.skyxplore.common.event.GameDeletedEvent;
import com.github.saphyra.skyxplore.data.gamedata.domain.resource.ResourceData;
import com.github.saphyra.skyxplore.game.dao.system.storage.allocation.Allocation;
import com.github.saphyra.skyxplore.game.dao.system.storage.allocation.AllocationDao;
import com.github.saphyra.skyxplore.game.dao.system.storage.allocation.AllocationType;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class AllocationService {
    private final AllocationDao allocationDao;
    private final IdGenerator idGenerator;

    @EventListener
    void gameDeletedEventListener(GameDeletedEvent event) {
        log.info("Deleting allocations for event {}", event);
        allocationDao.deleteByGameIdAndUserId(event.getGameId(), event.getUserId());
    }

    public void allocate(UUID gameId, UUID userId, UUID starId, UUID externalReference, ResourceData resourceData, int amount, AllocationType allocationType) {
        allocationDao.save(Allocation.builder()
            .allocationId(idGenerator.randomUUID())
            .gameId(gameId)
            .userId(userId)
            .starId(starId)
            .externalReference(externalReference)
            .dataId(resourceData.getId())
            .storageType(resourceData.getStorageType())
            .amount(amount)
            .allocationType(allocationType)
            .build());
    }
}
