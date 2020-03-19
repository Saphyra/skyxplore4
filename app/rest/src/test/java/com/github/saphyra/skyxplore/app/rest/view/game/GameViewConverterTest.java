package com.github.saphyra.skyxplore.app.rest.view.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.app.domain.game.Game;

@RunWith(MockitoJUnitRunner.class)
public class GameViewConverterTest {
    private static final UUID GAME_ID = UUID.randomUUID();
    private static final String GAME_NAME = "game-name";

    @InjectMocks
    private GameViewConverter underTest;

    @Mock
    private Game game;

    @Test
    public void convert() {
        given(game.getGameId()).willReturn(GAME_ID);
        given(game.getGameName()).willReturn(GAME_NAME);

        GameView result = underTest.convertDomain(game);

        assertThat(result.getGameId()).isEqualTo(GAME_ID);
        assertThat(result.getGameName()).isEqualTo(GAME_NAME);
    }
}