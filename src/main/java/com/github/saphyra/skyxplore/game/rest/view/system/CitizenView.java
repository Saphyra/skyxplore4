package com.github.saphyra.skyxplore.game.rest.view.system;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class CitizenView {
    @NonNull
    private final UUID citizenId;

    @NonNull
    private final Integer morale;

    @NonNull
    private final Integer satiety;

    @NonNull
    private final List<SkillView> skills;
}
