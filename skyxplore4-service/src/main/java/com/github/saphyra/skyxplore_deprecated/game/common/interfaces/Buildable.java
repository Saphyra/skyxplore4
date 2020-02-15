package com.github.saphyra.skyxplore_deprecated.game.common.interfaces;

import com.github.saphyra.skyxplore_deprecated.game.dao.common.ConstructionRequirements;

public interface Buildable {
    Integer getCurrentWorkPoints();

    ConstructionRequirements getConstructionRequirements();
}
