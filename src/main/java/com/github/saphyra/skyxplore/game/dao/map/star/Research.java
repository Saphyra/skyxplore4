package com.github.saphyra.skyxplore.game.dao.map.star;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Builder
@Data
@AllArgsConstructor
public class Research {
    @NonNull
    private final UUID researchId;

    @NonNull
    private final UUID starId;

    @NonNull
    private final String dataId;
}
