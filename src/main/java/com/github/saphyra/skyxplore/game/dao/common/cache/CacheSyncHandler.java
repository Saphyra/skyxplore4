package com.github.saphyra.skyxplore.game.dao.common.cache;

import com.github.saphyra.skyxplore.common.ExecutorServiceBean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@EnableScheduling
@Slf4j
public class CacheSyncHandler {
    private final List<CacheRepository<?, ?, ?, ?>> repositories;
    private final ExecutorServiceBean executorServiceBean;

    @Scheduled(fixedRateString = "${com.github.saphyra.skyxplore.cacheRepository.fullSyncExecutionIntervalInMilliseconds}")
    public void fullSync() {
        executorServiceBean.execute(
            () -> {
                log.info("Executing scheduled sync for CacheRepositories...");
                repositories.stream()
                    .parallel()
                    .forEach(CacheRepository::fullSync);
                log.info("Scheduled sync is finished.");
            }
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
