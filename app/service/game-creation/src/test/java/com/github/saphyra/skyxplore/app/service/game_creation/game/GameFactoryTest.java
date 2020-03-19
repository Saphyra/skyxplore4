package com.github.saphyra.skyxplore.app.service.game_creation.game;

import com.github.saphyra.skyxplore.app.domain.game.Game;
import com.github.saphyra.util.IdGenerator;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class GameFactoryTest {
    private static final UUID GAME_ID = UUID.randomUUID();
    private static final UUID USER_ID = UUID.randomUUID();
    private static final String GAME_NAME = "game-name";

    @Mock
    private IdGenerator idGenerator;

    @InjectMocks
    private GameFactory underTest;

    @Test
    public void createGame() {
        given(idGenerator.randomUUID()).willReturn(GAME_ID);

        Game result = underTest.create(USER_ID, GAME_NAME);

        assertThat(result.getGameId()).isEqualTo(GAME_ID);
        assertThat(result.getUserId()).isEqualTo(USER_ID);
        assertThat(result.getGameName()).isEqualTo(GAME_NAME);
        assertThat(result.getRound()).isEqualTo(1);
    }
}