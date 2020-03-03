package com.github.saphyra.skyxplore.app.service.game_creation;

import java.util.Collections;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.app.common.event.GameCreatedEvent;
import com.github.saphyra.skyxplore.app.common.request_context.RequestContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
//TODO unit test
public class GameComponentCreator {
    private final PlayerCreationService playerCreationService;
    private final RequestContextHolder requestContextHolder;

    @EventListener
    public void createGameComponents(GameCreatedEvent event) {
        log.info("Creating game components for gameId {}", event.getGameId());
        //TODO do after star creation
        playerCreationService.create(
            event.getGameId(),
            requestContextHolder.get().getUserId(),
            false,
            Collections.emptyList()
        );
    }
}
