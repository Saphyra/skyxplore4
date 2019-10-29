package com.github.saphyra.skyxplore.data.gamedata.domain.building.storage;

import com.github.saphyra.skyxplore.data.gamedata.domain.building.BuildingData;
import com.github.saphyra.skyxplore.game.module.map.surface.domain.SurfaceType;
import com.github.saphyra.skyxplore.game.module.system.resource.domain.StorageType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class StorageBuilding extends BuildingData {
    private StorageType stores;
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
