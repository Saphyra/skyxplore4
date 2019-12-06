package com.github.saphyra.skyxplore.game.rest.view.surface;

import com.github.saphyra.skyxplore.game.dao.common.ConstructionRequirements;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class BuildableBuildingView {
    @NonNull
    private final String dataId;

    @NonNull
    private final ConstructionRequirements constructionRequirements;
}
