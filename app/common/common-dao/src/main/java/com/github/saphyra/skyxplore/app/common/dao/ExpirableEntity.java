package com.github.saphyra.skyxplore.app.common.dao;

import java.time.OffsetDateTime;

import com.github.saphyra.util.OffsetDateTimeProvider;
import lombok.Data;

@Data
//TODO unit test
public class ExpirableEntity<TYPE> {
    private final TYPE entity;
    private volatile OffsetDateTime lastAccess;
    private final OffsetDateTimeProvider offsetDateTimeProvider;
    private final Integer expiration;

    public ExpirableEntity(TYPE entity, CacheContext cacheContext) {
        this.entity = entity;
        this.offsetDateTimeProvider = cacheContext.getOffsetDateTimeProvider();
        lastAccess = offsetDateTimeProvider.getCurrentDate();
        expiration = cacheContext.getCacheRepositoryExpirationSeconds();
    }

    public synchronized void updateLastAccess() {
        OffsetDateTime now = offsetDateTimeProvider.getCurrentDate();
        if (now.isAfter(lastAccess)) {
            lastAccess = now;
        }
    }

    public boolean isExpired() {
        return lastAccess.isBefore(offsetDateTimeProvider.getCurrentDate().minusSeconds(expiration));
    }

    public TYPE updateLastAccessAndGetEntity() {
        updateLastAccess();
        return getEntity();
    }
}
