package com.github.saphyra.skyxplore.app.common.dao.cache_repository;

import java.time.OffsetDateTime;

import com.github.saphyra.util.OffsetDateTimeProvider;
import lombok.Builder;
import lombok.Data;

@Data
class ExpirableEntity<TYPE> {
    private final TYPE entity;
    private volatile OffsetDateTime lastAccess;
    private final OffsetDateTimeProvider offsetDateTimeProvider;
    private final Integer expiration;

    @Builder
    ExpirableEntity(TYPE entity, CacheContext cacheContext) {
        this.entity = entity;
        this.offsetDateTimeProvider = cacheContext.getOffsetDateTimeProvider();
        lastAccess = offsetDateTimeProvider.getCurrentDate();
        expiration = cacheContext.getCacheRepositoryExpirationSeconds();
    }

    synchronized void updateLastAccess() {
        OffsetDateTime now = offsetDateTimeProvider.getCurrentDate();
        if (now.isAfter(lastAccess)) {
            lastAccess = now;
        }
    }

    boolean isExpired() {
        return lastAccess.isBefore(offsetDateTimeProvider.getCurrentDate().minusSeconds(expiration));
    }

    TYPE updateLastAccessAndGetEntity() {
        updateLastAccess();
        return getEntity();
    }
}
