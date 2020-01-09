package com.github.saphyra.skyxplore.game.dao.common.cache;

import com.github.saphyra.skyxplore.common.ExecutorServiceBean;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CacheSyncHandler {
    private final List<CacheRepository<?, ?, ?, ?>> repositories;
    private final ExecutorServiceBean executorServiceBean;

    public void fullSync() {
        executorServiceBean.execute(
            () -> repositories.stream()
                .parallel()
                .forEach(CacheRepository::fullSync)
        );
    }

    public void syncChanges() {
        executorServiceBean.execute(
            () -> repositories.stream()
                .parallel()
                .forEach(CacheRepository::syncChanges)
        );
    }

    public void processDeletions() {
        executorServiceBean.execute(() -> repositories
            .stream()
            .parallel()
            .forEach(CacheRepository::processDeletions));
    }
}
