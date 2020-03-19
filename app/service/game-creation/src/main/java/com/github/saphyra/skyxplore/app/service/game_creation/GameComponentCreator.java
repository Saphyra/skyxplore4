package com.github.saphyra.skyxplore.app.service.game_creation;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.app.common.event.GameCreatedEvent;
import com.github.saphyra.skyxplore.app.common.request_context.RequestContextHolder;
import com.github.saphyra.skyxplore.app.service.game_creation.star.StarCreationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
//TODO unit test
public class GameComponentCreator {
    private final StarCreationService starCreationService;
    private final RequestContextHolder requestContextHolder;

    @EventListener
    public void createGameComponents(GameCreatedEvent event) {
        log.info("Creating game components for gameId {}", event.getGameId());
        starCreationService.createStars(
            requestContextHolder.get().getUserId(),
            event.getGameId()
        );
    }
}
