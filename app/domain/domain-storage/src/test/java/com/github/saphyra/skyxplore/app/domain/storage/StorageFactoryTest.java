package com.github.saphyra.skyxplore.app.domain.storage;

import com.github.saphyra.skyxplore.app.domain.storage.domain.Storage;
import com.github.saphyra.skyxplore.app.domain.storage.domain.StorageKey;
import com.github.saphyra.skyxplore.app.common.utils.UuidConverter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class StorageFactoryTest {
    private static final UUID USER_ID = UUID.randomUUID();
    private static final String VALUE = "value";
    private static final String USER_ID_STRING = "user-id";

    @Mock
    private UuidConverter uuidConverter;

    @InjectMocks
    private StorageFactory underTest;

    @Test
    public void create() {
        //GIVEN
        given(uuidConverter.convertDomain(USER_ID)).willReturn(USER_ID_STRING);
        //WHEN
        Storage result = underTest.create(USER_ID, StorageKey.LOCALE, VALUE);
        //THEN
        assertThat(result.getStorageKey().getUserId()).isEqualTo(USER_ID_STRING);
        assertThat(result.getStorageKey().getStorageKey()).isEqualTo(StorageKey.LOCALE);
        assertThat(result.getValue()).isEqualTo(VALUE);
    }
}