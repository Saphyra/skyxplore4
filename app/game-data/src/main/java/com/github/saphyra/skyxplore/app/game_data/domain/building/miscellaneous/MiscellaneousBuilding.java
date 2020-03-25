package com.github.saphyra.skyxplore.app.game_data.domain.building.miscellaneous;

import java.util.List;

import com.github.saphyra.skyxplore.app.domain.common.SurfaceType;
import com.github.saphyra.skyxplore.app.game_data.domain.building.BuildingData;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
