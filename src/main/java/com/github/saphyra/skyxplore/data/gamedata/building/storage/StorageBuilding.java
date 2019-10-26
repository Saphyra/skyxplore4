package com.github.saphyra.skyxplore.data.gamedata.building.storage;

import com.github.saphyra.skyxplore.data.gamedata.building.BuildingData;
import com.github.saphyra.skyxplore.game.map.surface.domain.SurfaceType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class StorageBuilding extends BuildingData {
    private String stores;
    private Integer capacity;

    @Override
    public List<SurfaceType> getPlaceableSurfaceTypes() {
        return Arrays.asList(SurfaceType.CONCRETE);
    }

    @Override
    public SurfaceType getPrimarySurfaceType() {
        return SurfaceType.CONCRETE;
    }
}
