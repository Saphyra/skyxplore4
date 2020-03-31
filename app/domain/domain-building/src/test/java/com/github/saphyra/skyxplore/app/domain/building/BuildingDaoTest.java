package com.github.saphyra.skyxplore.app.domain.building;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.app.common.utils.UuidConverter;

@RunWith(MockitoJUnitRunner.class)
public class BuildingDaoTest {
    private static final UUID GAME_ID = UUID.randomUUID();
    private static final String GAME_ID_STRING = "game-id";
    private static final UUID BUILDING_ID = UUID.randomUUID();
    private static final String BUILDING_ID_STRING = "building-id";
    private static final UUID PLAYER_ID = UUID.randomUUID();
    private static final String PLAYER_ID_STRING = "player-id";
    private static final UUID SURFACE_ID = UUID.randomUUID();
    private static final String SURFACE_ID_STRING = "surface-id";
    private static final UUID STAR_ID = UUID.randomUUID();
    private static final String STAR_ID_STRING = "star-id";
    private static final String DATA_ID = "data-id";

    @Mock
    private UuidConverter uuidConverter;

    @Mock
    private BuildingConverter converter;

    @Mock
    private BuildingRepository repository;

    @InjectMocks
    private BuildingDao underTest;

    @Mock
    private Building building;

    @Mock
    private BuildingEntity buildingEntity;

    @Test
    public void deleteByGameId() {
        given(uuidConverter.convertDomain(GAME_ID)).willReturn(GAME_ID_STRING);

        underTest.deleteByGameId(GAME_ID);

        verify(repository).deleteByGameId(GAME_ID_STRING);
    }

    @Test
    public void findByBuildingIdAndPlayerId() {
        given(uuidConverter.convertDomain(BUILDING_ID)).willReturn(BUILDING_ID_STRING);
        given(uuidConverter.convertDomain(PLAYER_ID)).willReturn(PLAYER_ID_STRING);

        given(repository.findByBuildingIdAndPlayerId(BUILDING_ID_STRING, PLAYER_ID_STRING)).willReturn(Optional.of(buildingEntity));
        given(converter.convertEntity(Optional.of(buildingEntity))).willReturn(Optional.of(building));

        Optional<Building> result = underTest.findByBuildingIdAndPlayerId(BUILDING_ID, PLAYER_ID);

        assertThat(result).contains(building);
    }

    @Test
    public void findBySurfaceIdAndPlayerId() {
        given(uuidConverter.convertDomain(SURFACE_ID)).willReturn(SURFACE_ID_STRING);
        given(uuidConverter.convertDomain(PLAYER_ID)).willReturn(PLAYER_ID_STRING);

        given(repository.findBySurfaceIdAndPlayerId(SURFACE_ID_STRING, PLAYER_ID_STRING)).willReturn(Optional.of(buildingEntity));
        given(converter.convertEntity(Optional.of(buildingEntity))).willReturn(Optional.of(building));

        Optional<Building> result = underTest.findBySurfaceIdAndPlayerId(SURFACE_ID, PLAYER_ID);

        assertThat(result).contains(building);
    }

    @Test
    public void getByStarIdAndDataIdAndPlayerId() {
        given(uuidConverter.convertDomain(STAR_ID)).willReturn(STAR_ID_STRING);
        given(uuidConverter.convertDomain(PLAYER_ID)).willReturn(PLAYER_ID_STRING);

        given(repository.getByStarIdAndBuildingDataIdAndPlayerId(STAR_ID_STRING, DATA_ID, PLAYER_ID_STRING)).willReturn(Arrays.asList(buildingEntity));
        given(converter.convertEntity(Arrays.asList(buildingEntity))).willReturn(Arrays.asList(building));

        List<Building> result = underTest.getByStarIdAndDataIdAndPlayerId(STAR_ID, DATA_ID, PLAYER_ID);

        assertThat(result).containsExactly(building);
    }

    @Test
    public void getByStarIdAndPlayerId() {
        given(uuidConverter.convertDomain(STAR_ID)).willReturn(STAR_ID_STRING);
        given(uuidConverter.convertDomain(PLAYER_ID)).willReturn(PLAYER_ID_STRING);

        given(repository.getByStarIdAndPlayerId(STAR_ID_STRING, PLAYER_ID_STRING)).willReturn(Arrays.asList(buildingEntity));
        given(converter.convertEntity(Arrays.asList(buildingEntity))).willReturn(Arrays.asList(building));

        List<Building> result = underTest.getByStarIdAndPlayerId(STAR_ID, PLAYER_ID);

        assertThat(result).containsExactly(building);
    }
}