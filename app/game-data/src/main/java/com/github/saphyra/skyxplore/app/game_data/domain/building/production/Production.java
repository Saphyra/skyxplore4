package com.github.saphyra.skyxplore.app.game_data.domain.building.production;

import java.util.List;

import com.github.saphyra.skyxplore.app.domain.common.ConstructionRequirements;
import com.github.saphyra.skyxplore.app.domain.common.SkillType;
import com.github.saphyra.skyxplore.app.domain.common.SurfaceType;
import lombok.Data;

@Data
public class Production {
    private List<SurfaceType> placed;
    private Integer amount;
    private ConstructionRequirements constructionRequirements;
    private SkillType requiredSkill;
}
