package com.github.saphyra.skyxplore.app.domain.game.domain;

import com.github.saphyra.skyxplore.app.common.utils.UuidConverter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class GameDaoTest {
    private static final UUID USER_ID = UUID.randomUUID();
    private static final String USER_ID_STRING = "user-id";
    private static final String GAME_ID_STRING = "game-id";
    private static final UUID GAME_ID = UUID.randomUUID();

    @Mock
    private GameConverter gameConverter;

    @Mock
    private GameRepository gameRepository;

    @Mock
    private UuidConverter uuidConverter;

    @InjectMocks
    private GameDao underTest;

    @Mock
    private GameEntity gameEntity;

    @Mock
    private Game game;

    @Before
    public void setUp() {
        given(uuidConverter.convertDomain(USER_ID)).willReturn(USER_ID_STRING);
        given(uuidConverter.convertDomain(GAME_ID)).willReturn(GAME_ID_STRING);
    }

    @Test
    public void getByUserId() {
        given(gameRepository.getByUserId(USER_ID_STRING)).willReturn(Arrays.asList(gameEntity));
        given(gameConverter.convertEntity(Arrays.asList(gameEntity))).willReturn(Arrays.asList(game));

        List<Game> result = underTest.getByUserId(USER_ID);

        assertThat(result).containsExactly(game);
    }

    @Test
    public void findByGameIdAndUserId() {
        given(gameRepository.findByGameIdAndUserId(GAME_ID_STRING, USER_ID_STRING)).willReturn(Optional.of(gameEntity));
        given(gameConverter.convertEntity(Optional.of(gameEntity))).willReturn(Optional.of(game));

        Optional<Game> result = underTest.findByGameIdAndUserId(GAME_ID, USER_ID);

        assertThat(result).contains(game);
    }

    @Test
    public void deleteByGameId() {
        given(uuidConverter.convertDomain(GAME_ID)).willReturn(GAME_ID_STRING);

        underTest.deleteByGameId(GAME_ID);

        verify(gameRepository).deleteById(GAME_ID_STRING);
    }
}