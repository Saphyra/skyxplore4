package com.github.saphyra.skyxplore.app.domain.storage.locale;

import com.github.saphyra.cache.AbstractCache;
import com.github.saphyra.skyxplore.common.exception_handling.localization.LocaleProvider;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class LocaleCache extends AbstractCache<UUID, String> implements LocaleProvider {
    private final LocaleQueryService localeQueryService;

    public LocaleCache(LocaleQueryService localeQueryService) {
        super(CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.MINUTES).build());
        this.localeQueryService = localeQueryService;
    }

    @Override
    public Optional<String> load(UUID key) {
        return localeQueryService.getLocale(key);
    }

    @Override
    public Optional<String> getByUserId(UUID userId) {
        return get(userId);
    }
}
