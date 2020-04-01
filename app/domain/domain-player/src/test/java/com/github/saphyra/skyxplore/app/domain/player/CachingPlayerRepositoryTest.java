package com.github.saphyra.skyxplore.app.domain.player;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import com.github.saphyra.util.OffsetDateTimeProvider;

@RunWith(MockitoJUnitRunner.class)
public class CachingPlayerRepositoryTest {
    private static final String GAME_ID = "game-id";
    private static final String PLAYER_ID = "player-id";

    @Mock
    private PlayerRepository repository;

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

    private CachingPlayerRepository underTest;

    @Mock
    private PlayerEntity playerEntity;

    @Before
    public void setUp() {
        given(cacheContext.getCacheComponentWrapper()).willReturn(cacheComponentWrapper);
        given(cacheContext.getOffsetDateTimeProvider()).willReturn(offsetDateTimeProvider);

        underTest = new CachingPlayerRepository(repository, cacheContext);

        given(cacheComponentWrapper.getDeleteByKey()).willReturn(deleteByKeyComponent);
        given(cacheComponentWrapper.getGetMapByKey()).willReturn(getMapByKeyComponent);
    }

    @Test
    public void getByKey() {
        given(repository.getByGameId(GAME_ID)).willReturn(Arrays.asList(playerEntity));

        List<PlayerEntity> result = underTest.getByKey(GAME_ID);

        assertThat(result).containsExactly(playerEntity);
    }

    @Test
    public void deleteByIds() {
        underTest.deleteByIds(Arrays.asList(PLAYER_ID));

        verify(repository).deleteByPlayerIdIn(Arrays.asList(PLAYER_ID));
    }

    @Test
    public void deleteByGameId() {
        underTest.deleteByGameId(GAME_ID);

        verify(deleteByKeyComponent).deleteByKey(underTest, GAME_ID);
    }

    @Test
    public void deleteByPlayerIdIn() {
        ModifiableEntity<PlayerEntity> modifiableEntity = new ModifiableEntity<>(playerEntity, false);

        EntityMapping<String, PlayerEntity> entityMapping = new EntityMapping<>();
        entityMapping.put(PLAYER_ID, modifiableEntity);
        ExpirableEntity<EntityMapping<String, PlayerEntity>> expirableEntity = new ExpirableEntity<>(entityMapping, cacheContext);
        underTest.getCacheMap().put(GAME_ID, expirableEntity);

        underTest.deleteByPlayerIdIn(Arrays.asList(PLAYER_ID));

        assertThat(underTest.getDeleteQueue()).containsExactly(PLAYER_ID);
        assertThat(underTest.getCacheMap().get(GAME_ID).getEntity()).isEmpty();
    }

    @Test
    public void getByGameId() {
        Map<String, PlayerEntity> map = new HashMap<>();
        map.put(PLAYER_ID, playerEntity);
        given(getMapByKeyComponent.getMapByKey(underTest, GAME_ID, cacheContext)).willReturn(map);

        List<PlayerEntity> result = underTest.getByGameId(GAME_ID);

        assertThat(result).containsExactly(playerEntity);
    }

    @Test
    public void findByGameIdAndPlayerId() {
        Map<String, PlayerEntity> map = new HashMap<>();
        map.put(PLAYER_ID, playerEntity);
        given(getMapByKeyComponent.getMapByKey(underTest, GAME_ID, cacheContext)).willReturn(map);

        Optional<PlayerEntity> result = underTest.findByGameIdAndPlayerId(GAME_ID, PLAYER_ID);

        assertThat(result).contains(playerEntity);
    }
}