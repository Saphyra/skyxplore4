package com.github.saphyra.skyxplore.app.common.dao.cache_repository.component;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

import java.util.UUID;

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
public class GetEntityMappingComponentTest {
    private static final UUID KEY = UUID.randomUUID();

    @Mock
    private LoadByKeyComponent loadByKeyComponent;

    @Mock
    private CacheRepositoryStub cacheRepository;

    @Mock
    private CacheContext cacheContext;

    @InjectMocks
    private GetEntityMappingComponent underTest;

    @Mock
    private CacheMap<UUID, UUID, EntityStub> cacheMap;

    @Mock
    private ExpirableEntity<EntityMapping<UUID, EntityStub>> expirableEntity;

    @Mock
    private EntityMapping<UUID, EntityStub> entityaMapping;

    @Test
    public void alreadyInCache() {
        given(cacheRepository.getCacheMap()).willReturn(cacheMap);
        given(cacheMap.containsKey(KEY)).willReturn(true);
        given(cacheMap.get(KEY)).willReturn(expirableEntity);
        given(expirableEntity.updateLastAccessAndGetEntity()).willReturn(entityaMapping);

        EntityMapping<UUID, EntityStub> result = underTest.getEntityMapping(cacheRepository, KEY, cacheContext);

        verifyZeroInteractions(loadByKeyComponent);
        assertThat(result).isEqualTo(entityaMapping);
    }

    @Test
    public void notInCache() {
        given(cacheRepository.getCacheMap()).willReturn(cacheMap);
        given(cacheMap.containsKey(KEY)).willReturn(false);
        given(cacheMap.get(KEY)).willReturn(expirableEntity);
        given(expirableEntity.updateLastAccessAndGetEntity()).willReturn(entityaMapping);

        EntityMapping<UUID, EntityStub> result = underTest.getEntityMapping(cacheRepository, KEY, cacheContext);

        verify(loadByKeyComponent).loadByKey(cacheRepository, KEY, cacheContext);
        assertThat(result).isEqualTo(entityaMapping);
    }
}