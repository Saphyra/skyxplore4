package com.github.saphyra.skyxplore.game.rest.view;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.UUID;

@Data
@AllArgsConstructor
public class GameView {
    @NonNull
    private final UUID gameId;

    @NonNull
    private final String gameName;
}
