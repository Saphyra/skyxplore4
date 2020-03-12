package com.github.saphyra.skyxplore.app.common.dao.cache_repository.component;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.app.common.dao.cache_repository.CacheMap;
import com.github.saphyra.skyxplore.app.common.dao.cache_repository.EntityMapping;
import com.github.saphyra.skyxplore.app.common.dao.cache_repository.ExpirableEntity;

@RunWith(MockitoJUnitRunner.class)
public class EvictExpiredEntitiesComponentTest {
    private static final UUID KEY_1 = UUID.randomUUID();
    private static final UUID KEY_2 = UUID.randomUUID();

    @Mock
    private CacheRepositoryStub cacheRepository;

    @InjectMocks
    private EvictExpiredEntitiesComponent underTest;

    @Mock
    private ExpirableEntity<EntityMapping<UUID, EntityStub>> expiredEntity;

    @Mock
    private ExpirableEntity<EntityMapping<UUID, EntityStub>> validEntity;

    @Test
    public void evictExpiredEntities() {
        CacheMap<UUID, UUID, EntityStub> cacheMap = new CacheMap<>();
        cacheMap.put(KEY_1, expiredEntity);
        cacheMap.put(KEY_2, validEntity);

        given(cacheRepository.getCacheMap()).willReturn(cacheMap);

        given(expiredEntity.isExpired()).willReturn(true);
        given(validEntity.isExpired()).willReturn(false);

        underTest.evictExpiredEntities(cacheRepository);

        assertThat(cacheMap).hasSize(1);
        assertThat(cacheMap.get(KEY_2)).isEqualTo(validEntity);
    }
}