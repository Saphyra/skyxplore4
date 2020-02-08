package com.github.saphyra.skyxplore.game.common.interfaces;

import com.github.saphyra.skyxplore.game.dao.system.construction.ConstructionStatus;

public interface DisplayedQueueable extends Queueable, Buildable {
    ConstructionStatus getConstructionStatus();

    String getAdditionalData();
}
