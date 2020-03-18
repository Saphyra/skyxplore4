package com.github.saphyra.skyxplore.app.domain.star;

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
public class StarRepositoryTest {
    private static final String STAR_ID_1 = "star-id-1";
    private static final String STAR_ID_2 = "star-id-2";
    private static final String GAME_ID_1 = "game-id-1";
    private static final String GAME_ID_2 = "game-id-2";
    private static final String PLAYER_ID_1 = "player-id-1";
    private static final String PLAYER_ID_2 = "player-id-2";

    @Autowired
    private StarRepository underTest;

    @After
    public void clear() {
        underTest.deleteAll();
    }

    @Test
    public void deleteByGameId() {
        StarEntity entity1 = StarEntity.builder().starId(STAR_ID_1).gameId(GAME_ID_1).build();
        StarEntity entity2 = StarEntity.builder().starId(STAR_ID_2).gameId(GAME_ID_2).build();
        underTest.saveAll(Arrays.asList(entity1, entity2));

        underTest.deleteByGameId(GAME_ID_2);

        assertThat(underTest.findAll()).containsExactly(entity1);
    }

    @Test
    public void deleteByStarIdIn() {
        StarEntity entity1 = StarEntity.builder().starId(STAR_ID_1).gameId(GAME_ID_1).build();
        StarEntity entity2 = StarEntity.builder().starId(STAR_ID_2).gameId(GAME_ID_2).build();
        underTest.saveAll(Arrays.asList(entity1, entity2));

        underTest.deleteByStarIdIn(Arrays.asList(STAR_ID_2));

        assertThat(underTest.findAll()).containsExactly(entity1);
    }

    @Test
    public void findByStarIdAndOwnerId() {
        StarEntity entity1 = StarEntity.builder().starId(STAR_ID_1).gameId(GAME_ID_1).ownerId(PLAYER_ID_1).build();
        StarEntity entity2 = StarEntity.builder().starId(STAR_ID_2).gameId(GAME_ID_2).ownerId(PLAYER_ID_2).build();
        underTest.saveAll(Arrays.asList(entity1, entity2));

        Optional<StarEntity> result = underTest.findByStarIdAndOwnerId(STAR_ID_1, PLAYER_ID_1);

        assertThat(result).contains(entity1);
    }

    @Test
    public void getByGameId() {
        StarEntity entity1 = StarEntity.builder().starId(STAR_ID_1).gameId(GAME_ID_1).ownerId(PLAYER_ID_1).build();
        StarEntity entity2 = StarEntity.builder().starId(STAR_ID_2).gameId(GAME_ID_2).ownerId(PLAYER_ID_2).build();
        underTest.saveAll(Arrays.asList(entity1, entity2));

        List<StarEntity> result = underTest.getByGameId(GAME_ID_1);

        assertThat(result).containsExactly(entity1);
    }

    @Test
    public void getByOwnerId() {
        StarEntity entity1 = StarEntity.builder().starId(STAR_ID_1).gameId(GAME_ID_1).ownerId(PLAYER_ID_1).build();
        StarEntity entity2 = StarEntity.builder().starId(STAR_ID_2).gameId(GAME_ID_2).ownerId(PLAYER_ID_2).build();
        underTest.saveAll(Arrays.asList(entity1, entity2));

        List<StarEntity> result = underTest.getByOwnerId(PLAYER_ID_1);

        assertThat(result).containsExactly(entity1);
    }
}