package com.github.saphyra.skyxplore.data.gamedata.building.production;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.github.saphyra.skyxplore.data.gamedata.building.Building;
import com.github.saphyra.skyxplore.game.map.surface.domain.SurfaceType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProductionBuilding extends Building {
    private Map<String, Production> gives;
    private Integer workers;

    @Override
    public List<SurfaceType> getPlaceableSurfaceTypes() {
        return gives.values().stream()
            .flatMap(production -> production.getPlaced().stream())
            .collect(Collectors.toList());
    }
}
