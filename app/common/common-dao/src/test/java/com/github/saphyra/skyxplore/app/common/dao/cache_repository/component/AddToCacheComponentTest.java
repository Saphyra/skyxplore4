package com.github.saphyra.skyxplore.app.common.dao.cache_repository.component;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
public class AddToCacheComponentTest {
    private static final UUID KEY = UUID.randomUUID();
    private static final UUID ENTITY_ID = UUID.randomUUID();
    private static final UUID DELETED_ENTITY_ID = UUID.randomUUID();

    @Mock
    private EntityWrapperComponent entityWrapperComponent;

    @Mock
    private CacheContext cacheContext;


    @Mock
    private CacheRepositoryStub cacheRepository;

    @InjectMocks
    private AddToCacheComponent underTest;

    @Mock
    private EntityStub entityStub;

    @Mock
    private EntityStub deletedEntityStub;

    @Mock
    private ExpirableEntity<EntityMapping<UUID, EntityStub>> wrappedEntities;

    @Mock
    private CacheMap<UUID, UUID, EntityStub> cacheMap;

    @Test
    public void addToCache() {
        given(entityStub.getId()).willReturn(ENTITY_ID);
        given(deletedEntityStub.getId()).willReturn(DELETED_ENTITY_ID);

        given(cacheRepository.getDeleteQueue()).willReturn(new HashSet<>(Arrays.asList(DELETED_ENTITY_ID)));

        Map<UUID, EntityStub> map = Stream.of(entityStub)
            .collect(Collectors.toMap(EntityStub::getId, Function.identity()));
        given(entityWrapperComponent.wrapEntities(map, cacheContext)).willReturn(wrappedEntities);

        given(cacheRepository.getCacheMap()).willReturn(cacheMap);

        underTest.addToCache(cacheRepository, KEY, Arrays.asList(entityStub, deletedEntityStub), cacheContext);

        verify(cacheMap).put(KEY, wrappedEntities);
    }
}