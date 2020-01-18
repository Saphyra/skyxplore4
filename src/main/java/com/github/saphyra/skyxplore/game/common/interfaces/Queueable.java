package com.github.saphyra.skyxplore.game.common.interfaces;

import java.time.OffsetDateTime;
import java.util.UUID;

public interface Queueable extends Prioritizable {
    QueueType getQueueType();

    UUID getId();

    OffsetDateTime addedAt();
}
