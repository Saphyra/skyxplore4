package com.github.saphyra.skyxplore.game.common.interfaces;

import java.util.UUID;

public interface DeletableByGameId {
    void deleteByGameId(UUID gameId);
}
