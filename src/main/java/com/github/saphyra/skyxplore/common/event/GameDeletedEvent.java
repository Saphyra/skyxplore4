package com.github.saphyra.skyxplore.common.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.UUID;

@AllArgsConstructor
@Data
public class GameDeletedEvent {
    @NonNull
    private final UUID userId;

    @NonNull
    private final UUID gameId;
}
