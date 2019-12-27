package com.github.saphyra.skyxplore.game.dao.system.citizen;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.UUID;

@Data
@Builder
public class Skill {
    @NonNull
    private final UUID skillId;

    @NonNull
    private final UUID citizenId;

    @NonNull
    private final UUID gameId;

    @NonNull
    private final SkillType skillType;

    @NonNull
    private Integer level;

    @NonNull
    private Integer experience;

    @NonNull
    private Integer nextLevel;

    private final boolean isNew;
}
