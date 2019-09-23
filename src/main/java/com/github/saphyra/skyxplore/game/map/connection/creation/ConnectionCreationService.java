package com.github.saphyra.skyxplore.game.map.connection.creation;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.github.saphyra.skyxplore.common.event.StarsCreatedEvent;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConnectionCreationService {
    @EventListener
    void starsCreatedEventListener(StarsCreatedEvent event) {
        throw new UnsupportedOperationException();
    }
}
