package com.github.saphyra.skyxplore.app.common.dao.cache_repository.component;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.app.common.dao.cache_repository.CacheContext;
import com.github.saphyra.skyxplore.app.common.dao.cache_repository.EntityMapping;

@RunWith(MockitoJUnitRunner.class)
public class GetMapByKeyComponentTest {
    private static final UUID KEY = UUID.randomUUID();
    private static final UUID ENTITY_ID = UUID.randomUUID();

    @Mock
    private ExtractEntitiesComponent extractEntities;

    @Mock
    private GetEntityMappingComponent getEntityMapping;

    @Mock
    private CacheRepositoryStub cacheRepository;

    @Mock
    private CacheContext cacheContext;

    @InjectMocks
    private GetMapByKeyComponent underTest;

    @Mock
    private EntityMapping<UUID, EntityStub> entityMapping;

    @Test
    public void getMapByKey() {
        given(getEntityMapping.getEntityMapping(cacheRepository, KEY, cacheContext)).willReturn(entityMapping);

        Map<UUID, EntityStub> map = new HashMap<>();
        EntityStub entityStub = EntityStub.builder().id(ENTITY_ID).build();
        map.put(ENTITY_ID, entityStub);
        given(extractEntities.extractEntities(entityMapping)).willReturn(map);

        Map<UUID, EntityStub> result = underTest.getMapByKey(cacheRepository, KEY, cacheContext);
        assertThat(result).hasSize(1);
        assertThat(result.get(ENTITY_ID)).isEqualTo(entityStub);
    }
}