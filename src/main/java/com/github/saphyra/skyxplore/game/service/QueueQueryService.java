package com.github.saphyra.skyxplore.game.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.game.common.interfaces.Queueable;
import com.github.saphyra.skyxplore.game.dao.system.storage.allocation.Allocation;
import com.github.saphyra.skyxplore.game.rest.view.queue.QueueView;
import com.github.saphyra.skyxplore.game.service.system.storage.StorageQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class QueueQueryService {
    private final List<QueueItemProvider> queueSuppliers;
    private final StorageQueryService storageQueryService;

    public List<QueueView> getQueueOfStar(UUID starId) {
        return queueSuppliers.stream()
            .flatMap(listSupplier -> listSupplier.getQueueOfStar(starId).stream())
            .map(this::convert)
            .collect(Collectors.toList());
    }

    private QueueView convert(Queueable queueItem) {
        return QueueView.builder()
            .status(queueItem.getConstructionStatus())
            .currentWorkPoints(queueItem.getCurrentWorkPoints())
            .requiredResourcesAmount(queueItem.getConstructionRequirements().getRequiredWorkPoints())
            .requiredResourcesAmount(getRequiredResourcesAmount(queueItem))
            .allocatedResourcesAmount(getAllocatedResourcesAmount(queueItem))
            .queueType(queueItem.getQueueType())
            .queueItemId(queueItem.getId())
            .priority(queueItem.getPriority())
            .dataId(queueItem.getDataId())
            .build();
    }


    private int getRequiredResourcesAmount(Queueable queueItem) {
        return queueItem.getConstructionRequirements()
            .getRequiredResources()
            .values()
            .stream()
            .mapToInt(Integer::intValue)
            .sum();
    }

    private int getAllocatedResourcesAmount(Queueable queueItem) {
        return storageQueryService.getAllocationsByExternalReference(queueItem.getId()).stream()
            .mapToInt(Allocation::getAmount)
            .sum();
    }
}
