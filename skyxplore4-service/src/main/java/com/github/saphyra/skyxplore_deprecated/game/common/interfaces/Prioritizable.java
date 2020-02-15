package com.github.saphyra.skyxplore_deprecated.game.common.interfaces;

import com.github.saphyra.skyxplore_deprecated.game.dao.system.priority.PriorityType;

public interface Prioritizable {
    PriorityType getPriorityType();

    Integer getPriority();
}
