package com.github.saphyra.skyxplore.web;

import com.github.saphyra.authservice.redirection.RedirectionFilterSettings;
import com.github.saphyra.authservice.redirection.domain.ProtectedUri;
import com.github.saphyra.authservice.redirection.domain.RedirectionContext;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
public class HomePageFilterSetting implements RedirectionFilterSettings {
    private static final ProtectedUri PROTECTED_URI = new ProtectedUri("/", HttpMethod.GET);

    @Override
    public ProtectedUri getProtectedUri() {
        return PROTECTED_URI;
    }

    @Override
    public boolean shouldRedirect(RedirectionContext redirectionContext) {
        return true;
    }

    @Override
    public String getRedirectionPath(RedirectionContext redirectionContext) {
        return PageController.INDEX_MAPPING;
    }

    @Override
    public Integer getFilterOrder() {
        return Integer.MIN_VALUE;
    }

    @Override
    public String toString() {
        return "HomePageFilterSetting - " + PROTECTED_URI.toString();
    }
}
