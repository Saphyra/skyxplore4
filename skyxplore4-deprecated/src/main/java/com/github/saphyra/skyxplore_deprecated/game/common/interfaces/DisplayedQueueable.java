package com.github.saphyra.skyxplore_deprecated.game.common.interfaces;

import com.github.saphyra.skyxplore_deprecated.game.dao.system.construction.ConstructionStatus;

public interface DisplayedQueueable extends Queueable, Buildable {
    ConstructionStatus getConstructionStatus();

    String getAdditionalData();
}
