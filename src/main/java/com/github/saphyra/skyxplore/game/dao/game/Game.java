package com.github.saphyra.skyxplore.game.dao.game;

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

    @NonNull
    private Integer round;

    public void incrementRound() {
        round++;
    }
}
