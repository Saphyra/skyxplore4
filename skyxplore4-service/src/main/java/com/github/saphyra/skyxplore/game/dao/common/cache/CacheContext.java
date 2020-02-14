package com.github.saphyra.skyxplore.game.dao.common.cache;

import com.github.saphyra.util.OffsetDateTimeProvider;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class CacheContext {
    private final OffsetDateTimeProvider offsetDateTimeProvider;
    private final Integer cacheRepositoryExpirationSeconds;

    public CacheContext(
        OffsetDateTimeProvider offsetDateTimeProvider,
        @Value("${com.github.saphyra.skyxplore.cacheRepository.expirationSeconds}") Integer cacheRepositoryExpirationSeconds
    ) {
        this.offsetDateTimeProvider = offsetDateTimeProvider;
        this.cacheRepositoryExpirationSeconds = cacheRepositoryExpirationSeconds;
    }
}
