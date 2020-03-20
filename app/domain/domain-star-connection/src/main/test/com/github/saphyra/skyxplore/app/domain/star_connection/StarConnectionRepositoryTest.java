package com.github.saphyra.skyxplore.app.domain.star_connection;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.saphyra.skyxplore.app.common.test.RepositoryTestConfiguration;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = RepositoryTestConfiguration.class)
public class StarConnectionRepositoryTest {
    private static final String ENTITY_ID_1 = "entity-id-1";
    private static final String ENTITY_ID_2 = "entity-id-2";
    private static final String GAME_ID_1 = "game-id-1";
    private static final String GAME_ID_2 = "game-id-2";
    @Autowired
    private StarConnectionRepository underTest;

    @Test
    public void deleteByConnectionIdIn() {
        StarConnectionEntity entity1 = StarConnectionEntity.builder()
            .connectionId(ENTITY_ID_1)
            .build();
        StarConnectionEntity entity2 = StarConnectionEntity.builder()
            .connectionId(ENTITY_ID_2)
            .build();
        underTest.saveAll(Arrays.asList(entity1, entity2));

        underTest.deleteByConnectionIdIn(Arrays.asList(ENTITY_ID_1));

        assertThat(underTest.findAll()).containsExactly(entity2);
    }

    @Test
    public void deleteByGameId() {
        StarConnectionEntity entity1 = StarConnectionEntity.builder()
            .connectionId(ENTITY_ID_1)
            .gameId(GAME_ID_1)
            .build();
        StarConnectionEntity entity2 = StarConnectionEntity.builder()
            .connectionId(ENTITY_ID_2)
            .gameId(GAME_ID_2)
            .build();
        underTest.saveAll(Arrays.asList(entity1, entity2));

        underTest.deleteByGameId(GAME_ID_2);

        assertThat(underTest.findAll()).containsExactly(entity1);
    }

    @Test
    public void getByGameId() {
        StarConnectionEntity entity1 = StarConnectionEntity.builder()
            .connectionId(ENTITY_ID_1)
            .gameId(GAME_ID_1)
            .build();
        StarConnectionEntity entity2 = StarConnectionEntity.builder()
            .connectionId(ENTITY_ID_2)
            .gameId(GAME_ID_2)
            .build();
        underTest.saveAll(Arrays.asList(entity1, entity2));

        List<StarConnectionEntity> result = underTest.getByGameId(GAME_ID_2);

        assertThat(result).containsExactly(entity2);
    }
}