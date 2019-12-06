package com.github.saphyra.skyxplore.data.gamedata.domain.building.production;

import java.util.List;

import com.github.saphyra.skyxplore.game.dao.map.surface.SurfaceType;
import lombok.Data;

@Data
public class Production {
    private List<SurfaceType> placed;
    private Integer amount;
}
