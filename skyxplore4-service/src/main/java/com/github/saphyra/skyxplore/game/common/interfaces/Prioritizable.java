package com.github.saphyra.skyxplore.game.common.interfaces;

import com.github.saphyra.skyxplore.game.dao.system.priority.PriorityType;

public interface Prioritizable {
    PriorityType getPriorityType();

    Integer getPriority();
}
