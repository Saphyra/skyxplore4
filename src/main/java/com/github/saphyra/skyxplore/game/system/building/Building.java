package com.github.saphyra.skyxplore.game.system.building;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Building {
    private final UUID buildingId;
    private final String buildingType;
    private final UUID gameId;
    private final UUID userId;
    private int level;
    private UUID constructionId;
}
