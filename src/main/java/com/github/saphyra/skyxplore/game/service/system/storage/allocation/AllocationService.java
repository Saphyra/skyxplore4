package com.github.saphyra.skyxplore.game.service.system.storage.allocation;

import com.github.saphyra.skyxplore.data.gamedata.domain.resource.ResourceData;
import com.github.saphyra.skyxplore.game.dao.system.storage.allocation.Allocation;
import com.github.saphyra.skyxplore.game.dao.system.storage.allocation.AllocationCommandService;
import com.github.saphyra.skyxplore.game.dao.system.storage.allocation.AllocationType;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class AllocationService {
    private final AllocationCommandService allocationCommandService;
    private final IdGenerator idGenerator;

    public void allocate(UUID gameId, UUID starId, UUID externalReference, ResourceData resourceData, int amount, AllocationType allocationType, UUID playerId) {
        allocationCommandService.save(Allocation.builder()
            .allocationId(idGenerator.randomUUID())
            .gameId(gameId)
            .starId(starId)
            .playerId(playerId)
            .externalReference(externalReference)
            .dataId(resourceData.getId())
            .storageType(resourceData.getStorageType())
            .amount(amount)
            .allocationType(allocationType)
            .isNew(true)
            .build()
        );
    }
}
