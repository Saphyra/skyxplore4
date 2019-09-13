package com.github.saphyra.skyxplore.platform.storage.locale;

import com.github.saphyra.cache.AbstractCache;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class LocaleCache extends AbstractCache<UUID, String> {
    private final LocaleQueryService localeQueryService;

    public LocaleCache(LocaleQueryService localeQueryService) {
        super(CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.MINUTES).build());
        this.localeQueryService = localeQueryService;
    }


    @Override
    public Optional<String> get(UUID key) {
        return localeQueryService.getLocale(key);
    }
}
