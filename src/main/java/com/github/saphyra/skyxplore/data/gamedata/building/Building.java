package com.github.saphyra.skyxplore.data.gamedata.building;

import java.util.List;

import com.github.saphyra.skyxplore.data.gamedata.GameDataItem;
import com.github.saphyra.skyxplore.game.map.surface.domain.SurfaceType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public abstract class Building extends GameDataItem {
    private String buildingType;

    public abstract List<SurfaceType> getPlaceableSurfaceTypes();
}
