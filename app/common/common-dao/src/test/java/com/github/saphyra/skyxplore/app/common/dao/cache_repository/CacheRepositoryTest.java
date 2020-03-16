package com.github.saphyra.skyxplore.app.common.dao.cache_repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.repository.CrudRepository;

import com.github.saphyra.skyxplore.app.common.dao.cache_repository.component.CacheComponentWrapper;
import com.github.saphyra.skyxplore.app.common.dao.cache_repository.component.CacheRepositoryStub;
import com.github.saphyra.skyxplore.app.common.dao.cache_repository.component.DeleteAllComponent;
import com.github.saphyra.skyxplore.app.common.dao.cache_repository.component.DeleteByKeyComponent;
import com.github.saphyra.skyxplore.app.common.dao.cache_repository.component.EntityStub;
import com.github.saphyra.skyxplore.app.common.dao.cache_repository.component.EvictExpiredEntitiesComponent;
import com.github.saphyra.skyxplore.app.common.dao.cache_repository.component.FindAllComponent;
import com.github.saphyra.skyxplore.app.common.dao.cache_repository.component.FindByIdComponent;
import com.github.saphyra.skyxplore.app.common.dao.cache_repository.component.GetMapByKeyComponent;
import com.github.saphyra.skyxplore.app.common.dao.cache_repository.component.ProcessDeletionsComponent;
import com.github.saphyra.skyxplore.app.common.dao.cache_repository.component.SaveComponent;
import com.github.saphyra.skyxplore.app.common.dao.cache_repository.component.SyncChangesComponent;
import com.github.saphyra.util.OffsetDateTimeProvider;

@RunWith(MockitoJUnitRunner.class)
public class CacheRepositoryTest {
    private static final UUID KEY = UUID.randomUUID();
    private static final UUID ENTITY_ID_1 = UUID.randomUUID();
    private static final UUID ENTITY_ID_2 = UUID.randomUUID();

    @Mock
    private CrudRepository<EntityStub, UUID> repository;

    @Mock
    private CacheContext cacheContext;

    @Mock
    private CacheComponentWrapper cacheComponentWrapper;

    @Mock
    private DeleteByKeyComponent deleteByKeyComponent;

    @Mock
    private GetMapByKeyComponent getMapByKeyComponent;

    @Mock
    private SyncChangesComponent syncChangesComponent;

    @Mock
    private ProcessDeletionsComponent processDeletionsComponent;

    @Mock
    private EvictExpiredEntitiesComponent evictExpiredEntitiesComponent;

    @Mock
    private SaveComponent saveComponent;

    @Mock
    private FindByIdComponent findByIdComponent;

    @Mock
    private FindAllComponent findAllComponent;

    @Mock
    private OffsetDateTimeProvider offsetDateTimeProvider;

    @Mock
    private DeleteAllComponent deleteAllComponent;

    private CacheRepositoryStub underTest;

    @Before
    public void setUp() {
        given(cacheContext.getCacheComponentWrapper()).willReturn(cacheComponentWrapper);

        underTest = new CacheRepositoryStub(repository, cacheContext);
        given(cacheComponentWrapper.getDeleteByKey()).willReturn(deleteByKeyComponent);
        given(cacheComponentWrapper.getGetMapByKey()).willReturn(getMapByKeyComponent);
        given(cacheComponentWrapper.getProcessDeletionsComponent()).willReturn(processDeletionsComponent);
        given(cacheComponentWrapper.getSyncChangesComponent()).willReturn(syncChangesComponent);
        given(cacheComponentWrapper.getEvictExpiredEntitiesComponent()).willReturn(evictExpiredEntitiesComponent);
        given(cacheComponentWrapper.getSaveComponent()).willReturn(saveComponent);
        given(cacheComponentWrapper.getFindById()).willReturn(findByIdComponent);
        given(cacheComponentWrapper.getFindAll()).willReturn(findAllComponent);
        given(cacheComponentWrapper.getDeleteAll()).willReturn(deleteAllComponent);

        given(cacheContext.getOffsetDateTimeProvider()).willReturn(offsetDateTimeProvider);
    }

    @Test
    public void deleteByKey() {
        underTest.deleteByKey(KEY);

        verify(deleteByKeyComponent).deleteByKey(underTest, KEY);
    }

    @Test
    public void getMapByKey() {
        EntityStub entityStub = EntityStub.builder().id(ENTITY_ID_1).key(KEY).build();
        Map<UUID, EntityStub> map = Stream.of(entityStub)
            .collect(Collectors.toMap(EntityStub::getId, Function.identity()));

        given(getMapByKeyComponent.getMapByKey(underTest, KEY, cacheContext)).willReturn(map);

        Map<UUID, EntityStub> result = underTest.getMapByKey(KEY);

        assertThat(result).isEqualTo(map);
    }

    @Test
    public void processDeletions() {
        underTest.processDeletions();

        verify(processDeletionsComponent).processDeletions(underTest);
    }

    @Test
    public void syncChanges() {
        underTest.syncChanges();

        verify(syncChangesComponent).syncChanges(underTest);
    }

    @Test
    public void evictExpiredEntities() {
        underTest.evictExpiredEntities();

        verify(evictExpiredEntitiesComponent).evictExpiredEntities(underTest);
    }

    @Test
    public void fullSync() {
        underTest.fullSync();

        verify(processDeletionsComponent).processDeletions(underTest);
        verify(syncChangesComponent).syncChanges(underTest);
        verify(evictExpiredEntitiesComponent).evictExpiredEntities(underTest);
    }

    @Test
    public void save() {
        EntityStub entityStub = EntityStub.builder().id(ENTITY_ID_1).key(KEY).build();
        given(saveComponent.save(eq(underTest), any(), eq(entityStub), eq(cacheContext))).willReturn(entityStub);

        EntityStub result = underTest.save(entityStub);

        assertThat(result).isEqualTo(entityStub);
    }

    @Test
    public void saveAll() {
        EntityStub entityStub = EntityStub.builder().id(ENTITY_ID_1).key(KEY).build();
        given(saveComponent.save(eq(underTest), any(), eq(entityStub), eq(cacheContext))).willReturn(entityStub);

        Iterable<EntityStub> result = underTest.saveAll(Arrays.asList(entityStub));

        assertThat(result).isEqualTo(Arrays.asList(entityStub));
    }

    @Test
    public void findById() {
        EntityStub entityStub = EntityStub.builder().id(ENTITY_ID_1).key(KEY).build();
        given(findByIdComponent.findById(eq(underTest), any(), eq(ENTITY_ID_1), eq(cacheContext))).willReturn(Optional.of(entityStub));

        Optional<EntityStub> result = underTest.findById(ENTITY_ID_1);

        assertThat(result).contains(entityStub);
    }

    @Test
    public void existsById_inDeleteQueue() {
        underTest.getDeleteQueue().add(ENTITY_ID_1);

        boolean result = underTest.existsById(ENTITY_ID_1);

        assertThat(result).isFalse();
    }

    @Test
    public void existsById() {
        given(repository.existsById(ENTITY_ID_1)).willReturn(true);

        boolean result = underTest.existsById(ENTITY_ID_1);

        assertThat(result).isTrue();
    }

    @Test
    public void findAll() {
        EntityStub entityStub = EntityStub.builder().id(ENTITY_ID_1).key(KEY).build();
        given(findAllComponent.findAll(eq(underTest), any(), eq(cacheContext))).willReturn(Arrays.asList(entityStub));

        Iterable<EntityStub> result = underTest.findAll();

        assertThat(result).containsExactly(entityStub);
    }

    @Test
    public void findAllById() {
        EntityStub entity1 = EntityStub.builder().id(ENTITY_ID_1).key(KEY).build();
        EntityStub entity2 = EntityStub.builder().id(ENTITY_ID_2).key(KEY).build();
        given(findAllComponent.findAll(eq(underTest), any(), eq(cacheContext))).willReturn(Arrays.asList(entity1, entity2));

        Iterable<EntityStub> result = underTest.findAllById(Arrays.asList(ENTITY_ID_1));

        assertThat(result).containsExactly(entity1);
    }

    @Test
    public void count() {
        given(repository.count()).willReturn(5L);

        long result = underTest.count();

        assertThat(result).isEqualTo(5);
    }

    @Test
    public void deleteById() {
        EntityStub entityStub = EntityStub.builder().id(ENTITY_ID_1).key(KEY).build();
        ModifiableEntity<EntityStub> modifiableEntity = new ModifiableEntity<>(entityStub, false);

        EntityMapping<UUID, EntityStub> entityMapping = new EntityMapping<>();
        entityMapping.put(ENTITY_ID_1, modifiableEntity);
        ExpirableEntity<EntityMapping<UUID, EntityStub>> expirableEntity = new ExpirableEntity<>(entityMapping, cacheContext);
        underTest.getCacheMap().put(KEY, expirableEntity);

        underTest.deleteById(ENTITY_ID_1);

        assertThat(underTest.getDeleteQueue()).containsExactly(ENTITY_ID_1);
        assertThat(underTest.getCacheMap().get(KEY).getEntity()).isEmpty();
    }

    @Test
    public void delete() {
        EntityStub entityStub = EntityStub.builder().id(ENTITY_ID_1).key(KEY).build();
        ModifiableEntity<EntityStub> modifiableEntity = new ModifiableEntity<>(entityStub, false);

        EntityMapping<UUID, EntityStub> entityMapping = new EntityMapping<>();
        entityMapping.put(ENTITY_ID_1, modifiableEntity);
        ExpirableEntity<EntityMapping<UUID, EntityStub>> expirableEntity = new ExpirableEntity<>(entityMapping, cacheContext);
        underTest.getCacheMap().put(KEY, expirableEntity);

        underTest.delete(entityStub);

        assertThat(underTest.getDeleteQueue()).containsExactly(ENTITY_ID_1);
        assertThat(underTest.getCacheMap().get(KEY).getEntity()).isEmpty();
    }

    @Test
    public void deleteAllByEntity() {
        EntityStub entityStub = EntityStub.builder().id(ENTITY_ID_1).key(KEY).build();

        underTest.deleteAll(Arrays.asList(entityStub));

        verify(deleteAllComponent).deleteAll(underTest, Arrays.asList(entityStub));
    }

    @Test
    public void deleteAll() {
        EntityStub entityStub = EntityStub.builder().id(ENTITY_ID_1).key(KEY).build();
        ModifiableEntity<EntityStub> modifiableEntity = new ModifiableEntity<>(entityStub, false);

        EntityMapping<UUID, EntityStub> entityMapping = new EntityMapping<>();
        entityMapping.put(ENTITY_ID_1, modifiableEntity);
        ExpirableEntity<EntityMapping<UUID, EntityStub>> expirableEntity = new ExpirableEntity<>(entityMapping, cacheContext);
        underTest.getCacheMap().put(KEY, expirableEntity);

        underTest.getDeleteQueue().add(ENTITY_ID_2);

        underTest.deleteAll();

        assertThat(underTest.getCacheMap()).isEmpty();
        assertThat(underTest.getDeleteQueue()).isEmpty();
        verify(repository).deleteAll();
    }
}