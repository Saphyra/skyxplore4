package com.github.saphyra.skyxplore.game.service;

import java.util.List;
import java.util.UUID;

import com.github.saphyra.skyxplore.game.common.interfaces.Queueable;

public interface QueueItemProvider {
    List<? extends Queueable> getQueueOfStar(UUID starId);
}
