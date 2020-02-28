package com.github.saphyra.skyxplore.app.common.event;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
@Data
public class GameCreatedEvent {
    @NonNull
    private final UUID gameId;
}
