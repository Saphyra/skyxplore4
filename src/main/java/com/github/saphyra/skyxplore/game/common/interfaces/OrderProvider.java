package com.github.saphyra.skyxplore.game.common.interfaces;

import java.util.List;
import java.util.UUID;

public interface OrderProvider {
    List<Order> getForStar(UUID starId);
}
