package com.github.saphyra.skyxplore.app.domain.building;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.app.common.dao.cache_repository.CacheContext;
import com.github.saphyra.skyxplore.app.common.dao.cache_repository.EntityMapping;
import com.github.saphyra.skyxplore.app.common.dao.cache_repository.ExpirableEntity;
import com.github.saphyra.skyxplore.app.common.dao.cache_repository.ModifiableEntity;
import com.github.saphyra.skyxplore.app.common.dao.cache_repository.component.CacheComponentWrapper;
import com.github.saphyra.skyxplore.app.common.dao.cache_repository.component.DeleteByKeyComponent;
import com.github.saphyra.skyxplore.app.common.dao.cache_repository.component.GetMapByKeyComponent;
import com.github.saphyra.skyxplore.app.common.request_context.RequestContext;
import com.github.saphyra.skyxplore.app.common.request_context.RequestContextHolder;
import com.github.saphyra.skyxplore.app.common.utils.UuidConverter;
import com.github.saphyra.util.OffsetDateTimeProvider;

@RunWith(MockitoJUnitRunner.class)
public class CachingBuildingRepositoryTest {
    private static final String GAME_ID_STRING = "game-id";
    private static final String BUILDING_ID = "building-id";
    private static final UUID GAME_ID = UUID.randomUUID();
    private static final String PLAYER_ID = "player-id";
    private static final String SURFACE_ID = "surface-id";
    private static final String STAR_ID = "star-id";
    private static final String DATA_ID = "data-id";

    @Mock
    private BuildingRepository repository;

    @Mock
    private CacheContext cacheContext;

    @Mock
    private CacheComponentWrapper cacheComponentWrapper;

    @Mock
    private DeleteByKeyComponent deleteByKeyComponent;

    @Mock
    private OffsetDateTimeProvider offsetDateTimeProvider;

    @Mock
    private GetMapByKeyComponent getMapByKeyComponent;

    @Mock
    private RequestContextHolder requestContextHolder;

    @Mock
    private UuidConverter uuidConverter;

    private CachingBuildingRepository underTest;

    @Mock
    private RequestContext requestContext;

    @Mock
    private BuildingEntity entity;

    @Before
    public void setUp() {
        given(cacheContext.getCacheComponentWrapper()).willReturn(cacheComponentWrapper);
        given(cacheContext.getOffsetDateTimeProvider()).willReturn(offsetDateTimeProvider);

        underTest = new CachingBuildingRepository(repository, requestContextHolder, uuidConverter, cacheContext);

        given(cacheComponentWrapper.getDeleteByKey()).willReturn(deleteByKeyComponent);
        given(cacheComponentWrapper.getGetMapByKey()).willReturn(getMapByKeyComponent);

        given(requestContextHolder.get()).willReturn(requestContext);
        given(requestContext.getGameId()).willReturn(GAME_ID);

        given(uuidConverter.convertDomain(GAME_ID)).willReturn(GAME_ID_STRING);
    }

    @Test
    public void getByKey() {
        given(repository.getByGameId(GAME_ID_STRING)).willReturn(Arrays.asList(entity));

        List<BuildingEntity> result = underTest.getByKey(GAME_ID_STRING);

        assertThat(result).containsExactly(entity);
    }

    @Test
    public void deleteByIds() {
        underTest.deleteByIds(Arrays.asList(BUILDING_ID));

        verify(repository).deleteByBuildingIdIn(Arrays.asList(BUILDING_ID));
    }

    @Test
    public void deleteByGameId() {
        underTest.deleteByGameId(GAME_ID_STRING);

        verify(deleteByKeyComponent).deleteByKey(underTest, GAME_ID_STRING);
    }

    @Test
    public void deleteByBuildingIdIn() {
        ModifiableEntity<BuildingEntity> modifiableEntity = new ModifiableEntity<>(entity, false);

        EntityMapping<String, BuildingEntity> entityMapping = new EntityMapping<>();
        entityMapping.put(BUILDING_ID, modifiableEntity);
        ExpirableEntity<EntityMapping<String, BuildingEntity>> expirableEntity = new ExpirableEntity<>(entityMapping, cacheContext);
        underTest.getCacheMap().put(GAME_ID_STRING, expirableEntity);

        underTest.deleteByBuildingIdIn(Arrays.asList(BUILDING_ID));

        assertThat(underTest.getDeleteQueue()).containsExactly(BUILDING_ID);
        assertThat(underTest.getCacheMap().get(GAME_ID_STRING).getEntity()).isEmpty();
    }

    @Test
    public void getByGameId() {
        Map<String, BuildingEntity> map = new HashMap<>();
        map.put(BUILDING_ID, entity);
        given(getMapByKeyComponent.getMapByKey(underTest, GAME_ID_STRING, cacheContext)).willReturn(map);

        List<BuildingEntity> result = underTest.getByGameId(GAME_ID_STRING);

        assertThat(result).containsExactly(entity);
    }

    @Test
    public void findByBuildingIdAndPlayerId() {
        Map<String, BuildingEntity> map = new HashMap<>();
        map.put(BUILDING_ID, entity);
        given(getMapByKeyComponent.getMapByKey(underTest, GAME_ID_STRING, cacheContext)).willReturn(map);

        given(entity.getBuildingId()).willReturn(BUILDING_ID);
        given(entity.getPlayerId()).willReturn(PLAYER_ID);

        Optional<BuildingEntity> result = underTest.findByBuildingIdAndPlayerId(BUILDING_ID, PLAYER_ID);

        assertThat(result).contains(entity);
    }

    @Test
    public void findBySurfaceIdAndPlayerId() {
        Map<String, BuildingEntity> map = new HashMap<>();
        map.put(BUILDING_ID, entity);
        given(getMapByKeyComponent.getMapByKey(underTest, GAME_ID_STRING, cacheContext)).willReturn(map);

        given(entity.getSurfaceId()).willReturn(SURFACE_ID);
        given(entity.getPlayerId()).willReturn(PLAYER_ID);

        Optional<BuildingEntity> result = underTest.findBySurfaceIdAndPlayerId(SURFACE_ID, PLAYER_ID);

        assertThat(result).contains(entity);
    }

    @Test
    public void getByStarIdAndBuildingDataIdAndPlayerId() {
        Map<String, BuildingEntity> map = new HashMap<>();
        map.put(BUILDING_ID, entity);
        given(getMapByKeyComponent.getMapByKey(underTest, GAME_ID_STRING, cacheContext)).willReturn(map);

        given(entity.getStarId()).willReturn(STAR_ID);
        given(entity.getBuildingDataId()).willReturn(DATA_ID);
        given(entity.getPlayerId()).willReturn(PLAYER_ID);

        List<BuildingEntity> result = underTest.getByStarIdAndBuildingDataIdAndPlayerId(STAR_ID, DATA_ID, PLAYER_ID);

        assertThat(result).containsExactly(entity);
    }

    @Test
    public void getByStarIdAndPlayerId() {
        Map<String, BuildingEntity> map = new HashMap<>();
        map.put(BUILDING_ID, entity);
        given(getMapByKeyComponent.getMapByKey(underTest, GAME_ID_STRING, cacheContext)).willReturn(map);

        given(entity.getStarId()).willReturn(STAR_ID);
        given(entity.getPlayerId()).willReturn(PLAYER_ID);

        List<BuildingEntity> result = underTest.getByStarIdAndPlayerId(STAR_ID, PLAYER_ID);

        assertThat(result).containsExactly(entity);
    }
}