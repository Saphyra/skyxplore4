package com.github.saphyra.skyxplore.app.domain.game.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class GameCommandServiceTest {
    private static final UUID GAME_ID = UUID.randomUUID();

    @Mock
    private GameDao gameDao;

    @InjectMocks
    private GameCommandService underTest;

    @Mock
    private Game game;

    @Test
    public void delete() {
        underTest.delete(game);

        verify(gameDao).delete(game);
    }

    @Test
    public void deleteAll() {
        List<Game> games = Arrays.asList(game);

        underTest.deleteAll(games);

        verify(gameDao).deleteAll(games);
    }

    @Test
    public void deleteByGameId() {
        underTest.deleteByGameId(GAME_ID);

        verify(gameDao).deleteByGameId(GAME_ID);
    }

    @Test
    public void save() {
        underTest.save(game);

        verify(gameDao).save(game);
    }

    @Test
    public void saveAll() {
        List<Game> games = Arrays.asList(game);

        underTest.saveAll(games);

        verify(gameDao).saveAll(games);
    }

    @Test
    public void getType() {
        Class<Game> result = underTest.getType();

        assertThat(result).isEqualTo(Game.class);
    }
}