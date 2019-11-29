package com.github.saphyra.skyxplore.game.service.queue;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.game.common.interfaces.QueueType;
import com.github.saphyra.skyxplore.game.rest.request.UpdatePriorityRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class QueueService {
    private final List<QueueItemDao> queueItemDaos;

    public void updatePriority(UUID starId, UUID queueItemId, UpdatePriorityRequest request) {
        getQueueItemDaoByQueueType(request.getQueueType())
            .updatePriority(starId, queueItemId, request);
    }

    @Transactional
    public void cancel(UUID starId, UUID queueItemId, QueueType queueType) {
        getQueueItemDaoByQueueType(queueType)
            .cancel(starId, queueItemId, queueType);
    }

    private QueueItemDao getQueueItemDaoByQueueType(QueueType queueType) {
        return queueItemDaos.stream()
            .filter(queueItemDao -> queueItemDao.canHandle(queueType))
            .findFirst()
            .orElseThrow(() -> new IllegalStateException("No QueueItemDao can handle QueeuType " + queueType));
    }
}
