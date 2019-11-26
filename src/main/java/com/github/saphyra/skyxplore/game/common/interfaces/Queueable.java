package com.github.saphyra.skyxplore.game.common.interfaces;

import java.util.UUID;

import com.github.saphyra.skyxplore.game.dao.common.ConstructionRequirements;
import com.github.saphyra.skyxplore.game.dao.system.construction.ConstructionStatus;

public interface Queueable extends Prioritizable {
    QueueType getQueueType();

    UUID getId();

    ConstructionStatus getConstructionStatus();

    ConstructionRequirements getConstructionRequirements();

    String getDataId();
}
