package com.github.saphyra.skyxplore.app.common.dao.cache_repository.component;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.app.common.dao.cache_repository.CacheContext;
import com.github.saphyra.skyxplore.app.common.dao.cache_repository.CacheMap;
import com.github.saphyra.skyxplore.app.common.dao.cache_repository.EntityMapping;
import com.github.saphyra.skyxplore.app.common.dao.cache_repository.ExpirableEntity;

@RunWith(MockitoJUnitRunner.class)
public class FindByIdComponentTest {
    private static final UUID ENTITY_ID_1 = UUID.randomUUID();
    private static final UUID ENTITY_ID_2 = UUID.randomUUID();
    private static final UUID KEY = UUID.randomUUID();

    @Mock
    private ExtractEntitiesComponent extractEntitiesComponent;

    @Mock
    private SearchInRepositoryComponent searchInRepositoryComponent;

    @Mock
    private CacheRepositoryStub cacheRepository;

    @Mock
    private CacheContext cacheContext;

    @InjectMocks
    private FindByIdComponent underTest;

    @Mock
    private CacheMap<UUID, UUID, EntityStub> cacheMap;

    @Mock
    private ExpirableEntity<EntityMapping<UUID, EntityStub>> expirableEntity;

    @Mock
    private EntityMapping<UUID, EntityStub> entityMapping;

    @Test
    public void entityDeleted() {
        given(cacheRepository.getDeleteQueue()).willReturn(new HashSet<>(Arrays.asList(ENTITY_ID_1)));

        Optional<EntityStub> result = underTest.findById(cacheRepository, EntityStub::getKey, ENTITY_ID_1, cacheContext);

        assertThat(result).isEmpty();
    }

    @Test
    public void cachedEntity() {
        given(cacheRepository.getCacheMap()).willReturn(cacheMap);
        given(cacheMap.values()).willReturn(Arrays.asList(expirableEntity));
        given(expirableEntity.getEntity()).willReturn(entityMapping);

        EntityStub entity1 = EntityStub.builder().id(ENTITY_ID_1).key(KEY).build();
        EntityStub entity2 = EntityStub.builder().id(ENTITY_ID_2).build();
        Map<UUID, EntityStub> map = new HashMap<>();
        map.put(ENTITY_ID_1, entity1);
        map.put(ENTITY_ID_2, entity2);
        given(extractEntitiesComponent.extractEntities(entityMapping)).willReturn(map);

        given(cacheMap.get(KEY)).willReturn(expirableEntity);

        Optional<EntityStub> result = underTest.findById(cacheRepository, EntityStub::getKey, ENTITY_ID_1, cacheContext);

        verify(expirableEntity).updateLastAccess();
        assertThat(result).contains(entity1);
    }

    @Test
    public void searchInRepository() {
        given(cacheRepository.getCacheMap()).willReturn(cacheMap);
        given(cacheMap.values()).willReturn(Collections.emptyList());

        EntityStub entity1 = EntityStub.builder().id(ENTITY_ID_1).key(KEY).build();
        Function<EntityStub, UUID> keyMapper = EntityStub::getKey;
        given(searchInRepositoryComponent.searchInRepository(cacheRepository, keyMapper, ENTITY_ID_1, cacheContext)).willReturn(Optional.of(entity1));

        Optional<EntityStub> result = underTest.findById(cacheRepository, keyMapper, ENTITY_ID_1, cacheContext);

        verifyZeroInteractions(expirableEntity);
        assertThat(result).contains(entity1);
    }
}