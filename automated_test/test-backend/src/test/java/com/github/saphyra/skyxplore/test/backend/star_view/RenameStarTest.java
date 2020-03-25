package com.github.saphyra.skyxplore.test.backend.star_view;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.http.HttpStatus;
import org.testng.annotations.Test;

import com.github.saphyra.exceptionhandling.domain.ErrorResponse;
import com.github.saphyra.skyxplore.app.common.exception_handling.ErrorCode;
import com.github.saphyra.skyxplore.app.rest.request.user.RegistrationRequest;
import com.github.saphyra.skyxplore.app.rest.view.map.MapView;
import com.github.saphyra.skyxplore.app.rest.view.map.StarMapView;
import com.github.saphyra.skyxplore.test.common.TestBase;
import com.github.saphyra.skyxplore.test.common.parameters.RegistrationParameters;
import com.github.saphyra.skyxplore.test.framework.ResponseConverter;
import com.github.saphyra.skyxplore.test.framework.actions.CommonActions;
import com.github.saphyra.skyxplore.test.framework.actions.MapPageActions;
import com.github.saphyra.skyxplore.test.framework.actions.StarViewActions;
import com.github.saphyra.skyxplore.test.framework.model.AccessCookies;
import io.restassured.response.Response;

public class RenameStarTest extends TestBase {
    private static final String TOO_SHORT_STAR_NAME = "aa";
    private static final String TOO_LONG_STAR_NAME = Stream.generate(() -> "a").limit(31).collect(Collectors.joining());
    private static final String VALID_STAR_NAME = "valid-star name";

    @Test
    public void tooShortStarName() {
        RegistrationRequest registrationRequest = RegistrationParameters.validParameters()
            .toRegistrationRequest();
        AccessCookies accessCookies = CommonActions.registerAndCreateGame(registrationRequest);

        MapView map = MapPageActions.getMap(accessCookies);
        StarMapView starMapView = map.getStars().get(0);

        Response response = StarViewActions.getRenameStarResponse(accessCookies, starMapView.getStarId(), TOO_SHORT_STAR_NAME);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        ErrorResponse errorResponse = ResponseConverter.convert(response, ErrorResponse.class);
        assertThat(errorResponse.getErrorCode()).isEqualTo(ErrorCode.INVALID_STAR_NAME.name());
        assertThat(errorResponse.getLocalizedMessage()).isEqualTo("Érvénytelen csillag név.");
    }

    @Test
    public void tooLongStarName() {
        RegistrationRequest registrationRequest = RegistrationParameters.validParameters()
            .toRegistrationRequest();
        AccessCookies accessCookies = CommonActions.registerAndCreateGame(registrationRequest);

        MapView map = MapPageActions.getMap(accessCookies);
        StarMapView starMapView = map.getStars().get(0);

        Response response = StarViewActions.getRenameStarResponse(accessCookies, starMapView.getStarId(), TOO_LONG_STAR_NAME);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        ErrorResponse errorResponse = ResponseConverter.convert(response, ErrorResponse.class);
        assertThat(errorResponse.getErrorCode()).isEqualTo(ErrorCode.INVALID_STAR_NAME.name());
        assertThat(errorResponse.getLocalizedMessage()).isEqualTo("Érvénytelen csillag név.");
    }

    @Test
    public void renameStar() {
        RegistrationRequest registrationRequest = RegistrationParameters.validParameters()
            .toRegistrationRequest();
        AccessCookies accessCookies = CommonActions.registerAndCreateGame(registrationRequest);

        MapView map = MapPageActions.getMap(accessCookies);
        StarMapView starMapView = map.getStars().get(0);

        Response response = StarViewActions.getRenameStarResponse(accessCookies, starMapView.getStarId(), VALID_STAR_NAME);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK.value());

        StarMapView result = MapPageActions.getMap(accessCookies).getStars().get(0);
        assertThat(result.getStarName()).isEqualTo(VALID_STAR_NAME);
    }
}
