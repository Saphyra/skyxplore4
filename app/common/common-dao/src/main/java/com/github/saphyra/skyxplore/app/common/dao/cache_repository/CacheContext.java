package com.github.saphyra.skyxplore.app.common.dao.cache_repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.github.saphyra.skyxplore.app.common.dao.cache_repository.component.CacheComponentWrapper;
import com.github.saphyra.util.OffsetDateTimeProvider;
import lombok.Getter;

@Component
@Getter
public class CacheContext {
    private final CacheComponentWrapper cacheComponentWrapper;
    private final OffsetDateTimeProvider offsetDateTimeProvider;
    private final Integer cacheRepositoryExpirationSeconds;

    public CacheContext(
        CacheComponentWrapper cacheComponentWrapper,
        OffsetDateTimeProvider offsetDateTimeProvider,
        @Value("${com.github.saphyra.skyxplore.cacheRepository.expirationSeconds}") Integer cacheRepositoryExpirationSeconds
    ) {
        this.cacheComponentWrapper = cacheComponentWrapper;
        this.offsetDateTimeProvider = offsetDateTimeProvider;
        this.cacheRepositoryExpirationSeconds = cacheRepositoryExpirationSeconds;
    }
}
