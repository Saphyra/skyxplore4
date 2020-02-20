package com.github.saphyra.skyxplore.app.domain.storage.locale;

import com.github.saphyra.skyxplore.app.domain.storage.StorageFactory;
import com.github.saphyra.skyxplore.app.domain.storage.domain.Storage;
import com.github.saphyra.skyxplore.app.domain.storage.domain.StorageKey;
import com.github.saphyra.skyxplore.app.domain.storage.domain.StorageRepository;
import com.github.saphyra.skyxplore.common.utils.UuidConverter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class LocaleServiceTest {
    private static final UUID USER_ID = UUID.randomUUID();
    private static final String LOCALE = "locale";
    private static final String USER_ID_STRING = "user-id";

    @Mock
    private LocaleCache localeCache;

    @Mock
    private StorageRepository storageRepository;

    @Mock
    private StorageFactory storageFactory;

    @Mock
    private UuidConverter uuidConverter;

    @InjectMocks
    private LocaleService underTest;

    @Mock
    private Storage storage;

    @Before
    public void setUp() {
        given(uuidConverter.convertDomain(USER_ID)).willReturn(USER_ID_STRING);
    }

    @Test
    public void setLocale_createNew() {
        //GIVEN
        given(storageRepository.findById(any())).willReturn(Optional.empty());
        given(storageFactory.create(USER_ID, StorageKey.LOCALE)).willReturn(storage);
        //WHEN
        underTest.setLocale(USER_ID, LOCALE);
        //THEN
        verify(storage).setValue(LOCALE);
        verify(storageRepository).save(storage);
        verify(localeCache).invalidate(USER_ID);
    }

    @Test
    public void setLocale_update() {
        //GIVEN
        given(storageRepository.findById(any())).willReturn(Optional.of(storage));
        //WHEN
        underTest.setLocale(USER_ID, LOCALE);
        //THEN
        verify(storage).setValue(LOCALE);
        verify(storageRepository).save(storage);
        verify(localeCache).invalidate(USER_ID);
    }
}