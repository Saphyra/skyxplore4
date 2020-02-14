package com.github.saphyra.skyxplore.game.service.map.connection.creation;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
class ConnectionCreationConfiguration {
    @Value("${com.github.saphyra.skyxplore.game.connection.maxDistance}")
    private int maxDistance;

    @Value("${com.github.saphyra.skyxplore.game.connection.maxNumberOfConnections}")
    private int maxNumberOfConnections;
}
