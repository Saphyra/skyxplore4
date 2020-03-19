package com.github.saphyra.skyxplore.app.rest.view.game;


import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class GameView {
    @NonNull
    private final UUID gameId;

    @NonNull
    private final String gameName;
}
