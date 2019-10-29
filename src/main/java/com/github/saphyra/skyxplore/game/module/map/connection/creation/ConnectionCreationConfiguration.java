package com.github.saphyra.skyxplore.game.module.map.connection.creation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
class ConnectionCreationConfiguration {
    @Value("${com.github.saphyra.skyxplore.game.connection.maxDistance}")
    private int maxDistance;

    @Value("${com.github.saphyra.skyxplore.game.connection.maxNumberOfConnections}")
    private int maxNumberOfConnections;
}
