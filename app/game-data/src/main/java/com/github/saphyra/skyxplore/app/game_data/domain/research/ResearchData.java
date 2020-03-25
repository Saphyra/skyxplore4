package com.github.saphyra.skyxplore.app.game_data.domain.research;

import java.util.ArrayList;
import java.util.List;

import com.github.saphyra.skyxplore.app.domain.common.ConstructionRequirements;
import com.github.saphyra.skyxplore.app.game_data.domain.GameDataItem;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ResearchData extends GameDataItem {
    private ConstructionRequirements constructionRequirements;
    private List<Unlock> unlocks;
    private List<Unlock> usedIn = new ArrayList<>();
}
