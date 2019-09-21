package com.github.saphyra.skyxplore.game.game.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.Id;
import java.util.UUID;

@Builder
@AllArgsConstructor
@Data
public class Game{
    @Id
    @NonNull
    private final UUID gameId;

    @NonNull
    private final UUID userId;

    @NonNull
    private final String gameName;
}
