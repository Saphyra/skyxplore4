package com.github.saphyra.skyxplore.game.rest.view.system;

import com.github.saphyra.skyxplore.game.dao.system.citizen.SkillType;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class SkillView {
    @NonNull
    private final SkillType skillType;

    @NonNull
    private final Integer level;

    @NonNull
    private final Integer experience;

    @NonNull
    private final Integer nextLevel;
}
