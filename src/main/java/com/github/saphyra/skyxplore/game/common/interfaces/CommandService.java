package com.github.saphyra.skyxplore.game.common.interfaces;

import java.util.List;
import java.util.UUID;

public interface CommandService<T> extends SaveAllDao<T> {
    void delete(T domain);

    void deleteAll(List<T> domains);

    void deleteByGameId(UUID gameId);

    void save(T domain);
}
