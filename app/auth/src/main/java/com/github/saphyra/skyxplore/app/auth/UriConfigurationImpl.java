package com.github.saphyra.skyxplore.app.auth;

import com.github.saphyra.authservice.auth.UriConfiguration;
import com.github.saphyra.authservice.auth.domain.AllowedUri;
import com.github.saphyra.skyxplore.app.common.config.RequestConstants;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class UriConfigurationImpl implements UriConfiguration {
    private static final List<AllowedUri> ALLOWED_URIS = Stream.concat(
        Stream.of(
            new AllowedUri("/", HttpMethod.GET),
            new AllowedUri(RequestConstants.REGISTRATION_MAPPING, HttpMethod.PUT),
            new AllowedUri(RequestConstants.INDEX_MAPPING, HttpMethod.GET),
            new AllowedUri(RequestConstants.API_PREFIX + "/locale/*", HttpMethod.POST)
        ),
        RequestConstants.PROPERTY_PATHS.stream()
            .map(propertyPath -> new AllowedUri(propertyPath, HttpMethod.GET))
    ).collect(Collectors.toList());

    @Override
    public List<AllowedUri> getAllowedUris() {
        return ALLOWED_URIS;
    }
}
