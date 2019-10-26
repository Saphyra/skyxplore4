package com.github.saphyra.skyxplore.data.gamedata.building;

import com.github.saphyra.skyxplore.data.gamedata.GameDataItem;
import com.github.saphyra.skyxplore.game.map.surface.domain.SurfaceType;
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
