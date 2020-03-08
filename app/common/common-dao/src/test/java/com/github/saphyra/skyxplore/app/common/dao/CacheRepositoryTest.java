package com.github.saphyra.skyxplore.app.common.dao;

import com.github.saphyra.util.OffsetDateTimeProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.repository.CrudRepository;

import java.lang.reflect.Field;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class)
public class CacheRepositoryTest {
    private static final UUID ENTITY_ID_1 = UUID.randomUUID();
    private static final UUID ENTITY_ID_2 = UUID.randomUUID();
    private static final UUID KEY_1 = UUID.randomUUID();
    private static final OffsetDateTime NEW_LAST_ACCESS = OffsetDateTime.now();
    private static final OffsetDateTime INITIAL_LAST_ACCESS = OffsetDateTime.now().minusSeconds(1);

    @Mock
    private CrudRepository<EntityStub, UUID> repository;

    @Mock
    private CacheContext cacheContext;

    @Mock
    private OffsetDateTimeProvider offsetDateTimeProvider;

    @InjectMocks
    private CacheRepositoryStub underTest;

    @Before
    public void setUp() {
        given(cacheContext.getOffsetDateTimeProvider()).willReturn(offsetDateTimeProvider);
    }

    @Test
    public void findById_notFound() {
        given(repository.findById(ENTITY_ID_1)).willReturn(Optional.empty());

        Optional<EntityStub> result = underTest.findById(ENTITY_ID_1);

        assertThat(result).isEmpty();
    }

    @Test
    public void findById_notInCache() throws NoSuchFieldException, IllegalAccessException {
        EntityStub entity1 = new EntityStub(ENTITY_ID_1, KEY_1, false);
        EntityStub entity2 = new EntityStub(ENTITY_ID_2, KEY_1, false);
        given(repository.findById(ENTITY_ID_1)).willReturn(Optional.of(entity1));
        given(repository.findAll()).willReturn(Arrays.asList(entity1, entity2));

        Optional<EntityStub> result = underTest.findById(ENTITY_ID_1);

        assertThat(result).contains(entity1);

        Map<UUID, ExpirableEntity<Map<UUID, ModifiableEntity<EntityStub>>>> cache = getCache();
        assertThat(cache).containsKey(KEY_1);
        ExpirableEntity<Map<UUID, ModifiableEntity<EntityStub>>> items = cache.get(KEY_1);
        assertThat(items.getEntity()).containsKeys(ENTITY_ID_1, ENTITY_ID_2);
        assertThat(items.getEntity().get(ENTITY_ID_1).getEntity()).isEqualTo(entity1);
        assertThat(items.getEntity().get(ENTITY_ID_1).isModified()).isFalse();
        assertThat(items.getEntity().get(ENTITY_ID_2).getEntity()).isEqualTo(entity2);
        assertThat(items.getEntity().get(ENTITY_ID_2).isModified()).isFalse();
    }

    @Test
    public void findById_alreadyInCache() throws NoSuchFieldException, IllegalAccessException {
        given(offsetDateTimeProvider.getCurrentDate()).willReturn(INITIAL_LAST_ACCESS);

        EntityStub entity = new EntityStub(ENTITY_ID_1, KEY_1, false);
        ModifiableEntity<EntityStub> modifiableEntity = new ModifiableEntity<>(entity, false);
        Map<UUID, ModifiableEntity<EntityStub>> modifiableEntityMap = new ConcurrentHashMap<>();
        modifiableEntityMap.put(ENTITY_ID_1, modifiableEntity);
        Map<UUID, ExpirableEntity<Map<UUID, ModifiableEntity<EntityStub>>>> cache = getCache();
        cache.put(KEY_1, new ExpirableEntity<>(modifiableEntityMap, cacheContext));

        given(offsetDateTimeProvider.getCurrentDate()).willReturn(NEW_LAST_ACCESS);

        Optional<EntityStub> result = underTest.findById(ENTITY_ID_1);

        assertThat(result).contains(entity);
        verifyZeroInteractions(repository);
        assertThat(cache.get(KEY_1).getLastAccess()).isEqualTo(NEW_LAST_ACCESS);
    }

    @Test
    public void findById_inDeleteQueue() throws NoSuchFieldException, IllegalAccessException {
        Set<UUID> deleteQueue = getDeleteQueue();
        deleteQueue.add(ENTITY_ID_1);

        Optional<EntityStub> result = underTest.findById(ENTITY_ID_1);

        assertThat(result).isEmpty();
    }

    private Set<UUID> getDeleteQueue() throws NoSuchFieldException, IllegalAccessException {
        Field f = underTest.getClass().getSuperclass().getDeclaredField("deleteQueue");
        f.setAccessible(true);
        //noinspection unchecked
        return (Set<UUID>) f.get(underTest);
    }

    private Map<UUID, ExpirableEntity<Map<UUID, ModifiableEntity<EntityStub>>>> getCache() throws NoSuchFieldException, IllegalAccessException {
        Field f = underTest.getClass().getSuperclass().getDeclaredField("cacheMap");
        f.setAccessible(true);
        //noinspection unchecked
        return (Map<UUID, ExpirableEntity<Map<UUID, ModifiableEntity<EntityStub>>>>) f.get(underTest);
    }
}