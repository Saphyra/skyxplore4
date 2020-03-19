package com.github.saphyra.skyxplore.app.common.event;

import java.util.UUID;

import lombok.Data;

@Data
public class UserDeletedEvent {
    private final UUID userId;
}
