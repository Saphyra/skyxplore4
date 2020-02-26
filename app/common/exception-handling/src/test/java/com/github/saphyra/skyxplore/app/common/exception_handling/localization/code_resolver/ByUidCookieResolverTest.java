package com.github.saphyra.skyxplore.app.common.exception_handling.localization.code_resolver;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.saphyra.skyxplore.app.common.config.RequestDataProvider;
import com.github.saphyra.skyxplore.app.common.exception_handling.localization.LocaleProvider;
import com.github.saphyra.skyxplore.app.common.exception_handling.localization.properties.ErrorCodeLocalization;
import com.github.saphyra.skyxplore.app.common.exception_handling.localization.properties.ErrorCodeService;
import com.github.saphyra.skyxplore.app.common.utils.UuidConverter;

@RunWith(MockitoJUnitRunner.class)
public class ByUidCookieResolverTest {
    private static final String USER_ID_STRING = "user-id-string";
    private static final UUID USER_ID = UUID.randomUUID();
    private static final String LOCALE = "locale";

    @Mock
    private ErrorCodeService errorCodeService;

    @Mock
    private LocaleProvider localeProvider;

    @Mock
    private RequestDataProvider requestDataProvider;

    @Mock
    private UuidConverter uuidConverter;

    @InjectMocks
    private ByUidCookieResolver underTest;

    @Mock
    private HttpServletRequest request;

    @Mock
    private ErrorCodeLocalization errorCodeLocalization;

    @Test
    public void userIdNotFound() {
        //GIVEN
        given(requestDataProvider.getUserId(request)).willReturn(Optional.empty());
        //WHEN
        Optional<ErrorCodeLocalization> result = underTest.getByUid(request);
        //THEN
        assertThat(result).isEmpty();
    }

    @Test
    public void localeNotSetForUser() {
        //GIVEN
        given(requestDataProvider.getUserId(request)).willReturn(Optional.of(USER_ID_STRING));
        given(uuidConverter.convertEntity(USER_ID_STRING)).willReturn(USER_ID);
        given(localeProvider.getByUserId(USER_ID)).willReturn(Optional.empty());
        //WHEN
        Optional<ErrorCodeLocalization> result = underTest.getByUid(request);
        //THEN
        assertThat(result).isEmpty();
    }

    @Test
    public void localeNotSupported() {
        //GIVEN
        given(requestDataProvider.getUserId(request)).willReturn(Optional.of(USER_ID_STRING));
        given(uuidConverter.convertEntity(USER_ID_STRING)).willReturn(USER_ID);
        given(localeProvider.getByUserId(USER_ID)).willReturn(Optional.of(LOCALE));
        given(errorCodeService.getOptional(LOCALE)).willReturn(Optional.empty());
        //WHEN
        Optional<ErrorCodeLocalization> result = underTest.getByUid(request);
        //THEN
        assertThat(result).isEmpty();
    }

    @Test
    public void getByUid() {
        //GIVEN
        given(requestDataProvider.getUserId(request)).willReturn(Optional.of(USER_ID_STRING));
        given(uuidConverter.convertEntity(USER_ID_STRING)).willReturn(USER_ID);
        given(localeProvider.getByUserId(USER_ID)).willReturn(Optional.of(LOCALE));
        given(errorCodeService.getOptional(LOCALE)).willReturn(Optional.of(errorCodeLocalization));
        //WHEN
        Optional<ErrorCodeLocalization> result = underTest.getByUid(request);
        //THEN
        assertThat(result).contains(errorCodeLocalization);
    }
}