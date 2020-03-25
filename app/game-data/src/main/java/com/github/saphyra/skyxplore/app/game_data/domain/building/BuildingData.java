package com.github.saphyra.skyxplore.app.game_data.domain.building;

import java.util.List;
import java.util.Map;

import com.github.saphyra.skyxplore.app.domain.common.ConstructionRequirements;
import com.github.saphyra.skyxplore.app.domain.common.SurfaceType;
import com.github.saphyra.skyxplore.app.game_data.domain.GameDataItem;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public abstract class BuildingData extends GameDataItem {
    private String buildingType;
    private boolean defaultBuilding = false;
    private Map<Integer, ConstructionRequirements> constructionRequirements;

    public abstract List<SurfaceType> getPlaceableSurfaceTypes();

    public abstract SurfaceType getPrimarySurfaceType();
}
