package com.github.saphyra.skyxplore.game.common.interfaces;

import java.util.List;

public interface SaveAllDao<T> {
    void saveAll(List<T> domains);

    Class<T> getType();
}
