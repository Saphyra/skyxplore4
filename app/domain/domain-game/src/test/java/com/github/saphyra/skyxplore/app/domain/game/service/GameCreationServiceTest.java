package com.github.saphyra.skyxplore.app.domain.game.service;


import com.github.saphyra.skyxplore.app.common.event.GameCreatedEvent;
import com.github.saphyra.skyxplore.app.common.request_context.RequestContext;
import com.github.saphyra.skyxplore.app.common.request_context.RequestContextHolder;
import com.github.saphyra.skyxplore.app.common.service.DomainSaverService;
import com.github.saphyra.skyxplore.app.domain.game.domain.Game;
import com.github.saphyra.skyxplore.app.domain.game.domain.GameCommandService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.ApplicationEventPublisher;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class GameCreationServiceTest {
    private static final String GAME_NAME = "game-name";
    private static final UUID USER_ID = UUID.randomUUID();
    private static final UUID GAME_ID = UUID.randomUUID();

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @Mock
    private DomainSaverService domainSaverService;

    @Mock
    private GameCommandService gameCommandService;

    @Mock
    private GameFactory gameFactory;

    @Mock
    private GameNameValidator gameNameValidator;

    @Mock
    private RequestContextHolder requestContextHolder;

    @InjectMocks
    private GameCreationService underTest;

    @Mock
    private RequestContext requestContext;

    @Mock
    private Game game;

    @Test
    public void createGame() {
        given(requestContextHolder.get()).willReturn(requestContext);
        given(requestContext.getUserId()).willReturn(USER_ID);
        given(gameFactory.create(USER_ID, GAME_NAME)).willReturn(game);
        given(game.getGameId()).willReturn(GAME_ID);


        UUID result = underTest.createGame(GAME_NAME);

        verify(gameNameValidator).validate(GAME_NAME);
        verify(gameCommandService).save(game);
        verify(applicationEventPublisher).publishEvent(new GameCreatedEvent(GAME_ID));
        verify(domainSaverService).save();

        assertThat(result).isEqualTo(GAME_ID);
    }
}