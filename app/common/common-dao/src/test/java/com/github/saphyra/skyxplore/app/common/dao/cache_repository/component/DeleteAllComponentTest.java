package com.github.saphyra.skyxplore.app.common.dao.cache_repository.component;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.time.OffsetDateTime;
import java.util.Arrays;
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
import com.github.saphyra.skyxplore.app.common.dao.cache_repository.ModifiableEntity;
import com.github.saphyra.util.OffsetDateTimeProvider;

@RunWith(MockitoJUnitRunner.class)
public class DeleteAllComponentTest {
    private static final UUID ENTITY_ID = UUID.randomUUID();
    private static final UUID DELETABLE_ENTITY_ID = UUID.randomUUID();
    private static final OffsetDateTime INITIAL_LAST_ACCESS = OffsetDateTime.now().minusSeconds(234);
    private static final OffsetDateTime UPDATED_LAST_ACCESS = OffsetDateTime.now();

    @Mock
    private CacheContext cacheContext;

    @Mock
    private CacheRepositoryStub cacheRepository;

    @Mock
    private OffsetDateTimeProvider offsetDateTimeProvider;

    @InjectMocks
    private DeleteAllComponent underTest;

    @Mock
    private CacheMap<UUID, UUID, EntityStub> cacheMap;

    @Test
    public void deleteAll() {
        EntityStub entity = EntityStub.builder().id(ENTITY_ID).build();
        EntityStub deletableEntity = EntityStub.builder().id(DELETABLE_ENTITY_ID).build();

        given(cacheRepository.getCacheMap()).willReturn(cacheMap);

        EntityMapping<UUID, EntityStub> entityMapping = new EntityMapping<>();
        entityMapping.put(ENTITY_ID, new ModifiableEntity<>(entity, false));
        entityMapping.put(DELETABLE_ENTITY_ID, new ModifiableEntity<>(deletableEntity, false));

        given(cacheContext.getOffsetDateTimeProvider()).willReturn(offsetDateTimeProvider);
        given(offsetDateTimeProvider.getCurrentDate()).willReturn(INITIAL_LAST_ACCESS);
        ExpirableEntity<EntityMapping<UUID, EntityStub>> expirableEntity = new ExpirableEntity<>(entityMapping, cacheContext);

        given(cacheMap.values()).willReturn(Arrays.asList(expirableEntity));
        given(offsetDateTimeProvider.getCurrentDate()).willReturn(UPDATED_LAST_ACCESS);

        underTest.deleteAll(cacheRepository, Arrays.asList(deletableEntity));

        assertThat(entityMapping).hasSize(1);
        assertThat(entityMapping.get(ENTITY_ID).getEntity()).isEqualTo(entity);
        assertThat(expirableEntity.getLastAccess()).isEqualTo(UPDATED_LAST_ACCESS);
    }
}