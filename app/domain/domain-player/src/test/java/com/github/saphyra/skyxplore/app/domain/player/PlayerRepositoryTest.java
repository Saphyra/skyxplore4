package com.github.saphyra.skyxplore.app.domain.player;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.saphyra.skyxplore.app.common.test.RepositoryTestConfiguration;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = RepositoryTestConfiguration.class)
public class PlayerRepositoryTest {
    private static final String PLAYER_ID_1 = "player-id-1";
    private static final String PLAYER_ID_2 = "player-id-2";
    private static final String GAME_ID_1 = "game-id-1";
    private static final String GAME_ID_2 = "game-id-2";

    @Autowired
    private PlayerRepository underTest;

    @After
    public void clear() {
        underTest.deleteAll();
    }

    @Test
    public void deleteByGameId() {
        PlayerEntity player1 = PlayerEntity.builder()
            .playerId(PLAYER_ID_1)
            .gameId(GAME_ID_1)
            .build();

        PlayerEntity player2 = PlayerEntity.builder()
            .playerId(PLAYER_ID_2)
            .gameId(GAME_ID_2)
            .build();
        underTest.saveAll(Arrays.asList(player1, player2));

        underTest.deleteByGameId(GAME_ID_1);

        assertThat(underTest.findAll()).containsExactly(player2);
    }

    @Test
    public void deleteByPlayerIdIn() {
        PlayerEntity player1 = PlayerEntity.builder()
            .playerId(PLAYER_ID_1)
            .gameId(GAME_ID_1)
            .build();

        PlayerEntity player2 = PlayerEntity.builder()
            .playerId(PLAYER_ID_2)
            .gameId(GAME_ID_2)
            .build();
        underTest.saveAll(Arrays.asList(player1, player2));

        underTest.deleteByPlayerIdIn(Arrays.asList(PLAYER_ID_1));

        assertThat(underTest.findAll()).containsExactly(player2);
    }

    @Test
    public void findByGameIdAndPlayerId() {
        PlayerEntity player1 = PlayerEntity.builder()
            .playerId(PLAYER_ID_1)
            .gameId(GAME_ID_1)
            .build();
        underTest.saveAll(Arrays.asList(player1));

        Optional<PlayerEntity> result = underTest.findByGameIdAndPlayerId(GAME_ID_1, PLAYER_ID_1);

        assertThat(result).contains(player1);
    }

    @Test
    public void getByGameId() {
        PlayerEntity player1 = PlayerEntity.builder()
            .playerId(PLAYER_ID_1)
            .gameId(GAME_ID_1)
            .build();

        PlayerEntity player2 = PlayerEntity.builder()
            .playerId(PLAYER_ID_2)
            .gameId(GAME_ID_2)
            .build();
        underTest.saveAll(Arrays.asList(player1, player2));

        List<PlayerEntity> result = underTest.getByGameId(GAME_ID_1);

        assertThat(result).containsExactly(player1);
    }
}