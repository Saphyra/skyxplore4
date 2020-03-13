package com.github.saphyra.skyxplore.app.common.dao.cache_repository.component;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.app.common.dao.cache_repository.CacheContext;

@RunWith(MockitoJUnitRunner.class)
public class LoadByKeyComponentTest {
    private static final UUID KEY = UUID.randomUUID();

    @Mock
    private AddToCacheComponent addToCacheComponent;

    @Mock
    private CacheRepositoryStub cacheRepository;

    @Mock
    CacheContext cacheContext;

    @InjectMocks
    private LoadByKeyComponent underTest;

    @Mock
    private EntityStub entityStub;

    @Test
    public void loadByKey() {
        given(cacheRepository.getByKey(KEY)).willReturn(Arrays.asList(entityStub));

        underTest.loadByKey(cacheRepository, KEY, cacheContext);

        verify(addToCacheComponent).addToCache(cacheRepository, KEY, Arrays.asList(entityStub), cacheContext);
    }
}