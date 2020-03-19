package com.github.saphyra.skyxplore.app.common.event;

import java.util.UUID;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class GameCreatedEvent {
    @NonNull
    private final UUID gameId;
}
