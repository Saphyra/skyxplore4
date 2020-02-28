package com.github.saphyra.skyxplore.app.common.game_context;

import java.util.List;

public interface SaveAllDao<T> {
    void saveAll(List<T> domains);

    Class<T> getType();
}
