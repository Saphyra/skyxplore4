package com.github.saphyra.skyxplore.test.backend.locale;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.http.HttpStatus;
import org.testng.annotations.Test;

import com.github.saphyra.exceptionhandling.domain.ErrorResponse;
import com.github.saphyra.skyxplore.app.common.config.RequestConstants;
import com.github.saphyra.skyxplore.app.common.exception_handling.ErrorCode;
import com.github.saphyra.skyxplore.test.common.TestBase;
import com.github.saphyra.skyxplore.test.framework.ResponseConverter;
import com.github.saphyra.skyxplore.test.framework.actions.IndexPageActions;
import com.github.saphyra.skyxplore.test.framework.actions.SettingsPageActions;
import com.github.saphyra.skyxplore.test.framework.model.AccessCookies;
import io.restassured.response.Response;

public class SetLocaleTest extends TestBase {
    @Test
    public void invalidLocale() {
        //GIVEN
        AccessCookies accessCookies = IndexPageActions.registerAndLogin();
        //WHEN
        Response response = SettingsPageActions.setLocale(accessCookies, "invalid-cookie");
        //THEN
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        ErrorResponse errorResponse = ResponseConverter.convert(response, ErrorResponse.class);
        assertThat(errorResponse.getErrorCode()).isEqualTo(ErrorCode.INVALID_LOCALE.name());
        assertThat(errorResponse.getLocalizedMessage()).isEqualTo("Érvénytelen nyelv.");
    }

    @Test
    public void setLocale() {
        //GIVEN
        AccessCookies accessCookies = IndexPageActions.registerAndLogin();
        //WHEN
        Response response = SettingsPageActions.setLocale(accessCookies, "hu");
        //THEN
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getCookie(RequestConstants.COOKIE_LOCALE)).isEqualTo("hu");
    }
}
