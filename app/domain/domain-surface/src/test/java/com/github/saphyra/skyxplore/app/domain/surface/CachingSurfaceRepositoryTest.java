package com.github.saphyra.skyxplore.app.domain.surface;

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
public class CachingSurfaceRepositoryTest {
    private static final UUID GAME_ID = UUID.randomUUID();
    private static final String GAME_ID_STRING = "game-id";
    private static final String SURFACE_ID_1 = "surface-id-1";
    private static final String SURFACE_ID_2 = "surface-id-2";
    private static final String SURFACE_ID_3 = "surface-id-3";
    private static final String PLAYER_ID_1 = "player-id-1";
    private static final String PLAYER_ID_2 = "player-id-2";
    private static final String STAR_ID_1 = "star-id-1";
    private static final String STAR_ID_2 = "star-id-2";

    @Mock
    private SurfaceRepository repository;

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
    private UuidConverter uuidConverter;

    @Mock
    private RequestContextHolder requestContextHolder;

    private CachingSurfaceRepository underTest;

    @Mock
    private SurfaceEntity surfaceEntity;

    @Mock
    private RequestContext requestContext;

    @Before
    public void setUp() {
        given(cacheContext.getCacheComponentWrapper()).willReturn(cacheComponentWrapper);
        given(cacheContext.getOffsetDateTimeProvider()).willReturn(offsetDateTimeProvider);

        underTest = new CachingSurfaceRepository(repository, requestContextHolder, uuidConverter, cacheContext);

        given(cacheComponentWrapper.getDeleteByKey()).willReturn(deleteByKeyComponent);
        given(cacheComponentWrapper.getGetMapByKey()).willReturn(getMapByKeyComponent);

        given(requestContextHolder.get()).willReturn(requestContext);
        given(requestContext.getGameId()).willReturn(GAME_ID);
        given(uuidConverter.convertDomain(GAME_ID)).willReturn(GAME_ID_STRING);
    }

    @Test
    public void getByKey() {
        given(repository.getByGameId(GAME_ID_STRING)).willReturn(Arrays.asList(surfaceEntity));

        List<SurfaceEntity> result = underTest.getByKey(GAME_ID_STRING);

        assertThat(result).containsExactly(surfaceEntity);
    }

    @Test
    public void deleteByIds() {
        underTest.deleteByIds(Arrays.asList(SURFACE_ID_1));

        verify(repository).deleteBySurfaceIdIn(Arrays.asList(SURFACE_ID_1));
    }

    @Test
    public void deleteByGameId() {
        underTest.deleteByGameId(GAME_ID_STRING);

        verify(deleteByKeyComponent).deleteByKey(underTest, GAME_ID_STRING);
    }

    @Test
    public void deleteByPlayerIdIn() {
        ModifiableEntity<SurfaceEntity> modifiableEntity = new ModifiableEntity<>(surfaceEntity, false);

        EntityMapping<String, SurfaceEntity> entityMapping = new EntityMapping<>();
        entityMapping.put(SURFACE_ID_1, modifiableEntity);
        ExpirableEntity<EntityMapping<String, SurfaceEntity>> expirableEntity = new ExpirableEntity<>(entityMapping, cacheContext);
        underTest.getCacheMap().put(GAME_ID_STRING, expirableEntity);

        underTest.deleteBySurfaceIdIn(Arrays.asList(SURFACE_ID_1));

        assertThat(underTest.getDeleteQueue()).containsExactly(SURFACE_ID_1);
        assertThat(underTest.getCacheMap().get(GAME_ID_STRING).getEntity()).isEmpty();
    }

    @Test
    public void getByGameId() {
        Map<String, SurfaceEntity> map = new HashMap<>();
        map.put(SURFACE_ID_1, surfaceEntity);
        given(getMapByKeyComponent.getMapByKey(underTest, GAME_ID_STRING, cacheContext)).willReturn(map);

        List<SurfaceEntity> result = underTest.getByGameId(GAME_ID_STRING);

        assertThat(result).containsExactly(surfaceEntity);
    }

    @Test
    public void findBySurfaceIdAndPlayerId() {
        Map<String, SurfaceEntity> map = new HashMap<>();
        SurfaceEntity entity1 = SurfaceEntity.builder()
            .surfaceId(SURFACE_ID_1)
            .playerId(PLAYER_ID_1)
            .build();
        map.put(SURFACE_ID_1, entity1);
        map.put(SURFACE_ID_2, SurfaceEntity.builder().surfaceId(SURFACE_ID_2).build());
        given(getMapByKeyComponent.getMapByKey(underTest, GAME_ID_STRING, cacheContext)).willReturn(map);

        Optional<SurfaceEntity> result = underTest.findBySurfaceIdAndPlayerId(SURFACE_ID_1, PLAYER_ID_1);

        assertThat(result).contains(entity1);
    }

    @Test
    public void getByStarIdAndPlayerId() {
        Map<String, SurfaceEntity> map = new HashMap<>();
        SurfaceEntity entity1 = SurfaceEntity.builder()
            .starId(STAR_ID_1)
            .playerId(PLAYER_ID_1)
            .build();

        SurfaceEntity entity2 = SurfaceEntity.builder()
            .starId(STAR_ID_1)
            .playerId(PLAYER_ID_2)
            .build();

        SurfaceEntity entity3 = SurfaceEntity.builder()
            .starId(STAR_ID_2)
            .playerId(PLAYER_ID_1)
            .build();
        map.put(SURFACE_ID_1, entity1);
        map.put(SURFACE_ID_2, entity2);
        map.put(SURFACE_ID_3, entity3);
        given(getMapByKeyComponent.getMapByKey(underTest, GAME_ID_STRING, cacheContext)).willReturn(map);

        List<SurfaceEntity> result = underTest.getByStarIdAndPlayerId(STAR_ID_1, PLAYER_ID_1);

        assertThat(result).containsExactly(entity1);
    }
}