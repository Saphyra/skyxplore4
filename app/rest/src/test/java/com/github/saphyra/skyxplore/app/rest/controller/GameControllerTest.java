package com.github.saphyra.skyxplore.app.rest.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.app.common.common_request.OneStringParamRequest;
import com.github.saphyra.skyxplore.app.common.utils.UuidConverter;
import com.github.saphyra.skyxplore.app.domain.game.Game;
import com.github.saphyra.skyxplore.app.domain.game.GameQueryService;
import com.github.saphyra.skyxplore.app.rest.view.game.GameView;
import com.github.saphyra.skyxplore.app.rest.view.game.GameViewConverter;
import com.github.saphyra.skyxplore.app.service.game.GameDeletionService;
import com.github.saphyra.skyxplore.app.service.game_creation.GameComponentCreator;

@RunWith(MockitoJUnitRunner.class)
public class GameControllerTest {
    private static final String GAME_NAME = "game-name";
    private static final UUID GAME_ID = UUID.randomUUID();
    private static final String GAME_ID_STRING = "game-id";

    @Mock
    private GameComponentCreator gameComponentCreator;

    @Mock
    private GameQueryService gameQueryService;

    @Mock
    private GameDeletionService gameDeletionService;

    @Mock
    private GameViewConverter gameViewConverter;

    @Mock
    private UuidConverter uuidConverter;

    @InjectMocks
    private GameController underTest;

    @Mock
    private Game game;

    @Mock
    private GameView gameView;

    @Test
    public void createGame() {
        given(gameComponentCreator.createGame(GAME_NAME)).willReturn(GAME_ID);
        given(uuidConverter.convertDomain(GAME_ID)).willReturn(GAME_ID_STRING);

        String result = underTest.createGame(new OneStringParamRequest(GAME_NAME));

        verify(gameComponentCreator).createGame(GAME_NAME);
        assertThat(result).isEqualTo(GAME_ID_STRING);
    }

    @Test
    public void deleteGame() {
        underTest.deleteGame(GAME_ID);

        verify(gameDeletionService).deleteGame(GAME_ID);
    }

    @Test
    public void getGames() {
        given(gameQueryService.getByUserId()).willReturn(Arrays.asList(game));
        given(gameViewConverter.convertDomain(Arrays.asList(game))).willReturn(Arrays.asList(gameView));

        List<GameView> result = underTest.getGames();

        Assertions.assertThat(result).containsExactly(gameView);
    }
}