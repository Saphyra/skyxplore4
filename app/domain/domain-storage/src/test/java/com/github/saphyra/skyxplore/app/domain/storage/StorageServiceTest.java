package com.github.saphyra.skyxplore.app.domain.storage;

import com.github.saphyra.skyxplore.app.common.event.UserDeletedEvent;
import com.github.saphyra.skyxplore.app.domain.storage.domain.StorageRepository;
import com.github.saphyra.skyxplore.app.common.utils.UuidConverter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class StorageServiceTest {
    private static final UUID USER_ID = UUID.randomUUID();
    private static final String USER_ID_STRING = "user-id";

    @Mock
    private StorageRepository storageRepository;

    @Mock
    private UuidConverter uuidConverter;

    @InjectMocks
    private StorageService underTest;

    @Test
    public void deleteStorage() {
        //GIVEN
        given(uuidConverter.convertDomain(USER_ID)).willReturn(USER_ID_STRING);
        //WHEN
        underTest.deleteStorage(new UserDeletedEvent(USER_ID));
        //THEN
        verify(storageRepository).deleteByUserId(USER_ID_STRING);
    }
}