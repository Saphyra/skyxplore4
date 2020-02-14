package com.github.saphyra.skyxplore.game.newround.order;

public interface Order {
    Integer getPriority();

    void process();
}
