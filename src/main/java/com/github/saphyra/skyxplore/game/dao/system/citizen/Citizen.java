package com.github.saphyra.skyxplore.game.dao.system.citizen;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

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
    private final UUID ownerId;

    @NonNull
    private LocationType locationType;

    @NonNull
    private UUID locationId;

    @NonNull
    private Integer morale;

    @NonNull
    private Integer satiety;
}
