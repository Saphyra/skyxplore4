package com.github.saphyra.skyxplore.game.service.system.storage.allocation;

import java.util.UUID;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.common.event.GameDeletedEvent;
import com.github.saphyra.skyxplore.data.gamedata.domain.ResourceData;
import com.github.saphyra.skyxplore.game.dao.system.storage.allocation.Allocation;
import com.github.saphyra.skyxplore.game.dao.system.storage.allocation.AllocationDao;
import com.github.saphyra.skyxplore.game.dao.system.storage.allocation.AllocationType;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AllocationService {
    private final AllocationDao allocationDao;
    private final IdGenerator idGenerator;

    @EventListener
    void gameDeletedEventListener(GameDeletedEvent event) {
        allocationDao.deleteByGameIdAndUserId(event.getGameId(), event.getUserId());
    }

    public void allocate(UUID gameId, UUID userId, UUID starId, ResourceData resourceData, int amount, AllocationType allocationType) {
        allocationDao.save(Allocation.builder()
                .allocationId(idGenerator.randomUUID())
                .gameId(gameId)
                .userId(userId)
                .starId(starId)
                .dataId(resourceData.getId())
            .storageType(resourceData.getStorageType())
                .amount(amount)
                .allocationType(allocationType)
                .build());
    }
}
