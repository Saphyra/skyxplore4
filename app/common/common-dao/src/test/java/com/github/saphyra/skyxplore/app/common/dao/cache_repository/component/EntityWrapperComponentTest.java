package com.github.saphyra.skyxplore.app.common.dao.cache_repository.component;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.time.OffsetDateTime;
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
import com.github.saphyra.skyxplore.app.common.dao.cache_repository.ExpirableEntity;
import com.github.saphyra.util.OffsetDateTimeProvider;

@RunWith(MockitoJUnitRunner.class)
public class EntityWrapperComponentTest {
    private static final UUID ENTITY_ID = UUID.randomUUID();
    private static final OffsetDateTime LAST_ACCESS = OffsetDateTime.now();

    @Mock
    private CacheContext cacheContext;

    @Mock
    private OffsetDateTimeProvider offsetDateTimeProvider;

    @InjectMocks
    private EntityWrapperComponent underTest;


    @Test
    public void wrapEntities() {
        given(cacheContext.getOffsetDateTimeProvider()).willReturn(offsetDateTimeProvider);
        given(offsetDateTimeProvider.getCurrentDate()).willReturn(LAST_ACCESS);

        Map<UUID, EntityStub> entityStubMap = new HashMap<>();
        EntityStub entityStub = EntityStub.builder().id(ENTITY_ID).build();
        entityStubMap.put(ENTITY_ID, entityStub);

        ExpirableEntity<EntityMapping<UUID, EntityStub>> result = underTest.wrapEntities(entityStubMap, cacheContext);

        assertThat(result.getLastAccess()).isEqualTo(LAST_ACCESS);
        assertThat(result.getEntity()).hasSize(1);
        assertThat(result.getEntity().get(ENTITY_ID).isModified()).isFalse();
        assertThat(result.getEntity().get(ENTITY_ID).getEntity()).isEqualTo(entityStub);
    }
}