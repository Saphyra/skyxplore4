package com.github.saphyra.skyxplore.app.domain.research;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
public class CachingResearchRepositoryTest {
    private static final String GAME_ID_STRING = "game-id";
    private static final String ENTITY_ID_1 = "entity-id-1";
    private static final String ENTITY_ID_2 = "entity-id-2";
    private static final String ENTITY_ID_3 = "entity-id-3";
    private static final UUID GAME_ID = UUID.randomUUID();
    private static final String STAR_ID_1 = "star-id-1";
    private static final String STAR_ID_2 = "star-id-2";
    private static final String PLAYER_ID_1 = "player-id-1";
    private static final String PLAYER_ID_2 = "player-id-2";

    @Mock
    private CacheContext cacheContext;

    @Mock
    private CacheComponentWrapper cacheComponentWrapper;

    @Mock
    private OffsetDateTimeProvider offsetDateTimeProvider;

    @Mock
    private ResearchRepository researchRepository;

    @Mock
    private RequestContextHolder requestContextHolder;

    @Mock
    private UuidConverter uuidConverter;

    @Mock
    private DeleteByKeyComponent deleteByKeyComponent;

    @Mock
    private GetMapByKeyComponent getMapByKeyComponent;

    private CachingResearchRepository underTest;

    @Mock
    private ResearchEntity researchEntity;

    @Mock
    private RequestContext requestContext;

    @Before
    public void setUp() {
        given(cacheContext.getCacheComponentWrapper()).willReturn(cacheComponentWrapper);
        given(cacheContext.getOffsetDateTimeProvider()).willReturn(offsetDateTimeProvider);

        underTest = CachingResearchRepository.builder()
            .repository(researchRepository)
            .uuidConverter(uuidConverter)
            .requestContextHolder(requestContextHolder)
            .cacheContext(cacheContext)
            .build();

        given(cacheComponentWrapper.getDeleteByKey()).willReturn(deleteByKeyComponent);
        given(cacheComponentWrapper.getGetMapByKey()).willReturn(getMapByKeyComponent);
    }

    @Test
    public void getByKey() {
        given(researchRepository.getByGameId(GAME_ID_STRING)).willReturn(Arrays.asList(researchEntity));

        List<ResearchEntity> result = underTest.getByKey(GAME_ID_STRING);

        assertThat(result).containsExactly(researchEntity);
    }

    @Test
    public void deleteByIds() {
        underTest.deleteByIds(Arrays.asList(ENTITY_ID_1));

        verify(researchRepository).deleteByResearchIdIn(Arrays.asList(ENTITY_ID_1));
    }

    @Test
    public void deleteByGameId() {
        underTest.deleteByGameId(GAME_ID_STRING);

        verify(deleteByKeyComponent).deleteByKey(underTest, GAME_ID_STRING);
    }

    @Test
    public void deleteByResearchIdIn() {
        ModifiableEntity<ResearchEntity> modifiableEntity = new ModifiableEntity<>(researchEntity, false);

        EntityMapping<String, ResearchEntity> entityMapping = new EntityMapping<>();
        entityMapping.put(ENTITY_ID_1, modifiableEntity);
        ExpirableEntity<EntityMapping<String, ResearchEntity>> expirableEntity = new ExpirableEntity<>(entityMapping, cacheContext);
        underTest.getCacheMap().put(GAME_ID_STRING, expirableEntity);

        underTest.deleteByResearchIdIn(Arrays.asList(ENTITY_ID_1));

        assertThat(underTest.getDeleteQueue()).containsExactly(ENTITY_ID_1);
        assertThat(underTest.getCacheMap().get(GAME_ID_STRING).getEntity()).isEmpty();
    }

    @Test
    public void getByGameId() {
        Map<String, ResearchEntity> map = new HashMap<>();
        map.put(ENTITY_ID_1, researchEntity);
        given(getMapByKeyComponent.getMapByKey(underTest, GAME_ID_STRING, cacheContext)).willReturn(map);

        List<ResearchEntity> result = underTest.getByGameId(GAME_ID_STRING);

        assertThat(result).containsExactly(researchEntity);
    }

    @Test
    public void getByStarIdAndPlayerId() {
        given(requestContextHolder.get()).willReturn(requestContext);
        given(requestContext.getGameId()).willReturn(GAME_ID);
        given(uuidConverter.convertDomain(GAME_ID)).willReturn(GAME_ID_STRING);

        ResearchEntity entity1 = ResearchEntity.builder()
            .researchId(ENTITY_ID_1)
            .starId(STAR_ID_1)
            .playerId(PLAYER_ID_1)
            .build();

        ResearchEntity entity2 = ResearchEntity.builder()
            .researchId(ENTITY_ID_2)
            .starId(STAR_ID_2)
            .playerId(PLAYER_ID_1)
            .build();

        ResearchEntity entity3 = ResearchEntity.builder()
            .researchId(ENTITY_ID_3)
            .starId(STAR_ID_1)
            .playerId(PLAYER_ID_2)
            .build();
        Map<String, ResearchEntity> entityMap = Stream.of(entity1, entity2, entity3)
            .collect(Collectors.toMap(ResearchEntity::getResearchId, Function.identity()));
        given(getMapByKeyComponent.getMapByKey(underTest, GAME_ID_STRING, cacheContext)).willReturn(entityMap);

        List<ResearchEntity> result = underTest.getByStarIdAndPlayerId(STAR_ID_1, PLAYER_ID_1);

        assertThat(result).containsExactly(entity1);
    }
}