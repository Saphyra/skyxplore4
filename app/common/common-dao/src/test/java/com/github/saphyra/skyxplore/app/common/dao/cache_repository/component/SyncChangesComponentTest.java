package com.github.saphyra.skyxplore.app.common.dao.cache_repository.component;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.app.common.dao.cache_repository.CacheMap;
import com.github.saphyra.skyxplore.app.common.dao.cache_repository.EntityMapping;
import com.github.saphyra.skyxplore.app.common.dao.cache_repository.ExpirableEntity;
import com.github.saphyra.skyxplore.app.common.dao.cache_repository.ModifiableEntity;

@RunWith(MockitoJUnitRunner.class)
public class SyncChangesComponentTest {
    @Mock
    private CacheRepositoryStub cacheRepository;

    @InjectMocks
    private SyncChangesComponent underTest;

    @Mock
    private CacheMap<UUID, UUID, EntityStub> cacheMap;

    @Mock
    private EntityStub entity;

    @Mock
    private ModifiableEntity<EntityStub> modifiableEntity;

    @Mock
    private ExpirableEntity<EntityMapping<UUID, EntityStub>> expirableEntity;

    @Mock
    private EntityMapping<UUID, EntityStub> entityMapping;

    @Test
    public void syncChanges() {
        given(cacheRepository.getCacheMap()).willReturn(cacheMap);


        given(cacheMap.values()).willReturn(Arrays.asList(expirableEntity));
        given(expirableEntity.getEntity()).willReturn(entityMapping);
        given(entityMapping.values()).willReturn(Arrays.asList(modifiableEntity));
        given(modifiableEntity.getEntity()).willReturn(entity);
        given(modifiableEntity.isModified()).willReturn(true).willReturn(false);

        underTest.syncChanges(cacheRepository);

        verify(cacheRepository).saveAll(Arrays.asList(entity));
        verify(entity).setNew(false);
        verify(modifiableEntity, times(1)).setModified(false);
    }
}