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

@RunWith(MockitoJUnitRunner.class)
public class DeleteByKeyComponentTest {
    private static final UUID KEY = UUID.randomUUID();

    @Mock
    private CacheRepositoryStub cacheRepository;

    @InjectMocks
    private DeleteByKeyComponent underTest;

    @Mock
    private EntityStub entityStub;

    @Test
    public void deleteByKey() {
        given(cacheRepository.getByKey(KEY)).willReturn(Arrays.asList(entityStub));

        underTest.deleteByKey(cacheRepository, KEY);

        verify(cacheRepository).deleteAll(Arrays.asList(entityStub));
    }
}