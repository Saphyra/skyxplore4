package com.github.saphyra.skyxplore.app.common.dao.cache_repository.component;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProcessDeletionsComponentTest {
    private static final UUID ENTITY_ID = UUID.randomUUID();

    @Mock
    private CacheRepositoryStub cacheRepository;

    @InjectMocks
    private ProcessDeletionsComponent underTest;

    @Test
    public void processDeletions() {
        HashSet<UUID> deleteQueue = new HashSet<>(Arrays.asList(ENTITY_ID));
        given(cacheRepository.getDeleteQueue()).willReturn(deleteQueue);

        underTest.processDeletions(cacheRepository);

        verify(cacheRepository).deleteByIds(Arrays.asList(ENTITY_ID));
    }
}