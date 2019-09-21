package com.github.saphyra.skyxplore.common.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class GameDeletedEvent {
    @NonNull
    private final UUID userId;

    @NonNull
    private final UUID gameId;
}
