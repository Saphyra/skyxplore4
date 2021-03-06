package com.github.saphyra.skyxplore.game.newround.order.provider;

import com.github.saphyra.skyxplore.game.newround.order.Order;

import java.util.List;
import java.util.UUID;

public interface OrderProvider {
    List<Order> getForStar(UUID starId);
}
