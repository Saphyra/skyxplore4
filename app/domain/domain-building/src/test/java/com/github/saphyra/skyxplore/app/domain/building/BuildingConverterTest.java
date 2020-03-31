package com.github.saphyra.skyxplore.app.domain.building;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.app.common.utils.UuidConverter;

@RunWith(MockitoJUnitRunner.class)
public class BuildingConverterTest {
    private static final String BUILDING_ID_STRING = "building-id";
    private static final String DATA_ID = "data-id";
    private static final String GAME_ID_STRING = "game-id";
    private static final String STAR_ID_STRING = "star-id";
    private static final String PLAYER_ID_STRING = "player-id";
    private static final String SURFACE_ID_STRING = "surface-id";
    private static final Integer LEVEL = 6546;
    private static final String CONSTRUCTION_ID_STRING = "construction-id";
    private static final UUID BUILDING_ID = UUID.randomUUID();
    private static final UUID GAME_ID = UUID.randomUUID();
    private static final UUID STAR_ID = UUID.randomUUID();
    private static final UUID PLAYER_ID = UUID.randomUUID();
    private static final UUID SURFACE_ID = UUID.randomUUID();
    private static final UUID CONSTRUCTION_ID = UUID.randomUUID();

    @Mock
    private UuidConverter uuidConverter;

    @InjectMocks
    private BuildingConverter underTest;

    @Test
    public void convertEntity() {
        BuildingEntity entity = BuildingEntity.builder()
            .buildingId(BUILDING_ID_STRING)
            .buildingDataId(DATA_ID)
            .gameId(GAME_ID_STRING)
            .starId(STAR_ID_STRING)
            .playerId(PLAYER_ID_STRING)
            .surfaceId(SURFACE_ID_STRING)
            .level(LEVEL)
            .constructionId(CONSTRUCTION_ID_STRING)
            .isNew(true)
            .build();

        given(uuidConverter.convertEntity(BUILDING_ID_STRING)).willReturn(BUILDING_ID);
        given(uuidConverter.convertEntity(GAME_ID_STRING)).willReturn(GAME_ID);
        given(uuidConverter.convertEntity(STAR_ID_STRING)).willReturn(STAR_ID);
        given(uuidConverter.convertEntity(PLAYER_ID_STRING)).willReturn(PLAYER_ID);
        given(uuidConverter.convertEntity(SURFACE_ID_STRING)).willReturn(SURFACE_ID);
        given(uuidConverter.convertEntity(CONSTRUCTION_ID_STRING)).willReturn(CONSTRUCTION_ID);

        Building result = underTest.convertEntity(entity);

        assertThat(result.getBuildingId()).isEqualTo(BUILDING_ID);
        assertThat(result.getBuildingDataId()).isEqualTo(DATA_ID);
        assertThat(result.getGameId()).isEqualTo(GAME_ID);
        assertThat(result.getStarId()).isEqualTo(STAR_ID);
        assertThat(result.getPlayerId()).isEqualTo(PLAYER_ID);
        assertThat(result.getSurfaceId()).isEqualTo(SURFACE_ID);
        assertThat(result.getLevel()).isEqualTo(LEVEL);
        assertThat(result.getConstructionId()).isEqualTo(CONSTRUCTION_ID);
        assertThat(result.isNew()).isFalse();
    }

    @Test
    public void convertDomain() {
        Building building = Building.builder()
            .buildingId(BUILDING_ID)
            .buildingDataId(DATA_ID)
            .gameId(GAME_ID)
            .starId(STAR_ID)
            .playerId(PLAYER_ID)
            .surfaceId(SURFACE_ID)
            .level(LEVEL)
            .constructionId(CONSTRUCTION_ID)
            .isNew(true)
            .build();

        given(uuidConverter.convertDomain(BUILDING_ID)).willReturn(BUILDING_ID_STRING);
        given(uuidConverter.convertDomain(GAME_ID)).willReturn(GAME_ID_STRING);
        given(uuidConverter.convertDomain(STAR_ID)).willReturn(STAR_ID_STRING);
        given(uuidConverter.convertDomain(PLAYER_ID)).willReturn(PLAYER_ID_STRING);
        given(uuidConverter.convertDomain(SURFACE_ID)).willReturn(SURFACE_ID_STRING);
        given(uuidConverter.convertDomain(CONSTRUCTION_ID)).willReturn(CONSTRUCTION_ID_STRING);

        BuildingEntity result = underTest.convertDomain(building);

        assertThat(result.getBuildingId()).isEqualTo(BUILDING_ID_STRING);
        assertThat(result.getBuildingDataId()).isEqualTo(DATA_ID);
        assertThat(result.getGameId()).isEqualTo(GAME_ID_STRING);
        assertThat(result.getStarId()).isEqualTo(STAR_ID_STRING);
        assertThat(result.getPlayerId()).isEqualTo(PLAYER_ID_STRING);
        assertThat(result.getSurfaceId()).isEqualTo(SURFACE_ID_STRING);
        assertThat(result.getLevel()).isEqualTo(LEVEL);
        assertThat(result.getConstructionId()).isEqualTo(CONSTRUCTION_ID_STRING);
        assertThat(result.isNew()).isTrue();
    }
}