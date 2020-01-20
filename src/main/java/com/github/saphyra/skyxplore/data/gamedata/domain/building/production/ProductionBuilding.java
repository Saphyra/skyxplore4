package com.github.saphyra.skyxplore.data.gamedata.domain.building.production;

import com.github.saphyra.skyxplore.data.gamedata.domain.building.BuildingData;
import com.github.saphyra.skyxplore.game.dao.map.surface.SurfaceType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProductionBuilding extends BuildingData {
    private Map<String, Production> gives;
    private Integer workers;
    private Integer cache;
    private SurfaceType primarySurfaceType;

    @Override
    public List<SurfaceType> getPlaceableSurfaceTypes() {
        return gives.values().stream()
                .flatMap(production -> production.getPlaced().stream())
                .collect(Collectors.toList());
    }

    @Override
    public SurfaceType getPrimarySurfaceType() {
        if (isNull(primarySurfaceType)) {
            primarySurfaceType = getPlaceableSurfaceTypes().get(0);
        }
        return primarySurfaceType;
    }
}
