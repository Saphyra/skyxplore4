package com.github.saphyra.skyxplore.game.module.system.citizen.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.Map;
import java.util.UUID;

@Builder
@Data
public class Citizen {
    @NonNull
    private final UUID citizenId;

    @NonNull
    private String citizenName;

    @NonNull
    private final UUID gameId;

    @NonNull
    private final UUID userId;

    @NonNull
    private LocationType locationType;

    @NonNull
    private UUID locationId;

    @NonNull
    private Integer morale;

    @NonNull
    private Integer satiety;

    @NonNull
    private Map<SkillType, Skill> skills;
}
