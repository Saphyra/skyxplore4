package com.github.saphyra.skyxplore.app.domain.storage.locale;

import com.github.saphyra.skyxplore.app.domain.storage.domain.Storage;
import com.github.saphyra.skyxplore.app.domain.storage.domain.StorageKey;
import com.github.saphyra.skyxplore.app.domain.storage.domain.StorageKeyId;
import com.github.saphyra.skyxplore.app.domain.storage.domain.StorageRepository;
import com.github.saphyra.skyxplore.app.common.utils.UuidConverter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class LocaleQueryServiceTest {
    private static final UUID USER_ID = UUID.randomUUID();
    private static final String USER_ID_STRING = "user-id";
    private static final String LOCALE = "locale";

    @Mock
    private StorageRepository storageRepository;

    @Mock
    private UuidConverter uuidConverter;

    @InjectMocks
    private LocaleQueryService underTest;

    @Mock
    private Storage storage;

    @Test
    public void getLocale() {
        //GIVEN
        given(uuidConverter.convertDomain(USER_ID)).willReturn(USER_ID_STRING);
        given(storageRepository.findById(any())).willReturn(Optional.of(storage));
        given(storage.getValue()).willReturn(LOCALE);
        //WHEN
        Optional<String> result = underTest.getLocale(USER_ID);
        //THEN
        ArgumentCaptor<StorageKeyId> argumentCaptor = ArgumentCaptor.forClass(StorageKeyId.class);
        verify(storageRepository).findById(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getUserId()).isEqualTo(USER_ID_STRING);
        assertThat(argumentCaptor.getValue().getStorageKey()).isEqualTo(StorageKey.LOCALE);
        assertThat(result).contains(LOCALE);
    }
}