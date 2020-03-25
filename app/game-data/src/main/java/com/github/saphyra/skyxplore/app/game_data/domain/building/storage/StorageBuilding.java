package com.github.saphyra.skyxplore.app.game_data.domain.building.storage;

import java.util.Arrays;
import java.util.List;

import com.github.saphyra.skyxplore.app.domain.common.StorageType;
import com.github.saphyra.skyxplore.app.domain.common.SurfaceType;
import com.github.saphyra.skyxplore.app.game_data.domain.building.BuildingData;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

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
