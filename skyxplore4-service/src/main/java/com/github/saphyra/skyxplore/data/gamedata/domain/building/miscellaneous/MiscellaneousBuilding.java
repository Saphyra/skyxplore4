package com.github.saphyra.skyxplore.data.gamedata.domain.building.miscellaneous;

import com.github.saphyra.skyxplore.data.gamedata.domain.building.BuildingData;
import com.github.saphyra.skyxplore.game.dao.map.surface.SurfaceType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class MiscellaneousBuilding extends BuildingData {
    private List<SurfaceType> placeableSurfaceTypes;
    private Integer workers;

    @Override
    public SurfaceType getPrimarySurfaceType() {
        return getPlaceableSurfaceTypes().get(0);
    }
}
