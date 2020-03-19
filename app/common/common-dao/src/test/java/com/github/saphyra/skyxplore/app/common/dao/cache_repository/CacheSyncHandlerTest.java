package com.github.saphyra.skyxplore.app.common.dao.cache_repository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.app.common.service.ExecutorServiceBean;

@RunWith(MockitoJUnitRunner.class)
public class CacheSyncHandlerTest {
    @Mock
    private CacheRepository<?, ?, ?, ?> cacheRepository;

    @Mock
    private ExecutorServiceBean executorServiceBean;

    private CacheSyncHandler underTest;

    @Before
    public void setUp() {
        underTest = CacheSyncHandler.builder()
            .executorServiceBean(executorServiceBean)
            .repositories(Arrays.asList(cacheRepository))
            .build();
        doAnswer(invocationOnMock -> {
            ((Runnable) invocationOnMock.getArgument(0)).run();
            return null;
        }).when(executorServiceBean).execute(any());
    }

    @Test
    public void fullSync() {
        underTest.fullSync();

        verify(cacheRepository).fullSync();
    }

    @Test
    public void syncChanges() {
        underTest.syncChanges();

        verify(cacheRepository).syncChanges();
    }

    @Test
    public void processDeletions() {
        underTest.processDeletions();

        verify(cacheRepository).processDeletions();
    }
}