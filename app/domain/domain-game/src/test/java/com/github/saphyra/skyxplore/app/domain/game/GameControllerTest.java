package com.github.saphyra.skyxplore.app.domain.game;

import com.github.saphyra.skyxplore.app.common.common_request.OneStringParamRequest;
import com.github.saphyra.skyxplore.app.common.utils.UuidConverter;
import com.github.saphyra.skyxplore.app.domain.game.domain.Game;
import com.github.saphyra.skyxplore.app.domain.game.domain.GameQueryService;
import com.github.saphyra.skyxplore.app.domain.game.service.GameCreationService;
import com.github.saphyra.skyxplore.app.domain.game.service.GameDeletionService;
import com.github.saphyra.skyxplore.app.domain.game.view.GameView;
import com.github.saphyra.skyxplore.app.domain.game.view.GameViewConverter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class GameControllerTest {
    private static final String GAME_NAME = "game-name";
    private static final UUID GAME_ID = UUID.randomUUID();
    private static final String GAME_ID_STRING = "game-id";

    @Mock
    private GameCreationService gameCreationService;

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
        given(gameCreationService.createGame(GAME_NAME)).willReturn(GAME_ID);
        given(uuidConverter.convertDomain(GAME_ID)).willReturn(GAME_ID_STRING);

        String result = underTest.createGame(new OneStringParamRequest(GAME_NAME));

        verify(gameCreationService).createGame(GAME_NAME);
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

        assertThat(result).containsExactly(gameView);
    }
}