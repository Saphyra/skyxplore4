package com.github.saphyra.skyxplore.game.service.queue;

import com.github.saphyra.skyxplore.game.common.interfaces.DisplayedQueueable;
import com.github.saphyra.skyxplore.game.common.interfaces.QueueType;
import com.github.saphyra.skyxplore.game.rest.request.UpdatePriorityRequest;

import java.util.List;
import java.util.UUID;

public interface QueueItemDao {
    boolean canHandle(QueueType queueType);

    List<? extends DisplayedQueueable> getQueueOfStar(UUID starId);

    void updatePriority(UUID starId, UUID queueItemId, UpdatePriorityRequest request);

    void cancel(UUID starId, UUID queueItemId, QueueType queueType);
}
