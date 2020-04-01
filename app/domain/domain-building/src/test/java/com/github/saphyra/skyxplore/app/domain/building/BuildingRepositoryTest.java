package com.github.saphyra.skyxplore.app.domain.building;

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
public class BuildingRepositoryTest {
    private static final String BUILDING_ID_1 = "building-id-1";
    private static final String BUILDING_ID_2 = "building-id-2";
    private static final String GAME_ID_1 = "game-id-1";
    private static final String GAME_ID_2 = "game-id-2";
    private static final String PLAYER_ID_1 = "player-id-1";
    private static final String PLAYER_ID_2 = "player-id-2";
    private static final String SURFACE_ID_1 = "surface-id-1";
    private static final String SURFACE_ID_2 = "surface-id-2";
    private static final String STAR_ID_1 = "star-id-1";
    private static final String STAR_ID_2 = "star-id-2";
    private static final String BUILDING_DATA_ID_1 = "building-data-id-1";
    private static final String BUILDING_DATA_ID_2 = "building-data-id-2";

    @Autowired
    private BuildingRepository underTest;

    @After
    public void clear() {
        underTest.deleteAll();
    }

    @Test
    public void deleteByBuildingIdIn() {
        BuildingEntity entity1 = BuildingEntity.builder().buildingId(BUILDING_ID_1).build();
        BuildingEntity entity2 = BuildingEntity.builder().buildingId(BUILDING_ID_2).build();
        underTest.saveAll(Arrays.asList(entity1, entity2));

        underTest.deleteByBuildingIdIn(Arrays.asList(BUILDING_ID_2));

        assertThat(underTest.findAll()).containsExactly(entity1);
    }

    @Test
    public void deleteByGameId() {
        BuildingEntity entity1 = BuildingEntity.builder().buildingId(BUILDING_ID_1).gameId(GAME_ID_1).build();
        BuildingEntity entity2 = BuildingEntity.builder().buildingId(BUILDING_ID_2).gameId(GAME_ID_2).build();
        underTest.saveAll(Arrays.asList(entity1, entity2));

        underTest.deleteByGameId(GAME_ID_2);

        assertThat(underTest.findAll()).containsExactly(entity1);
    }

    @Test
    public void findByBuildingIdAndPlayerId() {
        BuildingEntity entity1 = BuildingEntity.builder().buildingId(BUILDING_ID_1).playerId(PLAYER_ID_1).build();
        BuildingEntity entity2 = BuildingEntity.builder().buildingId(BUILDING_ID_2).playerId(PLAYER_ID_2).build();
        underTest.saveAll(Arrays.asList(entity1, entity2));

        Optional<BuildingEntity> result = underTest.findByBuildingIdAndPlayerId(BUILDING_ID_1, PLAYER_ID_1);

        assertThat(result).contains(entity1);
    }

    @Test
    public void findBySurfaceIdAndPlayerId() {
        BuildingEntity entity1 = BuildingEntity.builder().buildingId(BUILDING_ID_1).surfaceId(SURFACE_ID_1).playerId(PLAYER_ID_1).build();
        BuildingEntity entity2 = BuildingEntity.builder().buildingId(BUILDING_ID_2).surfaceId(SURFACE_ID_2).playerId(PLAYER_ID_2).build();
        underTest.saveAll(Arrays.asList(entity1, entity2));

        Optional<BuildingEntity> result = underTest.findBySurfaceIdAndPlayerId(SURFACE_ID_1, PLAYER_ID_1);

        assertThat(result).contains(entity1);
    }

    @Test
    public void getByGameId() {
        BuildingEntity entity1 = BuildingEntity.builder().buildingId(BUILDING_ID_1).gameId(GAME_ID_1).build();
        BuildingEntity entity2 = BuildingEntity.builder().buildingId(BUILDING_ID_2).gameId(GAME_ID_2).build();
        underTest.saveAll(Arrays.asList(entity1, entity2));

        List<BuildingEntity> result = underTest.getByGameId(GAME_ID_1);

        assertThat(result).containsExactly(entity1);
    }

    @Test
    public void getByStarIdAndBuildingDataIdAndPlayerId() {
        BuildingEntity entity1 = BuildingEntity.builder().buildingId(BUILDING_ID_1).playerId(PLAYER_ID_1).starId(STAR_ID_1).buildingDataId(BUILDING_DATA_ID_1).build();
        BuildingEntity entity2 = BuildingEntity.builder().buildingId(BUILDING_ID_2).playerId(PLAYER_ID_2).starId(STAR_ID_2).buildingDataId(BUILDING_DATA_ID_2).build();
        underTest.saveAll(Arrays.asList(entity1, entity2));

        List<BuildingEntity> result = underTest.getByStarIdAndBuildingDataIdAndPlayerId(STAR_ID_1, BUILDING_DATA_ID_1, PLAYER_ID_1);

        assertThat(result).containsExactly(entity1);
    }

    @Test
    public void getByStarIdAndPlayerId() {
        BuildingEntity entity1 = BuildingEntity.builder().buildingId(BUILDING_ID_1).playerId(PLAYER_ID_1).starId(STAR_ID_1).build();
        BuildingEntity entity2 = BuildingEntity.builder().buildingId(BUILDING_ID_2).playerId(PLAYER_ID_2).starId(STAR_ID_2).build();
        underTest.saveAll(Arrays.asList(entity1, entity2));

        List<BuildingEntity> result = underTest.getByStarIdAndPlayerId(STAR_ID_1, PLAYER_ID_1);

        assertThat(result).containsExactly(entity1);
    }
}