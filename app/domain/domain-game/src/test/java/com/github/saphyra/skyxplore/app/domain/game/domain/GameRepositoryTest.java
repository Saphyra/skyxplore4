package com.github.saphyra.skyxplore.app.domain.game.domain;

import com.github.saphyra.skyxplore.app.common.test.RepositoryTestConfiguration;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = RepositoryTestConfiguration.class)
public class GameRepositoryTest {
    private static final String GAME_ID_1 = "game-id-1";
    private static final String USER_ID_1 = "user-id-1";
    private static final String USER_ID_2 = "user-id-2";
    private static final String GAME_ID_2 = "game-id-2";
    private static final String GAME_ID_3 = "game-id-3";
    private static final String GAME_NAME = "game-name";
    private static final Integer ROUND = 324;

    @Autowired
    private GameRepository underTest;

    @After
    public void clear() {
        underTest.deleteAll();
    }

    @Test
    public void findByGameIdAndUserId() {
        GameEntity entity1 = createEntity(GAME_ID_1, USER_ID_1);
        GameEntity entity2 = createEntity(GAME_ID_2, USER_ID_1);

        underTest.save(entity1);
        underTest.save(entity2);

        Optional<GameEntity> result = underTest.findByGameIdAndUserId(GAME_ID_1, USER_ID_1);

        assertThat(result).contains(entity1);
    }

    @Test
    public void getByUserId() {
        GameEntity entity1 = createEntity(GAME_ID_1, USER_ID_1);
        GameEntity entity2 = createEntity(GAME_ID_2, USER_ID_1);
        GameEntity entity3 = createEntity(GAME_ID_3, USER_ID_2);

        underTest.save(entity1);
        underTest.save(entity2);
        underTest.save(entity3);

        List<GameEntity> result = underTest.getByUserId(USER_ID_1);

        assertThat(result).containsExactly(entity1, entity2);
    }

    private GameEntity createEntity(String gameId, String userId) {
        return GameEntity.builder()
            .gameId(gameId)
            .userId(userId)
            .gameName(GAME_NAME)
            .round(ROUND)
            .build();
    }
}