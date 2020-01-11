package com.github.saphyra.skyxplore.game.common.interfaces;

import java.util.UUID;

public interface CommandService<T> extends SaveAllDao<T> {
    void delete(T domain);

    void deleteByGameId(UUID gameId);

    void save(T domain);

}
