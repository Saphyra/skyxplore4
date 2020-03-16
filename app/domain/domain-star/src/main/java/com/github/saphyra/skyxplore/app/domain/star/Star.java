package com.github.saphyra.skyxplore.app.domain.star;

import java.util.UUID;

import com.github.saphyra.skyxplore.app.domain.coordinate.Coordinate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@AllArgsConstructor
@Data
@Builder
public class Star {
    @NonNull
    private final UUID starId;

    @NonNull
    private final UUID gameId;

    @NonNull
    private String starName;

    @NonNull
    private Coordinate coordinate;

    @NonNull
    private UUID ownerId;

    private final boolean isNew;
}
