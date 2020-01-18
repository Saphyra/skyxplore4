package com.github.saphyra.skyxplore.game.common.interfaces;

import com.github.saphyra.skyxplore.game.dao.common.ConstructionRequirements;

public interface Buildable {
    Integer getCurrentWorkPoints();
    ConstructionRequirements getConstructionRequirements();
}
