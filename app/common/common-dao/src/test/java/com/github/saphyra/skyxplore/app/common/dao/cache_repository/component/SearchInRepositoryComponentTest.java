package com.github.saphyra.skyxplore.app.common.dao.cache_repository.component;

import com.github.saphyra.skyxplore.app.common.dao.cache_repository.CacheContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class)
public class SearchInRepositoryComponentTest {
    private static final UUID ENTITY_ID = UUID.randomUUID();
    private static final UUID KEY = UUID.randomUUID();

    @Mock
    private LoadByKeyComponent loadByKeyComponent;

    @Mock
    private CacheRepositoryStub cacheRepository;

    @Mock
    private CacheContext cacheContext;

    @InjectMocks
    private SearchInRepositoryComponent underTest;


    private EntityStub entity = EntityStub.builder()
        .id(ENTITY_ID)
        .key(KEY)
        .build();

    @Test
    public void searchInRepository_found() {
        given(cacheRepository.findById(ENTITY_ID)).willReturn(Optional.of(entity));

        Optional<EntityStub> result = underTest.searchInRepository(cacheRepository, EntityStub::getKey, ENTITY_ID, cacheContext);

        verify(loadByKeyComponent).loadByKey(cacheRepository, KEY, cacheContext);
        assertThat(result).contains(entity);
    }

    @Test
    public void searchInRepository_notFound() {
        given(cacheRepository.findById(ENTITY_ID)).willReturn(Optional.empty());

        Optional<EntityStub> result = underTest.searchInRepository(cacheRepository, EntityStub::getKey, ENTITY_ID, cacheContext);

        verifyZeroInteractions(loadByKeyComponent);
        assertThat(result).isEmpty();
    }
}