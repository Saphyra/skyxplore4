package com.github.saphyra.skyxplore.data.gamedata.building.storage;

import java.util.Arrays;
import java.util.List;

import com.github.saphyra.skyxplore.data.gamedata.building.Building;
import com.github.saphyra.skyxplore.game.map.surface.domain.SurfaceType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class StorageBuilding extends Building {
    private String stores;
    private Integer capacity;

    @Override
    public List<SurfaceType> getPlaceableSurfaceTypes() {
        return Arrays.asList(SurfaceType.CONCRETE);
    }
}
