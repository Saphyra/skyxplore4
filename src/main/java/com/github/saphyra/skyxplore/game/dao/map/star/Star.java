package com.github.saphyra.skyxplore.game.dao.map.star;

import com.github.saphyra.skyxplore.game.dao.common.coordinate.Coordinate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Data
@Builder
public class Star  {
    @NonNull
    private final UUID starId;

    @NonNull
    private final UUID gameId;

    @NonNull
    private final String starName;

    @NonNull
    private Coordinate coordinate;

    @NonNull
    private UUID ownerId;

    @NonNull
    private List<Research> researches;

    private final boolean isNew;
}
