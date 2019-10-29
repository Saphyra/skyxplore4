package com.github.saphyra.skyxplore.data.gamedata.domain.building;

import com.github.saphyra.skyxplore.data.gamedata.domain.GameDataItem;
import com.github.saphyra.skyxplore.game.module.map.surface.domain.SurfaceType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public abstract class BuildingData extends GameDataItem {
    private String buildingType;
    private boolean defaultBuilding = false;

    public abstract List<SurfaceType> getPlaceableSurfaceTypes();

    public abstract SurfaceType getPrimarySurfaceType();
}
