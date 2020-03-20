package com.github.saphyra.skyxplore.app.domain.star_connection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class CachingStarConnectionRepositoryTest {
    private static final String GAME_ID = "game-id";
    private static final String STAR_CONNECTION_ID = "star-connection-id";

    @Mock
    private StarConnectionRepository starConnectionRepository;

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

    private CachingStarConnectionRepository underTest;

    @Mock
    private StarConnectionEntity starConnectionEntity;

    @Before
    public void setUp() {
        given(cacheContext.getCacheComponentWrapper()).willReturn(cacheComponentWrapper);
        given(cacheContext.getOffsetDateTimeProvider()).willReturn(offsetDateTimeProvider);

        underTest = new CachingStarConnectionRepository(starConnectionRepository, cacheContext);

        given(cacheComponentWrapper.getDeleteByKey()).willReturn(deleteByKeyComponent);
        given(cacheComponentWrapper.getGetMapByKey()).willReturn(getMapByKeyComponent);
    }

    @Test
    public void getByKey() {
        given(starConnectionRepository.getByGameId(GAME_ID)).willReturn(Arrays.asList(starConnectionEntity));

        List<StarConnectionEntity> result = underTest.getByKey(GAME_ID);

        assertThat(result).containsExactly(starConnectionEntity);
    }

    @Test
    public void deleteByIds() {
        underTest.deleteByIds(Arrays.asList(STAR_CONNECTION_ID));

        verify(starConnectionRepository).deleteByConnectionIdIn(Arrays.asList(STAR_CONNECTION_ID));
    }

    @Test
    public void deleteByGameId() {
        underTest.deleteByGameId(GAME_ID);

        verify(deleteByKeyComponent).deleteByKey(underTest, GAME_ID);
    }

    @Test
    public void deleteByConnectionIdIn() {
        ModifiableEntity<StarConnectionEntity> modifiableEntity = new ModifiableEntity<>(starConnectionEntity, false);

        EntityMapping<String, StarConnectionEntity> entityMapping = new EntityMapping<>();
        entityMapping.put(STAR_CONNECTION_ID, modifiableEntity);
        ExpirableEntity<EntityMapping<String, StarConnectionEntity>> expirableEntity = new ExpirableEntity<>(entityMapping, cacheContext);
        underTest.getCacheMap().put(GAME_ID, expirableEntity);

        underTest.deleteByConnectionIdIn(Arrays.asList(STAR_CONNECTION_ID));

        assertThat(underTest.getDeleteQueue()).containsExactly(STAR_CONNECTION_ID);
        assertThat(underTest.getCacheMap().get(GAME_ID).getEntity()).isEmpty();
    }

    @Test
    public void getByGameId() {
        Map<String, StarConnectionEntity> map = new HashMap<>();
        map.put(STAR_CONNECTION_ID, starConnectionEntity);
        given(getMapByKeyComponent.getMapByKey(underTest, GAME_ID, cacheContext)).willReturn(map);

        List<StarConnectionEntity> result = underTest.getByGameId(GAME_ID);

        assertThat(result).containsExactly(starConnectionEntity);
    }
}