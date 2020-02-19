package com.github.saphyra.skyxplore.app.domain.storage.locale;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class LocaleCacheTest {
    private static final String LOCALE = "locale";
    private static final UUID USER_ID = UUID.randomUUID();

    @Mock
    private LocaleQueryService localeQueryService;

    @InjectMocks
    private LocaleCache underTest;

    @Test
    public void get() {
        //GIVEN
        given(localeQueryService.getLocale(USER_ID)).willReturn(Optional.of(LOCALE));
        //WHEN
        Optional<String> result = underTest.get(USER_ID);
        underTest.get(USER_ID);
        //THEN
        assertThat(result).contains(LOCALE);
        verify(localeQueryService, times(1)).getLocale(USER_ID);
    }

    @Test
    public void getByUserId() {
        //GIVEN
        given(localeQueryService.getLocale(USER_ID)).willReturn(Optional.of(LOCALE));
        //WHEN
        Optional<String> result = underTest.getByUserId(USER_ID);
        underTest.getByUserId(USER_ID);
        //THEN
        assertThat(result).contains(LOCALE);
        verify(localeQueryService, times(1)).getLocale(USER_ID);
    }
}