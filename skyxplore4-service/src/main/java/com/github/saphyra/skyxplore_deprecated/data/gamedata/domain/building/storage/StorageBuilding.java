package com.github.saphyra.skyxplore_deprecated.data.gamedata.domain.building.storage;

import com.github.saphyra.skyxplore_deprecated.data.gamedata.domain.building.BuildingData;
import com.github.saphyra.skyxplore_deprecated.game.dao.map.surface.SurfaceType;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.storage.resource.StorageType;
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
