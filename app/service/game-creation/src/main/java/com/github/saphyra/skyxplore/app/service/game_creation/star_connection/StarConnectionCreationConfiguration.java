package com.github.saphyra.skyxplore.app.service.game_creation.star_connection;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
class StarConnectionCreationConfiguration {
    @Value("${com.github.saphyra.skyxplore.game.connection.maxDistance}")
    private int maxDistance;

    @Value("${com.github.saphyra.skyxplore.game.connection.maxNumberOfConnections}")
    private int maxNumberOfConnections;
}
