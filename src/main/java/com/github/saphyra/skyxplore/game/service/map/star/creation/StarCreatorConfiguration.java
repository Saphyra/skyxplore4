package com.github.saphyra.skyxplore.game.service.map.star.creation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class StarCreatorConfiguration {
    @Value("${com.github.saphyra.skyxplore.game.map.dimensions.x}")
    private int x;

    @Value("${com.github.saphyra.skyxplore.game.map.dimensions.y}")
    private int y;

    @Value("${com.github.saphyra.skyxplore.game.star.creation.attempts}")
    private int creationAttempts;

    @Value("${com.github.saphyra.skyxplore.game.star.creation.minStarDistance}")
    private int minStarDistance;

    public int getX() {
        return x - 1;
    }

    public int getY() {
        return y - 1;
    }
}
