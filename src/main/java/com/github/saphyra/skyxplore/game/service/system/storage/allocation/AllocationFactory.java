package com.github.saphyra.skyxplore.game.service.system.storage.allocation;

import com.github.saphyra.skyxplore.data.gamedata.domain.resource.ResourceData;
import com.github.saphyra.skyxplore.data.gamedata.domain.resource.ResourceDataService;
import com.github.saphyra.skyxplore.game.dao.system.storage.allocation.Allocation;
import com.github.saphyra.skyxplore.game.dao.system.storage.allocation.AllocationType;
import com.github.saphyra.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class AllocationFactory {
    private final IdGenerator idGenerator;
    private final ResourceDataService resourceDataService;

    public Allocation allocate(UUID gameId, UUID starId, UUID externalReference, String dataId, int amount, AllocationType allocationType, UUID playerId) {
        return allocate(
            gameId,
            starId,
            externalReference,
            resourceDataService.get(dataId),
            amount,
            allocationType,
            playerId
        );
    }

    public Allocation allocate(UUID gameId, UUID starId, UUID externalReference, ResourceData resourceData, int amount, AllocationType allocationType, UUID playerId) {
        return Allocation.builder()
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
            .build();
    }
}
