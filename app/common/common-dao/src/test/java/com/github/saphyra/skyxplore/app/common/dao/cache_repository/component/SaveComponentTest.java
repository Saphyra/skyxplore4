package com.github.saphyra.skyxplore.app.common.dao.cache_repository.component;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.app.common.dao.cache_repository.CacheContext;
import com.github.saphyra.skyxplore.app.common.dao.cache_repository.EntityMapping;
import com.github.saphyra.skyxplore.app.common.dao.cache_repository.ModifiableEntity;

@RunWith(MockitoJUnitRunner.class)
public class SaveComponentTest {
    private static final UUID ENTITY_ID = UUID.randomUUID();
    private static final UUID KEY = UUID.randomUUID();

    @Mock
    private GetEntityMappingComponent getEntityMappingComponent;

    @Mock
    private CacheRepositoryStub cacheRepository;

    @Mock
    private CacheContext cacheContext;

    @InjectMocks
    private SaveComponent underTest;

    @Mock
    private EntityMapping<UUID, EntityStub> entityMapping;

    @Captor
    private ArgumentCaptor<ModifiableEntity<EntityStub>> modifiableEntityCaptor;

    @Test
    public void save() {
        EntityStub entity = EntityStub.builder().id(ENTITY_ID).key(KEY).build();

        given(getEntityMappingComponent.getEntityMapping(cacheRepository, KEY, cacheContext)).willReturn(entityMapping);
        HashSet<UUID> deleteQueue = new HashSet<>(Arrays.asList(ENTITY_ID));
        given(cacheRepository.getDeleteQueue()).willReturn(deleteQueue);

        EntityStub result = underTest.save(cacheRepository, EntityStub::getKey, entity, cacheContext);

        verify(entityMapping).put(eq(ENTITY_ID), modifiableEntityCaptor.capture());
        assertThat(modifiableEntityCaptor.getValue().getEntity()).isEqualTo(entity);
        assertThat(modifiableEntityCaptor.getValue().isModified()).isTrue();
        assertThat(deleteQueue).isEmpty();
        assertThat(result).isEqualTo(entity);
    }
}