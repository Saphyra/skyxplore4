package com.github.saphyra.skyxplore.game.service.queue;

import java.util.List;
import java.util.UUID;

import com.github.saphyra.skyxplore.game.common.interfaces.QueueType;
import com.github.saphyra.skyxplore.game.common.interfaces.Queueable;
import com.github.saphyra.skyxplore.game.rest.request.UpdatePriorityRequest;

public interface QueueItemDao {
    boolean canHandle(QueueType queueType);
    List<? extends Queueable> getQueueOfStar(UUID starId);

    void updatePriority(UUID starId, UUID queueItemId, UpdatePriorityRequest request);

    void cancel(UUID starId, UUID queueItemId, QueueType queueType);
}
