package com.github.saphyra.skyxplore.app.domain.surface;

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
public class SurfaceRepositoryTest {
    private static final String SURFACE_ID_1 = "surface-id-1";
    private static final String SURFACE_ID_2 = "surface-id-2";
    private static final String GAME_ID_1 = "game-id-1";
    private static final String GAME_ID_2 = "game-id-2";
    private static final String PLAYER_ID_1 = "player-id-1";
    private static final String STAR_ID_1 = "star-id-1";
    private static final String STAR_ID_2 = "star-id-2";

    @Autowired
    private SurfaceRepository underTest;

    @After
    public void clear() {
        underTest.deleteAll();
    }

    @Test
    public void deleteBySurfaceIdIn() {
        SurfaceEntity entity1 = SurfaceEntity.builder()
            .surfaceId(SURFACE_ID_1)
            .build();
        SurfaceEntity entity2 = SurfaceEntity.builder()
            .surfaceId(SURFACE_ID_2)
            .build();
        underTest.saveAll(Arrays.asList(entity1, entity2));

        underTest.deleteBySurfaceIdIn(Arrays.asList(SURFACE_ID_2));

        assertThat(underTest.findAll()).containsExactly(entity1);
    }

    @Test
    public void deleteByGameId() {
        SurfaceEntity entity1 = SurfaceEntity.builder()
            .surfaceId(SURFACE_ID_1)
            .gameId(GAME_ID_1)
            .build();
        SurfaceEntity entity2 = SurfaceEntity.builder()
            .surfaceId(SURFACE_ID_2)
            .gameId(GAME_ID_2)
            .build();
        underTest.saveAll(Arrays.asList(entity1, entity2));

        underTest.deleteByGameId(GAME_ID_2);

        assertThat(underTest.findAll()).containsExactly(entity1);
    }

    @Test
    public void findBySurfaceIdAndPlayerId() {
        SurfaceEntity entity1 = SurfaceEntity.builder()
            .surfaceId(SURFACE_ID_1)
            .playerId(PLAYER_ID_1)
            .build();
        SurfaceEntity entity2 = SurfaceEntity.builder()
            .surfaceId(SURFACE_ID_2)
            .playerId(PLAYER_ID_1)
            .build();
        underTest.saveAll(Arrays.asList(entity1, entity2));

        Optional<SurfaceEntity> result = underTest.findBySurfaceIdAndPlayerId(SURFACE_ID_1, PLAYER_ID_1);

        assertThat(result).contains(entity1);
    }

    @Test
    public void getByStarIdAndPlayerId() {
        SurfaceEntity entity1 = SurfaceEntity.builder()
            .surfaceId(SURFACE_ID_1)
            .playerId(PLAYER_ID_1)
            .starId(STAR_ID_1)
            .build();
        SurfaceEntity entity2 = SurfaceEntity.builder()
            .surfaceId(SURFACE_ID_2)
            .playerId(PLAYER_ID_1)
            .starId(STAR_ID_2)
            .build();
        underTest.saveAll(Arrays.asList(entity1, entity2));

        List<SurfaceEntity> result = underTest.getByStarIdAndPlayerId(STAR_ID_1, PLAYER_ID_1);

        assertThat(result).contains(entity1);
    }
}