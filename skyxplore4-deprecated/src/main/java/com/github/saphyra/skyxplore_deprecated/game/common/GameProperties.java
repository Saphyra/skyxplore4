package com.github.saphyra.skyxplore_deprecated.game.common;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class GameProperties {
    @Value("${game.devMode:false}")
    private boolean devMode;

    @Value("${com.github.saphyra.skyxplore.game.skill.initialNextLevel}")
    private Integer initialNextLevel;
}
