package com.github.saphyra.skyxplore.data.gamedata.domain.building.production;

import com.github.saphyra.skyxplore.game.dao.common.ConstructionRequirements;
import com.github.saphyra.skyxplore.game.dao.map.surface.SurfaceType;
import lombok.Data;

import java.util.List;

@Data
public class Production {
    private List<SurfaceType> placed;
    private Integer amount;
    private ConstructionRequirements constructionRequirements;
}
