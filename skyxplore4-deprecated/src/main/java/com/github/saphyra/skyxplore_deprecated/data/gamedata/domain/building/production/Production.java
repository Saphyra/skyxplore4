package com.github.saphyra.skyxplore_deprecated.data.gamedata.domain.building.production;

import com.github.saphyra.skyxplore_deprecated.game.dao.common.ConstructionRequirements;
import com.github.saphyra.skyxplore_deprecated.game.dao.map.surface.SurfaceType;
import com.github.saphyra.skyxplore_deprecated.game.dao.system.citizen.SkillType;
import lombok.Data;

import java.util.List;

@Data
public class Production {
    private List<SurfaceType> placed;
    private Integer amount;
    private ConstructionRequirements constructionRequirements;
    private SkillType requiredSkill;
}
