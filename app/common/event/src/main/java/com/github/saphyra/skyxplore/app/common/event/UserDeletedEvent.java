package com.github.saphyra.skyxplore.app.common.event;

import lombok.Data;

import java.util.UUID;

@Data
public class UserDeletedEvent {
    private final UUID userId;
}
