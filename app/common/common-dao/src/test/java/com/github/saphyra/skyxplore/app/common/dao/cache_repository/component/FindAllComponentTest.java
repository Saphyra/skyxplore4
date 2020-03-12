package com.github.saphyra.skyxplore.app.common.dao.cache_repository.component;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.repository.CrudRepository;

import com.github.saphyra.skyxplore.app.common.dao.cache_repository.CacheContext;

@RunWith(MockitoJUnitRunner.class)
public class FindAllComponentTest {
    private static final UUID ENTITY_ID_1 = UUID.randomUUID();
    private static final UUID ENTITY_ID_2 = UUID.randomUUID();
    private static final UUID KEY_1 = UUID.randomUUID();
    private static final UUID KEY_2 = UUID.randomUUID();
    private static final UUID DELETED_ENTITY_ID = UUID.randomUUID();

    @Mock
    private AddToCacheComponent addToCacheComponent;

    @Mock
    private CacheContext cacheContext;

    @Mock
    private CacheRepositoryStub cacheRepository;

    @Mock
    private CrudRepository<EntityStub, UUID> crudRepository;

    @InjectMocks
    private FindAllComponent underTest;

    @Test
    public void findAll() {
        given(cacheRepository.getRepository()).willReturn(crudRepository);
        given(cacheRepository.getDeleteQueue()).willReturn(new HashSet<>(Arrays.asList(DELETED_ENTITY_ID)));

        EntityStub entity1 = EntityStub.builder().id(ENTITY_ID_1).key(KEY_1).build();
        EntityStub entity2 = EntityStub.builder().id(ENTITY_ID_2).key(KEY_2).build();
        EntityStub deletedEntity = EntityStub.builder().id(DELETED_ENTITY_ID).build();
        given(crudRepository.findAll()).willReturn(Arrays.asList(entity1, entity2, deletedEntity));

        List<EntityStub> result = underTest.findAll(cacheRepository, EntityStub::getKey, cacheContext);

        verify(addToCacheComponent).addToCache(cacheRepository, KEY_1, Arrays.asList(entity1), cacheContext);
        verify(addToCacheComponent).addToCache(cacheRepository, KEY_2, Arrays.asList(entity2), cacheContext);
        verifyNoMoreInteractions(addToCacheComponent);
        assertThat(result).contains(entity1, entity2);
    }
}