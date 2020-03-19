package com.github.saphyra.skyxplore.app.service.game_creation.game;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.app.common.service.DomainSaverService;
import com.github.saphyra.skyxplore.app.domain.game.Game;

@RunWith(MockitoJUnitRunner.class)
public class GameCreationServiceTest {
    private static final String GAME_NAME = "game-name";
    private static final UUID USER_ID = UUID.randomUUID();
    private static final UUID GAME_ID = UUID.randomUUID();

    @Mock
    private DomainSaverService domainSaverService;

    @Mock
    private GameFactory gameFactory;

    @Mock
    private GameNameValidator gameNameValidator;

    @InjectMocks
    private GameCreationService underTest;

    @Mock
    private Game game;

    @Test
    public void createGame() {
        given(gameFactory.create(USER_ID, GAME_NAME)).willReturn(game);
        given(game.getGameId()).willReturn(GAME_ID);


        UUID result = underTest.createGame(GAME_NAME, USER_ID);

        verify(gameNameValidator).validate(GAME_NAME);
        verify(domainSaverService).add(game);

        assertThat(result).isEqualTo(GAME_ID);
    }
}