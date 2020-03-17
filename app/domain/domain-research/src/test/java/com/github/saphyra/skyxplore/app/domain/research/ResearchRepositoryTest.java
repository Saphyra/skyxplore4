package com.github.saphyra.skyxplore.app.domain.research;

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
public class ResearchRepositoryTest {
    private static final String ENTITY_ID_1 = "entity-id-1";
    private static final String ENTITY_ID_2 = "entity-id-2";
    private static final String GAME_ID_1 = "game-id-1";
    private static final String GAME_ID_2 = "game-id-2";
    private static final String STAR_ID_1 = "star-id-1";
    private static final String PLAYER_ID_1 = "player-id-1";
    private static final String PLAYER_ID_2 = "player-id-2";

    @Autowired
    private ResearchRepository underTest;

    @Test
    public void deleteByGameId() {
        ResearchEntity entity1 = ResearchEntity.builder()
            .researchId(ENTITY_ID_1)
            .gameId(GAME_ID_1)
            .build();

        ResearchEntity entity2 = ResearchEntity.builder()
            .researchId(ENTITY_ID_2)
            .gameId(GAME_ID_2)
            .build();
        underTest.saveAll(Arrays.asList(entity1, entity2));

        underTest.deleteByGameId(GAME_ID_2);

        assertThat(underTest.findAll()).containsExactly(entity1);
    }

    @Test
    public void deleteByResearchIdIn() {
        ResearchEntity entity1 = ResearchEntity.builder()
            .researchId(ENTITY_ID_1)
            .gameId(GAME_ID_1)
            .build();

        ResearchEntity entity2 = ResearchEntity.builder()
            .researchId(ENTITY_ID_2)
            .gameId(GAME_ID_2)
            .build();
        underTest.saveAll(Arrays.asList(entity1, entity2));

        underTest.deleteByResearchIdIn(Arrays.asList(ENTITY_ID_1));

        assertThat(underTest.findAll()).containsExactly(entity2);
    }

    @Test
    public void getByGameId() {
        ResearchEntity entity1 = ResearchEntity.builder()
            .researchId(ENTITY_ID_1)
            .gameId(GAME_ID_1)
            .build();

        ResearchEntity entity2 = ResearchEntity.builder()
            .researchId(ENTITY_ID_2)
            .gameId(GAME_ID_2)
            .build();
        underTest.saveAll(Arrays.asList(entity1, entity2));

        List<ResearchEntity> result = underTest.getByGameId(GAME_ID_1);

        assertThat(result).containsExactly(entity1);
    }

    @Test
    public void getByStarIdAndPlayerId() {
        ResearchEntity entity1 = ResearchEntity.builder()
            .researchId(ENTITY_ID_1)
            .gameId(GAME_ID_1)
            .starId(STAR_ID_1)
            .playerId(PLAYER_ID_1)
            .build();

        ResearchEntity entity2 = ResearchEntity.builder()
            .researchId(ENTITY_ID_2)
            .gameId(GAME_ID_2)
            .starId(STAR_ID_1)
            .playerId(PLAYER_ID_2)
            .build();
        underTest.saveAll(Arrays.asList(entity1, entity2));

        List<ResearchEntity> result = underTest.getByStarIdAndPlayerId(STAR_ID_1, PLAYER_ID_1);

        assertThat(result).containsExactly(entity1);
    }
}