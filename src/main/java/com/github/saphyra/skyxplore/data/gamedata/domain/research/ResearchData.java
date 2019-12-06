package com.github.saphyra.skyxplore.data.gamedata.domain.research;

import com.github.saphyra.skyxplore.data.gamedata.domain.GameDataItem;
import com.github.saphyra.skyxplore.game.dao.common.ConstructionRequirements;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ResearchData extends GameDataItem {
    private ConstructionRequirements constructionRequirements;
}
