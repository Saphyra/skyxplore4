package com.github.saphyra.skyxplore.app.domain.game.service;

import com.github.saphyra.exceptionhandling.exception.NotFoundException;
import com.github.saphyra.skyxplore.app.common.event.GameDeletedEvent;
import com.github.saphyra.skyxplore.app.common.exception_handling.ErrorCode;
import com.github.saphyra.skyxplore.app.common.game_context.CommandService;
import com.github.saphyra.skyxplore.app.common.request_context.RequestContext;
import com.github.saphyra.skyxplore.app.common.request_context.RequestContextHolder;
import com.github.saphyra.skyxplore.app.common.service.ExecutorServiceBean;
import com.github.saphyra.skyxplore.app.domain.game.domain.Game;
import com.github.saphyra.skyxplore.app.domain.game.domain.GameCommandService;
import com.github.saphyra.skyxplore.app.domain.game.domain.GameQueryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class GameDeletionServiceTest {
    private static final UUID GAME_ID = UUID.randomUUID();

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @Mock
    private CommandService commandService;

    @Mock
    private ExecutorServiceBean executorServiceBean;

    @Mock
    private GameCommandService gameCommandService;

    @Mock
    private GameQueryService gameQueryService;

    @Mock
    private RequestContextHolder requestContextHolder;

    private GameDeletionService underTest;

    @Mock
    private Game game;

    @Before
    public void setUp() {
        underTest = GameDeletionService.builder()
            .applicationEventPublisher(applicationEventPublisher)
            .deletables(Arrays.asList(commandService))
            .executorServiceBean(executorServiceBean)
            .gameCommandService(gameCommandService)
            .gameQueryService(gameQueryService)
            .requestContextHolder(requestContextHolder)
            .build();
    }

    @Test
    public void gameNotFound() {
        given(gameQueryService.findByGameIdAndUserId(GAME_ID)).willReturn(Optional.empty());
        given(requestContextHolder.get()).willReturn(RequestContext.builder().userId(UUID.randomUUID()).build());

        Throwable ex = catchThrowable(() -> underTest.deleteGame(GAME_ID));

        assertThat(ex).isInstanceOf(NotFoundException.class);
        NotFoundException exception = (NotFoundException) ex;
        assertThat(exception.getErrorMessage().getErrorCode()).isEqualTo(ErrorCode.GAME_NOT_FOUND.name());
    }

    @Test
    public void deleteGame() {
        given(gameQueryService.findByGameIdAndUserId(GAME_ID)).willReturn(Optional.of(game));
        doAnswer(invocationOnMock -> {
            ((Runnable) invocationOnMock.getArgument(0)).run();
            return null;
        }).when(executorServiceBean).execute(any());

        underTest.deleteGame(GAME_ID);

        verify(commandService).deleteByGameId(GAME_ID);
        verify(applicationEventPublisher).publishEvent(new GameDeletedEvent());
        verify(gameCommandService).delete(game);
    }
}