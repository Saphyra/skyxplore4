package com.github.saphyra.skyxplore.app.domain.building;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BuildingCommandServiceTest {
    private static final UUID GAME_ID = UUID.randomUUID();

    @Mock
    private BuildingDao buildingDao;

    @InjectMocks
    private BuildingCommandService underTest;

    @Mock
    private Building building;

    @Test
    public void deleteByGameId() {
        underTest.deleteByGameId(GAME_ID);

        verify(buildingDao).deleteByGameId(GAME_ID);
    }

    @Test
    public void save() {
        underTest.save(building);

        verify(buildingDao).save(building);
    }

    @Test
    public void delete() {
        underTest.delete(building);

        verify(buildingDao).delete(building);
    }

    @Test
    public void deleteAll() {
        underTest.deleteAll(Arrays.asList(building));

        verify(buildingDao).deleteAll(Arrays.asList(building));
    }

    @Test
    public void saveAll() {
        underTest.saveAll(Arrays.asList(building));

        verify(buildingDao).saveAll(Arrays.asList(building));
    }

    @Test
    public void getType() {
        Class<Building> result = underTest.getType();

        assertThat(result).isEqualTo(Building.class);
    }
}