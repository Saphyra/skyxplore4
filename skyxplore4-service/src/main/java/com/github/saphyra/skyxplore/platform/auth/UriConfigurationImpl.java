package com.github.saphyra.skyxplore.platform.auth;

import com.github.saphyra.authservice.auth.UriConfiguration;
import com.github.saphyra.authservice.auth.domain.AllowedUri;
import com.github.saphyra.skyxplore.common.PageController;
import com.github.saphyra.skyxplore.common.RequestConstants;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.github.saphyra.skyxplore.platform.auth.UserController.REGISTRATION_MAPPING;

@SuppressWarnings("unused")
@Component
public class UriConfigurationImpl implements UriConfiguration {
    private static final List<AllowedUri> ALLOWED_URIS = Stream.concat(
        Stream.of(
            new AllowedUri("/", HttpMethod.GET),
            new AllowedUri(REGISTRATION_MAPPING, HttpMethod.PUT),
            new AllowedUri(PageController.INDEX_MAPPING, HttpMethod.GET)
        ),
        RequestConstants.PROPERTY_PATHS.stream()
            .map(propertyPath -> new AllowedUri(propertyPath, HttpMethod.GET))
    ).collect(Collectors.toList());

    @Override
    public List<AllowedUri> getAllowedUris() {
        return ALLOWED_URIS;
    }
}
